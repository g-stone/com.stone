package com.stone.tools.property;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class StonePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements IStonePropertyPlaceholderConfigurer {
	private final static Logger logger = Logger.getLogger(StonePropertyPlaceholderConfigurer.class);
	private Properties properties;
	
	@Override
	public Properties getProperties() {
		return properties;
	}
	
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties properties) throws BeansException {
		logger.info("回调属性设置...");
		super.processProperties(beanFactoryToProcess, properties);
		this.properties = properties;
	}
}
