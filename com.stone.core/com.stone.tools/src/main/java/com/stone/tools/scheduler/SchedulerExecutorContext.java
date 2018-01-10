package com.stone.tools.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.stone.tools.scheduler.task.IdCronTask;
import com.stone.tools.scheduler.test.IdTaskTest;

@Component("schedulerExecutorContext")
public class SchedulerExecutorContext implements InitializingBean{
	public void start(IdCronTask task) {
		try {
			if (StringUtils.isEmpty(task.getTaskId())) {
				throw new Exception("the taskid must be not empty.");
			}
			
			if (StringUtils.isEmpty(task.getTrigger())) {
				throw new Exception("任务的调度表达式不能为空.");
			}
			
			ScheduledFuture<?> scheduledFuture = scheduler.schedule(task.getRunnable(), task.getTrigger());
			scheduled_future.put(task.getTaskId(), scheduledFuture);
			tasks.put(task.getTaskId(), task);
			logger.info("the task with " + task.getTaskId() + "has bean already started.");
		} catch (Exception e) {
			logger.info(null, e);
			throw new RuntimeException(e);
		}
	}
	
	public void stop(String taskId) {
		logger.info("正在停止任务 " + taskId);
		if (StringUtils.isEmpty(taskId)) {
			throw new RuntimeException("the taskid must be not empty.");
		}
		
		try {
			ScheduledFuture<?> scheduledFuture = scheduled_future.remove(taskId);
			if (scheduledFuture == null) {
				throw new Exception("the task with id " + taskId + " is not exists.");
			} else {
				if (!scheduledFuture.isCancelled()) {
					/** false 表示当前任务若正在执行，则待其执行结束，再结束此任务. */
					scheduledFuture.cancel(false);
				}
			}
		} catch (Exception e) {
			logger.info(null, e);
			throw new RuntimeException(e);
		}
	}
	
	public void resetTrigger(String taskId, String cronExpression) {
		logger.info("正在修改当前任务 " + taskId + "执行频率.");
		if (StringUtils.isEmpty(taskId)) {
			throw new RuntimeException("the taskid must be not empty.");
		}
		
		if (StringUtils.isEmpty(cronExpression)) {
			throw new RuntimeException("任务的调度表达式不能为空.");
		}
		
		IdCronTask task = tasks.get(taskId);
		if (task != null) {
			if (cronExpression.equals(task.getTrigger())) {
				return;
			}
			
			/** first, stop the task. */
			ScheduledFuture<?> scheduledFuture = scheduled_future.remove(taskId);
			scheduledFuture.cancel(false);
			
			/** second, reset the task with cronExpression. */
			
			task.setTrigger(cronExpression);
			/** third, restart the task. */
			scheduledFuture = scheduler.schedule(task.getRunnable(), task.getTrigger());
			scheduled_future.put(taskId, scheduledFuture);
		}
	}
	public void onlyOneTime(IdCronTask task) {
		if (StringUtils.isEmpty(task.getTaskId())) {
			throw new RuntimeException("the taskid must be not empty.");
		}  
		
		scheduler.execute(task.getRunnable(), 2000);
	}
	public void destrory() {
		logger.info("正在终止自动任务的线程池资源.");
		ScheduledExecutorService scheduledExecutor = (ScheduledExecutorService) scheduler.getConcurrentExecutor();
		try {
			scheduledExecutor.shutdownNow();
		} catch (Exception e) {
			logger.info("自动任务的线程池资源清理发生异常.", e);
		} finally {
			logger.info("自动任务的线程池资源清理完成.");
		}
	}
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerExecutorContext.class);
	private static final Map<String, IdCronTask> tasks = new HashMap<String, IdCronTask>(12);
	private static final Map<String, ScheduledFuture<?>> scheduled_future = new HashMap<String, ScheduledFuture<?>>(16);
	private final static int POOL_SIZE = 64;
	private final ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(POOL_SIZE));
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(logger.isDebugEnabled()){
			IdCronTask task = new IdCronTask(new Runnable(){
				@Override
				public void run() {
					System.out.println(new Date(System.currentTimeMillis()) + " run...");
				}}, "0 0/1 * * * ?");
			task.setSchedulerContext(this);
			task.start();
			
			new Thread(new IdTaskTest(task)).start();
		}
	}
}
