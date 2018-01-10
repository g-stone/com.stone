package com.stone.tools.scheduler.task;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.support.CronTrigger;

import com.stone.tools.scheduler.SchedulerExecutorContext;

public class IdCronTask {
	private String taskId;
	private CronTrigger trigger;
	private String cron;
	private Runnable runnable;
	private SchedulerExecutorContext schedulerContext;
	private static AtomicInteger atomic = new AtomicInteger();
	
	public IdCronTask(Runnable runnable, String expression){
		this.runnable = runnable;
		setTrigger(expression);
		taskId = "it-" + atomic.incrementAndGet();
	}
	
	public IdCronTask(Runnable runnable, String expression, String taskId){
		this.runnable = runnable;
		setTrigger(expression);
		if(taskId == null || taskId.trim().equals("")){
			this.taskId = "it-" + atomic.incrementAndGet();
		}else{
			this.taskId = taskId;
		}
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public CronTrigger getTrigger() {
		return trigger;
	}
	public void setTrigger(String expression) {
		setCron(expression);
		this.trigger = new CronTrigger(expression);
	}
	public String getCron() {
		return cron;
	}
	private void setCron(String cron) {
		this.cron = cron;
	}
	public Runnable getRunnable() {
		return runnable;
	}
	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
	public void setSchedulerContext(SchedulerExecutorContext schedulerContext) {
		this.schedulerContext = schedulerContext;
	}
	
	public void start(){
		schedulerContext.start(this);
	}
	
	public void stop(){
		schedulerContext.stop(this.taskId);
	}
	
	public void resetTrigger(String cronExpression){
		schedulerContext.resetTrigger(this.taskId, cronExpression);
	}
}
