package com.hxminco.mrms.comm.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("filter")
public class Filter {
	@XStreamAsAttribute
	private String field;
	@XStreamAsAttribute
	private String value;
	@XStreamAsAttribute
	private String match;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	
}
