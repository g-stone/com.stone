package com.stone.tools.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;

import com.stone.tools.database.h2.H2Bootstart;

/**
 * H2数据库初始化
 * @author dba
 */
public class DatabaseApplicationInitializer implements WebApplicationInitializer {
	private static Logger logger = LoggerFactory.getLogger(DatabaseApplicationInitializer.class);
	public static H2Bootstart bootstrap;
	private static String init = "jmei.sql";
	private boolean isExecuteDrop = false;
	private boolean isExecuteInit = false;
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		long startTime = System.currentTimeMillis();
		bootstrap = new H2Bootstart();
		bootstrap.start();
		
		long endTime = System.currentTimeMillis();
		
		logger.info(H2Bootstart.serverLocal.get().getStatus());
		logger.info(H2Bootstart.serverLocal.get().toString());
		
		if(isExecuteDrop){
			dropTask();
		}
		if(isExecuteInit){
			initTask(servletContext);
		}
		
		logger.info("启动{}在{}耗费{}...", "H2", new Date(System.currentTimeMillis()), endTime - startTime);
	}
	
	private void dropTask(){
		Connection conn = null;
		Statement stat = null;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:tcp://192.168.56.1:14023/./data", "jmei", "jmei");
			stat = conn.createStatement();
			
			logger.info("销毁表{}结果{}...", "t_article", stat.execute("drop table t_article if exists"));
			logger.info("销毁表{}结果{}...", "t_article_category", stat.execute("drop table t_article_category if exists"));
			logger.info("销毁表{}结果{}...", "t_case", stat.execute("drop table t_case if exists"));
			logger.info("销毁表{}结果{}...", "t_case_category", stat.execute("drop table t_case_category if exists"));
			logger.info("销毁表{}结果{}...", "t_case_images", stat.execute("drop table t_case_images if exists"));
			logger.info("销毁表{}结果{}...", "t_idle", stat.execute("drop table t_idle if exists"));
			logger.info("销毁表{}结果{}...", "t_industry_category", stat.execute("drop table t_industry_category if exists"));
			logger.info("销毁表{}结果{}...", "t_site_images", stat.execute("drop table t_site_images if exists"));
		} catch(Exception e) {
			logger.error("数据库表销毁异常{}...", e.getMessage());
		} finally {
			try{
				stat.close();
				conn.close();
			}catch(Exception e){
				logger.error("数据库关闭异常{}...", e.getMessage());
			}
		}
	}
	
	private void initTask(ServletContext servletContext){
		Connection conn = null;
		Statement stat = null;
		try {
			InputStream stream = servletContext.getResourceAsStream("/WEB-INF/classes/" + init);
			
			if(stream == null){
				logger.info("初始化文件配置不正确...");
				return;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:tcp://192.168.56.1:14023/./data", "jmei", "jmei");
			stat = conn.createStatement();
			
			String line = null;
			while((line = reader.readLine()) != null){
				logger.info(line);
				if(StringUtils.hasText(line)){
					logger.info("执行SQL{}结果{}...", line, stat.execute(line));
				}
			}
		} catch(Exception e) {
			logger.error("执行SQL异常{}...", e.getMessage());
			e.printStackTrace();
		} finally {
			try{
				stat.close();
				conn.close();
			}catch(Exception e){
				logger.error("数据库关闭异常{}...", e.getMessage());
			}
		}
	}
}
