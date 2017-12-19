package com.stone.ui.kindeditor.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

import com.stone.tools.property.IStonePropertyPlaceholderConfigurer;

@Component("kindeditorConfig")
public class KindeditorConfiguration implements ApplicationContextAware, ServletContextAware, InitializingBean{
	private ApplicationContext applicationContext;
	private ServletContext servletContext;
	private IStonePropertyPlaceholderConfigurer config;
	private static Logger logger = Logger.getLogger(KindeditorConfiguration.class);
	private static String DEFAULT_MAX_SIZE = "1000000";
	private static String DEFAULT_SUBFIX = "attached/";
	
	private String rootPathPrefix;
	private String rootPathSubfix;
	private String rootPathMaping;
	private String rootPath;
	private List<String> categoryTypes;
	private List<String> imageTypes;
	private List<String> flashTypes;
	private List<String> mediaTypes;
	private List<String> filesTypes;
	private Long maxSize;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		config = applicationContext.getBean(IStonePropertyPlaceholderConfigurer.class);
		
		String prefix = config.getProperties().getProperty("config.kindeditor.root.path.prefix");
		
		if(StringUtils.hasText(prefix)){
			setRootPathPrefix(prefix);
		} else {
			setRootPathPrefix(servletContext.getRealPath("/"));
		}
		
		String subfix = config.getProperties().getProperty("config.kindeditor.root.path.subfix", DEFAULT_SUBFIX);
		setRootPathSubfix(subfix);
		
		String mapping = config.getProperties().getProperty("config.kindeditor.root.path.maping");
		if(StringUtils.hasText(prefix)){
			this.setRootPathMaping(mapping);
		}
		
		setRootPath(getRootPathPrefix() + getRootPathSubfix());
		
		String categoryType = config.getProperties().getProperty("config.kindeditor.category.type");
		categoryTypes = new ArrayList<String>();
		if(StringUtils.hasText(categoryType)){
			for(String item:categoryType.split(",")){
				categoryTypes.add(item.trim());
			}
		}
		
		String imageType = config.getProperties().getProperty("config.kindeditor.image.type");
		imageTypes = new ArrayList<String>();
		if(StringUtils.hasText(imageType)){
			for(String item:imageType.split(",")){
				imageTypes.add(item.trim());
			}
		}
		
		String flashType = config.getProperties().getProperty("config.kindeditor.flash.type");
		flashTypes = new ArrayList<String>();
		if(StringUtils.hasText(flashType)){
			for(String item:flashType.split(",")){
				flashTypes.add(item.trim());
			}
		}
		
		String mediaType = config.getProperties().getProperty("config.kindeditor.media.type");
		mediaTypes = new ArrayList<String>();
		if(StringUtils.hasText(mediaType)){
			for(String item:mediaType.split(",")){
				mediaTypes.add(item.trim());
			}
		}
		
		String filesType = config.getProperties().getProperty("config.kindeditor.file.type");
		filesTypes = new ArrayList<String>();
		if(StringUtils.hasText(filesType)){
			for(String item:filesType.split(",")){
				filesTypes.add(item.trim());
			}
		}
		
		String maxsize = config.getProperties().getProperty("config.kindeditor.max.size", DEFAULT_MAX_SIZE);
		setMaxSize(Long.parseLong(maxsize.replaceAll("[^0-9]", "")));
		
		logger.info(this);
	}
	
	public String getRootPath() {
		return rootPath;
	}
	
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
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
	
	public Long getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}
	
	public String getRootPathPrefix() {
		return rootPathPrefix;
	}
	
	public void setRootPathPrefix(String rootPathPrefix) {
		this.rootPathPrefix = rootPathPrefix;
	}
	
	public String getRootPathSubfix() {
		return rootPathSubfix;
	}
	
	public void setRootPathSubfix(String rootPathSubfix) {
		this.rootPathSubfix = rootPathSubfix;
	}
	
	public String getRootPathMaping() {
		return rootPathMaping;
	}
	
	public void setRootPathMaping(String rootPathMaping) {
		this.rootPathMaping = rootPathMaping;
	}
	
	@Override
	public String toString() {
		return "KindeditorConfiguration [rootPath=" + rootPath + ", categoryTypes=" + categoryTypes + ", imageTypes="
				+ imageTypes + ", flashTypes=" + flashTypes + ", mediaTypes=" + mediaTypes + ", filesTypes="
				+ filesTypes + ", maxSize=" + maxSize + "]";
	}
}
