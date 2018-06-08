package com.hxminco.mrms.comm.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@XStreamAlias("root")
public class PageDataRoot {
	@XStreamAsAttribute
	private PageData pageData;

	public PageDataRoot(){

	}

	public PageDataRoot(Integer start, Integer limit, Integer total ,List<Record> records){
		pageData = new PageData();
		pageData.setStart(start.toString());
		pageData.setLimit(limit.toString());
		pageData.setTotal(total.toString());
		pageData.setRecords(records);
	}
	public PageDataRoot(Integer start, Integer limit, Integer total ){
		pageData = new PageData();
		pageData.setStart(start.toString());
		pageData.setLimit(limit.toString());
		pageData.setTotal(total.toString());
	}


	public PageData getPageData() {
		return pageData;
	}

	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}

	public <T> Record makeRecord(List<String> strReturn, T obj){
		Record record = new Record();
		List<Field> fields = new ArrayList<Field>();
		for (String one : strReturn) {//递归实体
			Field field = new Field();
			Method meth = null;//为二级节点添加属性，属性值为对应属性的值
			try {
				meth = obj.getClass().getMethod("get" + one.substring(0, 1).toUpperCase() + one.substring(1));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			try {
				field.setName(one);
				field.setValue(meth.invoke(obj) == null ? "" : meth.invoke(obj).toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			fields.add(field);
		}
		record.setFields(fields);
		return record;
	}
}
