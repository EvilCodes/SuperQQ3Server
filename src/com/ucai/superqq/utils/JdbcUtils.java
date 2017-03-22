package com.ucai.superqq.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtils {

	static{
		/*
		 * 加载jdbc驱动程序
		 */
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getValue("dbpath"));
		getConnection();
	}
	
	public static Connection getConnection(){
		try {
			String path = PropertiesUtil.getValue("dbpath");
			System.out.println("-------------------"+path);
			Connection connection = DriverManager.getConnection(
				"jdbc:sqlite://"+path);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeAll(ResultSet set,PreparedStatement statement,Connection connection){
		try {
			if(set!=null){
				set.close();
			}
			if(statement!=null){
				statement.close();
			}
			if(connection!=null){
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
