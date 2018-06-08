package com.hxminco.mrms.service;


import com.hxminco.mrms.comm.utils.Tools;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Employee on 2017/9/15.
 */
public class TestString {
    public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("0000000000000004500");
        BigDecimal decimal1 = new BigDecimal("45.00").multiply(new BigDecimal(100));
        System.out.println(decimal);
        System.out.println(decimal1);
        System.out.println(decimal1.equals(decimal));
    }

    public static List<String> read(File file) {
        List<String> list = new ArrayList<>();
            BufferedReader br = null;
            String regex = "\\s+";
        try {
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String str = null;
            while ((str = br.readLine()) != null) {//使用readLine方法，一次读一行
                str = str.trim().replaceAll(regex, " ");
                String[] split = str.split(" ");
                str = split[1];
                list.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}

