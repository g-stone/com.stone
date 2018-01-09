package com.stone.tools.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;
import org.springframework.scheduling.config.CronTask;

public class SchedulerContext implements ApplicationContextAware, InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		schedulerTaskRegistar = applicationContext.getBean(ContextLifecycleScheduledTaskRegistrar.class);
		
		List<CronTask> cronTasks = schedulerTaskRegistar.getCronTaskList();
		
		for(CronTask task:cronTasks){
			logger.info(task);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	private ApplicationContext applicationContext;
	private ContextLifecycleScheduledTaskRegistrar schedulerTaskRegistar;
	private static Logger logger = Logger.getLogger(SchedulerContext.class);
}
