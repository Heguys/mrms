package com.hxminco.mrms.comm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils{
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String join(Object... ss){
		if(null == ss){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (Object s : ss) {
			sb.append("\t").append(s);
		}
		return sb.toString();
	}

	public static boolean isExcel2003(String fileName){
		return fileName.matches("^.+\\.(?i)(xls)$");
	}

	public static boolean isExcelMore2007(String fileName){
		return fileName.matches("^.+\\.(?i)(xlsx)$");
	}

	public static <T> boolean  splitListTo(List<T> srcList, List<List<T>> destList, int cnt){
		if(srcList == null || destList == null){
			return false;
		}

		if(srcList.size() <= cnt){
			destList.add(srcList);
			return true;
		}

		int count = 0;
		List<T> lstTemp = new ArrayList<>();
		for(T one : srcList){
			if(count == cnt){
				count = 0;
				destList.add(lstTemp);
				lstTemp = new ArrayList<>();
			}
			lstTemp.add(one);
			count++;
		}
		destList.add(lstTemp);
		return true;
	}

	public static boolean marchers(String regex,String str) {
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(str);
		boolean b = matcher.matches();
		return b;
	}
}
