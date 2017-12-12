package com.stone.tools.database.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.server.ShutdownHandler;
import org.h2.tools.Server;

import com.stone.tools.database.Bootstart;

public class H2Bootstart implements Bootstart {
	public static ThreadLocal<Server> serverLocal = new ThreadLocal<Server>();
	@Override
	public void start() {
		String[] properties = new String[]{
				"-tcpPort", "14023", 
//				"-tcpPassword", "jmei", 
//				"-tcpDaemon", 
				"-tcpShutdown", "true", 
//				"-tcpShutdownForce", 
				"-tcpAllowOthers", 
//				"-tcpSSL",
//				"-ifExists",
				"-baseDir", "/jmei",
//				"-key", "jmei", "jmei",
				"-trace"};
		Server server = null;
		try {
			server = Server.createTcpServer(properties).start();
			server.setOut(System.err);
			server.setShutdownHandler(new ShutdownHandler(){
				@Override
				public void shutdown() {
					System.out.println("Now database will stop ...");
					serverLocal.get().stop();
				}});
			serverLocal.set(server);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		serverLocal.get().shutdown();
	}
	
	public static void main(String[] args) throws Exception{
		Bootstart bootstrap = new H2Bootstart();
		bootstrap.start();
		
		System.out.println(H2Bootstart.serverLocal.get().getStatus());
		System.out.println(H2Bootstart.serverLocal.get().toString());
		System.out.println(H2Bootstart.serverLocal.get());
		
		System.out.println(H2Bootstart.serverLocal.get().toString());
		
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:tcp://192.168.56.1:14023/./data", "jmei", "jmei");
		
		Statement stat = conn.createStatement();
		
//		stat.execute("create table test(id varchar(40), name varchar(40))");
		
		stat.execute("insert into test values('AEO0001', '菩提树下的杨过')");
		stat.execute("insert into test values('AEO0002', '菩提树下的杨过')");
		
		ResultSet result = stat.executeQuery("select * from test ");
		
		int i = 1;
		while (result.next()) {
			System.out.println(i++ + ":\t" + result.getString("id") + "->" + result.getString("name"));
		}
		
		System.out.println(conn);
		
//		bootstrap.stop();
		
	}
}
