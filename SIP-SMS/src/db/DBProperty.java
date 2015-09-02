package db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperty {
	public static String driver;
	public static String url;
	public static String userName;
	public static String userPass;
	
	static{
			
		InputStream in = DBProperty.class.getResourceAsStream("src/db.properties");
		Properties pro=new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver=pro.getProperty("driver").trim();
		url=pro.getProperty("url").trim();
		userName=pro.getProperty("userName").trim();
		userPass=pro.getProperty("userPass").trim();
	}
	
}
