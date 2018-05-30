package com.ia.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class PropertierUtil {

	public static String getValueByKey(String key) {
		 
        Properties prop = new Properties();     
        try{
        	//获取根路径
        	String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            //读取属性文件Order.properties
            InputStream in = new BufferedInputStream (new FileInputStream(path+"Order.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
//            while(it.hasNext()){
//                key=it.next();
//                System.out.println(key+":"+prop.getProperty(key));
//            }
            in.close();  
            return prop.getProperty(key);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    
	}
	
	 public static void main(String[] args) { 
//	        Properties prop = new Properties();     
//	        try{
//	        	String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//	            //读取属性文件Order.properties
//	            InputStream in = new BufferedInputStream (new FileInputStream(path+"Order.properties"));
//	            prop.load(in);     ///加载属性列表
//	            Iterator<String> it=prop.stringPropertyNames().iterator();
//	            while(it.hasNext()){
//	                String key=it.next();
//	                System.out.println(key+":"+prop.getProperty(key));
//	            }
//	            in.close();
//	            
//	            ///保存属性到Order.properties文件
//	            FileOutputStream oFile = new FileOutputStream("Order.properties", true);//true表示追加打开
//	            prop.setProperty("phone", "10086");
//	            prop.store(oFile, "The New properties file");
//	            oFile.close();
//	        }
//	        catch(Exception e){
//	            System.out.println(e);
//	        }
		 System.out.println(getValueByKey("Auto"));
	    } 
}
