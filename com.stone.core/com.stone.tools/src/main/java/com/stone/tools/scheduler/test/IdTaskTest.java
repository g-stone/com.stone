package com.stone.tools.scheduler.test;

import java.util.concurrent.TimeUnit;

import com.stone.tools.scheduler.task.IdCronTask;

public class IdTaskTest implements Runnable{
	public IdTaskTest(IdCronTask task){
		this.task = task;
	}
	
	@Override
	public void run() {
		try {
			TimeUnit.MINUTES.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("发起作业停止信号...");
		//task.stop();
		task.resetTrigger("0 0/2 * * * ?");
	}
	
	private IdCronTask task;
}
