package com.stone.fileserver.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.stone.fileserver.FileserverApplicationInitializer;
import com.stone.fileserver.cfg.FileserverConfiguration;
import com.stone.fileserver.comparator.FilenameComparator;
import com.stone.fileserver.comparator.FilesizeComparator;
import com.stone.fileserver.comparator.FiletypeComparator;
import com.stone.tools.ConverteUtils;
import com.stone.tools.DateUtils;

public class FileManagerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FileManagerServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>File manager servlet is work...</h1>");
		response.getWriter().println("session=" + request.getSession(true).getId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		FileserverConfiguration config = FileserverApplicationInitializer.config;
		
		String rootPath = config.getRootPath();
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = request.getContextPath();
		
		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if(!config.getCategoryTypes().contains(dirName)){
				logger.info("Invalid Directory name.");
				response.getWriter().print(ConverteUtils.toJson(result));
				return ;
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
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			logger.info("Parameter is not valid.");
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			logger.info("Directory does not exist.");
			response.getWriter().print(ConverteUtils.toJson(result));
			return;
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
					hash.put("is_photo", config.getFilesTypes().contains(fileExt));
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
		
		response.getWriter().print(ConverteUtils.toJson(result));
	}
}
