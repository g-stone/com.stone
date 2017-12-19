package com.stone.ui.kindeditor.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stone.tools.DateUtils;
import com.stone.ui.kindeditor.config.KindeditorConfiguration;
import com.stone.ui.kindeditor.config.comparator.FilenameComparator;
import com.stone.ui.kindeditor.config.comparator.FilesizeComparator;
import com.stone.ui.kindeditor.config.comparator.FiletypeComparator;

@RequestMapping("/spring/kindeditor")
@RestController("kindeditorController")
public class KindeditorController {
	@ResponseBody
	@RequestMapping(value = "/demo")
	public Object demo(Model model){
		return new Object();
	}
	
	@ResponseBody
	@RequestMapping(value = "/manager")
	public Map<String, Object> kindeditorManager(HttpServletResponse response, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		
		String rootPath = kindeditorConfig.getRootPath();
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = request.getContextPath() + kindeditorConfig.getRootPathMaping() + kindeditorConfig.getRootPathSubfix();
		
		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if(!kindeditorConfig.getCategoryTypes().contains(dirName)){
				logger.info("Invalid Directory name.");
				return result;
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		
		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";
		
		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			logger.info("Access is not allowed.");
			return result;
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			logger.info("Parameter is not valid.");
			return result;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			logger.info("Directory does not exist.");
			return result;
		}

		//遍历目录取的文件信息
		List<Hashtable<String,Object>> fileList = new ArrayList<Hashtable<String,Object>>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", kindeditorConfig.getFilesTypes().contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", DateUtils.formatDate(file.lastModified(), DateUtils.CN_DATE_FULL));
				fileList.add(hash);
			}
		}
		
		if ("size".equals(order)) {
			Collections.sort(fileList, new FilesizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new FiletypeComparator());
		} else {
			Collections.sort(fileList, new FilenameComparator());
		}
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/upload")
	public Map<String, Object> kindeditorUpload(HttpServletResponse response, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", 1);
		String savePath = kindeditorConfig.getRootPath();
		
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + kindeditorConfig.getRootPathMaping() + kindeditorConfig.getRootPathSubfix();
		
		//定义允许上传的文件扩展名
		HashMap<String, List<String>> extMap = new HashMap<String, List<String>>();
		extMap.put("image", kindeditorConfig.getImageTypes());
		extMap.put("flash", kindeditorConfig.getFlashTypes());
		extMap.put("media", kindeditorConfig.getMediaTypes());
		extMap.put("file", kindeditorConfig.getFilesTypes());
		
		//最大文件大小
		long maxSize = 1000000;
		
		if(!ServletFileUpload.isMultipartContent(request)){
			result.put("message", "请选择文件！");
			return result;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			result.put("message", "上传目录不存在！");
			return result;
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			result.put("message", "上传目录没有写权限！");
			return result;
		}
		
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			result.put("message", "目录名不正确！");
			return result;
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		String ymd = DateUtils.formatDate(new Date(), DateUtils.UN_DATE_DIG_SHORT);
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		try{
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = itr.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					//检查文件大小
					if(item.getSize() > maxSize){
						result.put("message", "上传文件大小超过限制！");
						return result;
					}
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!extMap.get(dirName).contains(fileExt)){
						result.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式！");
						return result;
					}
					
					String newFileName = DateUtils.formatDate(new Date(), DateUtils.UN_DATE_DIG_FULL) + "_" + new Random().nextInt(1000) + "." + fileExt;
					try{
						File uploadedFile = new File(savePath, newFileName);
						item.write(uploadedFile);
					}catch(Exception e){
						result.put("message", "上传文件失败！");
						return result;
					}
					
					result.put("error", 0);
					result.put("url", saveUrl + newFileName);
				}
			}
		}catch(Exception e){
			result.put("message", "上传文件失败！");
		}
		
		return result;
	}
	
	private KindeditorConfiguration kindeditorConfig;
	private static Logger logger = Logger.getLogger(KindeditorController.class);
	public KindeditorConfiguration getKindeditorConfig() {
		return kindeditorConfig;
	}
	@Resource(name = "kindeditorConfig")
	public void setKindeditorConfig(KindeditorConfiguration kindeditorConfig) {
		this.kindeditorConfig = kindeditorConfig;
	}
}
