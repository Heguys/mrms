package com.hxminco.mrms.comm.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("return")
public class Return {
	@XStreamAsAttribute
	private String  field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
