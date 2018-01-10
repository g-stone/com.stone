package com.stone.tools.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("monitorScheduler")
public class MonitorScheduler {
	public void monitor() {
		logger.info("监控执行{}在{}", new Date(System.currentTimeMillis()), 0);
	}
	
	private static Logger logger = LoggerFactory.getLogger(MonitorScheduler.class);
}
