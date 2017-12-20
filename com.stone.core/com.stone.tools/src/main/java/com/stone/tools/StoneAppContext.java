package com.stone.tools;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.stone.tools.property.IStonePropertyPlaceholderConfigurer;

@Component("applicationAwareUtils")
public class StoneAppContext implements ApplicationContextAware, InitializingBean{
	private static ApplicationContext applicationContext;
	private static IStonePropertyPlaceholderConfigurer propertyConfigurer;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(propertyConfigurer == null){
			propertyConfigurer = applicationContext.getBean(IStonePropertyPlaceholderConfigurer.class);
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		StoneAppContext.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return StoneAppContext.applicationContext;
	}
	
	public static WebApplicationContext getWebApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
	public static ServletContext getServletContext() {
		return getWebApplicationContext().getServletContext();
	}
	/**
	 * 获取当前请求实例对象
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取当前响应实例对象
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	/**
	 * 获取当前会话实例对象
	 * @return
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttr(String name) {
		Object obj = getSession().getAttribute(name);
		if (null == obj) {
			return null;
		}
		return (T) obj;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getRequestPara(String name) {
		Object obj = getRequest().getParameter(name);
		if (null == obj) {
			return null;
		}
		return (T) obj;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getRequestAttr(String name) {
		Object obj = getRequest().getAttribute(name);
		if (null == obj) {
			return null;
		}
		return (T) obj;
	}
	
	public static String getResourceKey(String key) {
		Object obj = propertyConfigurer.getProperties().get(key);
		if (null == obj) {
			return null;
		}
		return String.valueOf(obj);
	}
	
	public static String getRemoteAddr() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		// 判断是否为反向代理,多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
		// 是取X-Forwarded-For中第一个非unknown的有效IP字符串
		if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
			String[] tempArray = ip.split(",");
			for (int i = 0; i < tempArray.length; i++) {
				if (!"unknown".equalsIgnoreCase(tempArray[i])) {
					ip = tempArray[i].replaceAll("\\s", "");
					break;
				}
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		// 本地使用localhost访问时获取为0:0:0:0:0:0:0:1
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
