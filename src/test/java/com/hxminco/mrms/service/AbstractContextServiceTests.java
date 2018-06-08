package com.hxminco.mrms.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/spring-*.xml"})
public class AbstractContextServiceTests {


//    public static void main(String[] args) throws IOException {
//        String ident = PCSerialNumberManager.getDiskNumber();
//        System.err.println("ident="+ident);
//        String passwd = PCSerialNumberManager.getSerialNumber("C");
//        System.err.println("passwd="+passwd);
//    }
//public static void main(String[] args) {
//    try {
//        URLConnection connection = new URL("http://192.168.1.61:8080/mrms-server/res/mrms.war").openConnection();
//        InputStream input = connection.getInputStream();
//
//        OutputStream output = new FileOutputStream(new File("E:\\mrms.war"));
//        try {
//            byte[] buffer = new byte[1024];
//            int i = 0;
//            while ((i = input.read(buffer)) != -1) {
//                output.write(buffer, 0, i);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            output.flush();
//            output.close();
//            input.close();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}

//    public List<String> getFileNameList(String ftpDirectory)
//    {
//        List<String> list = new ArrayList<String>();
//        if(!open())
//            return list;
//        try
//        {
//            DataInputStream dis = new  DataInputStream(ftpClient.nameList(ftpDirectory));
//            String filename = "";
//            while((filename=dis.readLine())!=null)
//            {
//                list.add(filename);
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return list;
//    }
}