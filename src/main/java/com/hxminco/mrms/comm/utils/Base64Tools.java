package com.hxminco.mrms.comm.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class Base64Tools {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public static byte[] encode(byte[] bytes){
		return Base64.encodeBase64(bytes);
	}
	  
	public static String encode2String(byte[] bytes){
		return Base64.encodeBase64String(bytes);
	}
	  
	public static String encode2String(String targetString){
	    byte[] bytes = targetString.getBytes(DEFAULT_CHARSET);
	    return Base64.encodeBase64String(bytes);
	}
	
	public static String encode2FileUrl(String url){
		InputStream in =null;
		try {
			in = new FileInputStream(url);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			String base64String= Base64.encodeBase64String(bytes);
			return base64String;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String encode2FileUrl(InputStream inputStream){
		try {
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			String base64String= Base64.encodeBase64String(bytes);
			return base64String;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static byte[] encode2Byte(String targetString){
	    byte[] bytes = targetString.getBytes(DEFAULT_CHARSET);
	    return Base64.encodeBase64(bytes);
	}
	  
	public static byte[] decode(byte[] bytes){
	    return Base64.decodeBase64(bytes);
	}
	  
	public static String decode(String targetString){
	    return new String(Base64.decodeBase64(targetString));
	}
	  
	public static byte[] decode2Byte(String targetString){
	    byte[] bytes = targetString.getBytes(DEFAULT_CHARSET);
	    return Base64.decodeBase64(bytes);
	}
	
	public static String decode2String(String targetString){
	    byte[] bytes = targetString.getBytes(DEFAULT_CHARSET);
	    return new String(decode(bytes));
	}

}
