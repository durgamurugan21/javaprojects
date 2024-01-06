package com.landbinami.dbconnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DBConnection {
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int i = 0;
	private boolean validatedataFlag = false;
	
	public Connection getConnection(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
	         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/landbinami", "root", "admin");
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}
		return con;
	}
	


}
