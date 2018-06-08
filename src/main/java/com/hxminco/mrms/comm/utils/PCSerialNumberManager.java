package com.hxminco.mrms.comm.utils;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Employee on 2017/9/4.
 */
public class PCSerialNumberManager {
    public static String getSerialNumber(String drive) {
        StringBuilder result = new StringBuilder();
        BufferedReader input = null;
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    +"Set colDrives = objFSO.Drives\n"
                    +"Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    +"Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            input = new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString().trim();
    }

    public static String getDiskNumber() throws IOException {
        Process process = Runtime.getRuntime().exec(
                new String[] { "wmic", "cpu", "get", "ProcessorId" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        return serial;
    }
}
