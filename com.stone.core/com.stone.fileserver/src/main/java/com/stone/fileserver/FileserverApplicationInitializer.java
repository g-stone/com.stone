package com.stone.fileserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;

import com.stone.fileserver.cfg.FileserverConfiguration;
import com.stone.fileserver.servlet.FileManagerServlet;
import com.stone.fileserver.servlet.FileUploadServlet;

/**
 * @function 文件服务器初始化
 * @author dba
 */
public class FileserverApplicationInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		long stamp = System.currentTimeMillis();
		config(servletContext);
		
		if(!config.isStatus()){
			logger.error(config.getMsg());
			System.exit(1);
		}
		
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMaxThreads(config.getMaxSize());
		threadPool.setMinThreads(config.getMinSize());
		threadPool.setIdleTimeout(config.getIdleTimeout());
		threadPool.setName(config.getThreadGroup());
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase(config.getRootPath());
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setStylesheet("");
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		
		context.addServlet(new ServletHolder(new FileManagerServlet()), config.getManagerService());
		context.addServlet(new ServletHolder(new FileUploadServlet()), config.getUploadService());
		
		context.addServlet(new ServletHolder(new HttpServlet(){
			private static final long serialVersionUID = 1L;
			protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println("<h1>Hello mm...</h1>");
				response.getWriter().println("session=" + request.getSession(true).getId());
			}
		}), "/hello");
		
		HandlerList handlers = new HandlerList();  
		handlers.setHandlers(new Handler[] {resourceHandler, context, new DefaultHandler()});
		
		Server server = new Server(threadPool);
		
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(config.getPort());
		connector.setReuseAddress(false);
		
		server.addConnector(connector);
		server.setHandler(handlers);
		server.setStopAtShutdown(true);
		
		try{
			server.start();
		} catch(Exception e){
			logger.error("文件服务启动失败", e);
			System.exit(1);
		}
		
		logger.info("初始化并启动JETTY文件服务器在" + new Date(System.currentTimeMillis()) + "耗时" + (System.currentTimeMillis() - stamp));
	}
	
	private void config(ServletContext servletContext){
		InputStream stream = null;
		config = new FileserverConfiguration();
		config.setStatus(false);
		try{
			stream = servletContext.getResourceAsStream("/WEB-INF/classes/" + configFile);
			Properties properties = new Properties();
			properties.load(stream);
			
			String val = properties.getProperty("file.server.port");
			if(StringUtils.hasText(val)){
				config.setPort(Integer.parseInt(val));
			} else {
				config.setMsg("端口号未配置");
				return;
			}
			
			val = properties.getProperty("file.server.root.path");
			if(StringUtils.hasText(val)){
				config.setRootPath(FilenameUtils.separatorsToSystem(val));
			} else {
				config.setMsg("服务根目录未配置");
				return;
			}
			
			val = properties.getProperty("file.server.pool.maxsize", MAX_POOL_SIZE);
			if(StringUtils.hasText(val)){
				config.setMaxSize(Integer.parseInt(val));
			}
			val = properties.getProperty("file.server.pool.minsize", MIN_POOL_SIZE);
			if(StringUtils.hasText(val)){
				config.setMinSize(Integer.parseInt(val));
			}
			val = properties.getProperty("file.server.pool.idletimeout", THREAD_IDLE_TIMEOUT);
			if(StringUtils.hasText(val)){
				config.setIdleTimeout(Integer.parseInt(val));
			}
			val = properties.getProperty("file.server.pool.threadgroup", THREAD_GROUP);
			if(StringUtils.hasText(val)){
				config.setThreadGroup(val);
			}
			val = properties.getProperty("file.server.file.manager.service", MANAGER_SERVICE);
			if(StringUtils.hasText(val)){
				config.setManagerService(val);
			}
			val = properties.getProperty("file.server.file.upload.service", UPLOAD_SERVICE);
			if(StringUtils.hasText(val)){
				config.setUploadService(val);
			}
			
			config.setProperties(properties);
			config.setStatus(true);
		} catch(Exception e) {
			config.setStatus(false);
			config.setMsg(e.getMessage());
			logger.error("解析文件服务信息配置异常！", e);
		} finally {
			if(stream != null){
				try{
					stream.close();
				}catch(Exception e){
					logger.error("关闭释放文件服务信息配置文件流异常！", e);
				}
			}
		}
		
		logger.info(config);
	}
	
	private static Logger logger = Logger.getLogger(FileserverApplicationInitializer.class);
	private static String configFile = "fileserver.properties";
	private static FileserverConfiguration config;
	
	private final static String MAX_POOL_SIZE = "600";
	private final static String MIN_POOL_SIZE = "15";
	private final static String THREAD_IDLE_TIMEOUT = "60";
	private final static String THREAD_GROUP = "file@server";
	private final static String MANAGER_SERVICE = "/manager";
	private final static String UPLOAD_SERVICE = "/upload";
}
