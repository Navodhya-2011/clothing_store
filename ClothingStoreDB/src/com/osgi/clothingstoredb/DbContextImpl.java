package com.osgi.clothingstoredb;


import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DbContextImpl implements DbContext{
	
	private Connection dbConnection;
	private final String driverName;
	private String ConnectionString;
	private String dbUser;
	private String dbPassword;
	
	//setup db environment
	public DbContextImpl() {
		
		this.driverName = "com.mysql.jdbc.Driver";
		this.ConnectionString = "jdbc:mysql://localhost:3307/clothingdb";
		this.dbUser = "root";
		this.dbPassword = "";
	
	}
	
	@Override
	public Connection getDatabaseConnection() {
		
		try {
			
			Class.forName(driverName);
			dbConnection = (Connection)DriverManager.getConnection(ConnectionString,dbUser,dbPassword);
			
			System.out.println("Database Connection Eshtablished");
			
		}catch(Exception ex) {
			
			System.out.println("dbConnectionError: " + ex.getMessage());
		}
		
		return dbConnection;
	}
	
	

}
