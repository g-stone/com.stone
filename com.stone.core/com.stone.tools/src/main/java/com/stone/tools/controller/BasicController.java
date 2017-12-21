package com.stone.tools.controller;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class BasicController {
	private static final ThreadLocal<SoftReference<Map<Class<?>, Logger>>> 
	THREADLOCAL_LOGGER = new ThreadLocal<SoftReference<Map<Class<?>, Logger>>>() {
		@Override
		protected SoftReference<Map<Class<?>, Logger>> initialValue() {
			return new SoftReference<Map<Class<?>, Logger>>(
					new HashMap<Class<?>, Logger>());
		}
	};
	
	protected Logger get(){
		final SoftReference<Map<Class<?>, Logger>> ref = THREADLOCAL_LOGGER.get();
		Map<Class<?>, Logger> logs = ref.get();
		
		if(logs == null){
			logs = new HashMap<Class<?>, Logger>();
			THREADLOCAL_LOGGER.set(new SoftReference<Map<Class<?>, Logger>>(logs));
		}
		
		Logger logger = logs.get(getClass());
		if(logger == null){
			logger = Logger.getLogger(getClass());
			logs.put(getClass(), logger);
		}
		
		return logger;
	}
}
