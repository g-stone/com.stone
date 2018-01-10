package com.stone.tools.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

@Component("schedulerContext")
public class SchedulerContext implements ApplicationContextAware, InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		long startTime = System.currentTimeMillis();
		schedulerTaskRegistar = applicationContext.getBean(ContextLifecycleScheduledTaskRegistrar.class);
		logger.info("定时任务上下文初始化with：" + schedulerTaskRegistar);
		
		List<CronTask> cronTasks = schedulerTaskRegistar.getCronTaskList();
		
		for(CronTask task:cronTasks){
			logger.info("初始化任务{}描述{}...", task.toString(), task.getExpression());
		}
		
		CronTask taskDefine = new CronTask(new Runnable(){
			@Override
			public void run() {
				System.out.println("run...");
			}}, "0 0/1 * * * ?");
		List<CronTask> newTaskList = new ArrayList<CronTask>();
		newTaskList.add(taskDefine);
		newTaskList.addAll(cronTasks);
		schedulerTaskRegistar.setCronTasksList(newTaskList);
		
		long endTime = System.currentTimeMillis();
		logger.info("初始化{}在{}耗费{}...", "任务调度", new Date(System.currentTimeMillis()), endTime - startTime);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	private ApplicationContext applicationContext;
	private ContextLifecycleScheduledTaskRegistrar schedulerTaskRegistar;
	private static Logger logger = LoggerFactory.getLogger(SchedulerContext.class);
}
