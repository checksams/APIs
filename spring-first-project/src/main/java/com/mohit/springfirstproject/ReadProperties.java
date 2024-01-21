package com.mohit.springfirstproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    public static Properties main(String[] args) {

        File file = new File(ReadProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String absolutePath = file.getAbsolutePath();
        System.out.println("Absolute Path: " + absolutePath);

        String osName = System.getProperty("os.name").toLowerCase();
        String fileSeparator = File.separator;

        if (osName.contains("windows")) {
            //System.out.println("File system is Windows");
            //System.out.println("File separator: " + fileSeparator);
            absolutePath = absolutePath + "\\com\\mohit\\springfirstproject\\config.properties";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            //System.out.println("File system is Unix-based");
            //System.out.println("File separator: " + fileSeparator);
            absolutePath = absolutePath + "/com/mohit/springfirstproject/config.properties";
        } else {
            System.out.println("Unknown operating system");
        }
        
        System.out.println("Absolute Path: " + absolutePath);

        Properties prop = new Properties();
        try {
        	String currDir = System.getProperty("user.dir");
        	System.out.println("Current dir="+currDir);
        	//InputStream input = ReadProperties.class.getClassLoader().getResourceAsStream(absolutePath);
            FileInputStream input= new FileInputStream(absolutePath);
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return prop;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            System.out.println("db.conn.url = "+prop.getProperty("db.conn.url"));
            System.out.println("db.username = "+prop.getProperty("db.username"));
            System.out.println("db.password = "+prop.getProperty("db.password"));
			
			return prop;
			
        } catch (IOException ex) {
            ex.printStackTrace();
		}finally{
			prop= null;
		}
		
		return prop;

    }

}