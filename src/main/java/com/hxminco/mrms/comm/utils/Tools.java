package com.hxminco.mrms.comm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Tools {
    private static Properties p = new Properties();
  
    /** 
     * 读取properties配置文件信息 
     */  
    static{  
        try {  
            //p.load(Tools.class.getClassLoader().getResourceAsStream("classpath:/application.properties"));
            p.load(new FileInputStream(Tools.class.getResource("/").getPath()+"application.properties"));
        } catch (IOException e) {
            e.printStackTrace();   
        }  
    }  
    /** 
     * 根据key得到value的值 
     */  
    public static String getValue(String key)  
    {
        return p.getProperty(key);
    }


    public static void main(String[] args) {
        String version = getValue("fileNamePrefix");
        System.out.println(version);
        System.out.println(Tools.class.getResource("/").getPath()+"application.properties");
        System.out.println( Thread.currentThread().getContextClassLoader().getResource("").getPath()+"application.properties");
    }
} 