package com.mohit.springfirstproject;

//Importing all SQL classes
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.*;

public class Connect{

//Object of Connection class
//initially assigned NULL
static Connection con = null;

public static Connection connectDB()
{
	
	try
	{


	    ReadProperties ppt = new ReadProperties();
	    Properties prop = new Properties();
	    prop = ppt.main(null);
	    
        String dbDriverClass = prop.getProperty("db.driver.class");
        String dbConnUurl = prop.getProperty("db.conn.url");
        String dbUsername = prop.getProperty("db.username");
        String dbPassword = prop.getProperty("db.password");

        System.out.println("WelcomeController db.driver.class = "+dbDriverClass);
        System.out.println("WelcomeController db.conn.url = "+dbConnUurl);
        System.out.println("WelcomeController db.username = "+dbUsername);
        System.out.println("WelcomeController db.password = "+dbPassword);
        
		Class.forName(dbDriverClass);

		con = DriverManager.getConnection(
				dbConnUurl, //for oracle
				dbUsername, 
				dbPassword);
			
		return con;
	}

	catch (SQLException | ClassNotFoundException e)
	{
		System.out.println(e);

		return null;
	}
}
}
