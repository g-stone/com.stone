package com.stone.fileserver.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.stone.fileserver.FileserverApplicationInitializer;
import com.stone.fileserver.cfg.FileserverConfiguration;
import com.stone.tools.ConverteUtils;
import com.stone.tools.DateUtils;

public class FileUploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>File upload servlet is work...</h1>");
		response.getWriter().println("session=" + request.getSession(true).getId());
		
		FileserverConfiguration config = FileserverApplicationInitializer.config;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("error", 1);
		String savePath = config.getRootPath();
		
		//文件保存目录URL
		String saveUrl  = request.getContextPath();
		
		//定义允许上传的文件扩展名
		HashMap<String, List<String>> extMap = new HashMap<String, List<String>>();
		extMap.put("image", config.getImageTypes());
		extMap.put("flash", config.getFlashTypes());
		extMap.put("media", config.getMediaTypes());
		extMap.put("file", config.getFilesTypes());
		
		//最大文件大小
		long maxSize = 1000000;
		
		if(!ServletFileUpload.isMultipartContent(request)){
			result.put("message", "请选择文件！");
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			result.put("message", "上传目录不存在！");
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			result.put("message", "上传目录没有写权限！");
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
		}
		
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			result.put("message", "目录名不正确！");
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
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
						response.getWriter().print(ConverteUtils.toJson(result));
						return;
					}
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!extMap.get(dirName).contains(fileExt)){
						result.put("message", "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式！");
						response.getWriter().print(ConverteUtils.toJson(result));
						return;
					}
					
					String newFileName = DateUtils.formatDate(new Date(), DateUtils.UN_DATE_DIG_FULL) + "_" + new Random().nextInt(1000) + "." + fileExt;
					try{
						File uploadedFile = new File(savePath, newFileName);
						item.write(uploadedFile);
					}catch(Exception e){
						result.put("message", "上传文件失败！");
						response.getWriter().print(ConverteUtils.toJson(result));
						return;
					}
					
					result.put("error", 0);
					result.put("url", saveUrl + newFileName);
				}
			}
		}catch(Exception e){
			result.put("message", "上传文件失败！");
		}
		
		response.getWriter().print(ConverteUtils.toJson(result));
	}
}
