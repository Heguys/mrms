package com.hxminco.mrms.comm.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GetFileName
{
    public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }
    public static void getAllFileName(String path,ArrayList<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
        fileName.addAll(Arrays.asList(names));
        if(files != null && files.length > 0) {
            for (File a : files) {
                if (a.isDirectory()) {
                    getAllFileName(a.getAbsolutePath(), fileName);
                }
            }
        }
    }
}