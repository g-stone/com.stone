package com.stone.fileserver.cfg;

import java.util.List;
import java.util.Properties;

public class FileserverConfiguration {
	private Integer port;
	private String rootPath;
	private Integer maxSize;
	private Integer minSize;
	private Integer idleTimeout;
	private String threadGroup;
	
	private String managerService;
	private String uploadService;
	
	private List<String> categoryTypes;
	private List<String> imageTypes;
	private List<String> flashTypes;
	private List<String> mediaTypes;
	private List<String> filesTypes;
	private Long fileMaxSize;
	
	private Properties properties;
	private boolean status;
	private String msg;
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	public Integer getMinSize() {
		return minSize;
	}
	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}
	public Integer getIdleTimeout() {
		return idleTimeout;
	}
	public void setIdleTimeout(Integer idleTimeout) {
		this.idleTimeout = idleTimeout;
	}
	public String getThreadGroup() {
		return threadGroup;
	}
	public void setThreadGroup(String threadGroup) {
		this.threadGroup = threadGroup;
	}
	public String getManagerService() {
		return managerService;
	}
	public void setManagerService(String managerService) {
		this.managerService = managerService;
	}
	public String getUploadService() {
		return uploadService;
	}
	public void setUploadService(String uploadService) {
		this.uploadService = uploadService;
	}
	public List<String> getCategoryTypes() {
		return categoryTypes;
	}
	public void setCategoryTypes(List<String> categoryTypes) {
		this.categoryTypes = categoryTypes;
	}
	public List<String> getImageTypes() {
		return imageTypes;
	}
	public void setImageTypes(List<String> imageTypes) {
		this.imageTypes = imageTypes;
	}
	public List<String> getFlashTypes() {
		return flashTypes;
	}
	public void setFlashTypes(List<String> flashTypes) {
		this.flashTypes = flashTypes;
	}
	public List<String> getMediaTypes() {
		return mediaTypes;
	}
	public void setMediaTypes(List<String> mediaTypes) {
		this.mediaTypes = mediaTypes;
	}
	public List<String> getFilesTypes() {
		return filesTypes;
	}
	public void setFilesTypes(List<String> filesTypes) {
		this.filesTypes = filesTypes;
	}
	public Long getFileMaxSize() {
		return fileMaxSize;
	}
	public void setFileMaxSize(Long fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
