package com.hxminco.mrms.comm.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
	//反序列化成日期对象Date
	@Override
	public Timestamp unmarshal(String str) throws Exception {
		if(str != null){
			return Timestamp.valueOf(str);
		}else {
			return null;
		}
	}

	@Override
	public String marshal(Timestamp v) throws Exception {
		if(v != null){
			return String.valueOf(v);
		}else {
			return null;
		}
	}

}