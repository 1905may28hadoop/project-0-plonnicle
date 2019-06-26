package com.revature.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Connection conn = null;
	
	// This static block will run before anything -- when the class is loaded
	// It's not strictly necessary but it will tell us if we're missing
	// Our driver.
	
	static {
		try {
			//This line check for the Class of our Driver and loads it
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		try{
				// In here we need our url, username, and password for our DB
				// But we don't want to hardcode. We'll use Properties (class).
			Properties properties = new Properties();
			// Ve vant to loader properties from file. BUT ze path might changier
			// depending on how ve build our projekt.
			// Instead of hardcoding, we'll look on the classpath.
			// It involves quite a few method calls.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			properties.load(loader.getResourceAsStream("connection.properties"));
			
			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			
			// Now we actually make the connection:
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("GONNECTED.");
			
		}catch(IOException | SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
}
