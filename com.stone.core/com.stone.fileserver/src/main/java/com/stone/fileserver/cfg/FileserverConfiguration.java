package com.stone.fileserver.cfg;

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
	
	@Override
	public String toString() {
		return "FileserverConfiguration [port=" + port + ", rootPath=" + rootPath + ", maxSize=" + maxSize
				+ ", minSize=" + minSize + ", idleTimeout=" + idleTimeout + ", threadGroup=" + threadGroup
				+ ", properties=" + properties + ", status=" + status + ", msg=" + msg + "]";
	}
}
