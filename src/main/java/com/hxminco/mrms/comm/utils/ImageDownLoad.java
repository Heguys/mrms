package com.hxminco.mrms.comm.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Employee on 2017/8/21.
 */
public class ImageDownLoad {

    public static void saveToFile(Map<String,String> srcMap) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        String destUrl = "";
        String imgFilePath ="";
        try {
            Map.Entry<String, String> next = null;
            Iterator<Map.Entry<String, String>> it = srcMap.entrySet().iterator();
            while (it.hasNext()){
                next = it.next();
                destUrl = next.getKey();
                imgFilePath = next.getValue();
                url = new URL(destUrl);
                httpUrl = (HttpURLConnection) url.openConnection();
                httpUrl.connect();
                bis = new BufferedInputStream(httpUrl.getInputStream());
                fos = new FileOutputStream(String.valueOf(new File(imgFilePath)));
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
                fos.flush();
            }
        } catch (IOException e) {
        } catch (ClassCastException e) {
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }
        }
    }
}