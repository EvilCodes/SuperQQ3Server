package com.ucai.superqq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {
	public PropertiesUtil(){
		
	}
	public static void main(String[] args) {
		System.out.println(getValue("dbpath"));
	}
	public static final File file = new File(PropertiesUtil.class.getResource("/").getPath()+"/path.properties");
	public static String getValue(String key) {
		String result = null;
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			result = (String) properties.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
