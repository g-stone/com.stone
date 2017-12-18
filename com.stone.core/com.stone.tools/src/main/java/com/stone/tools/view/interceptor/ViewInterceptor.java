package com.stone.tools.view.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(ViewInterceptor.class);
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info(modelAndView);
		logger.info(request.getContextPath());
		
		if(modelAndView != null){
			modelAndView.addObject("ctpath", request.getContextPath());
		}
	}
}
