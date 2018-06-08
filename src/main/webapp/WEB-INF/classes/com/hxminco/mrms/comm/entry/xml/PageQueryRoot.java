package com.hxminco.mrms.comm.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XStreamAlias("root")
public class PageQueryRoot {
	@XStreamAsAttribute
	private Auth auth;
	@XStreamAsAttribute
	private PageQuery pageQuery;
	public Auth getAuth() {
		return auth;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	public PageQuery getPageQuery() {
		return pageQuery;
	}
	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}

	public Map<String, Object> getAllFilters() {
		if (pageQuery == null) {
			return null;
		}
		List<Filter> filters = pageQuery.getFilters();
		Map<String, Object> mpFilter = new HashMap<String, Object>();

		for (Filter one : filters) {
			//XmlSqlUtil.covertFilter2Condition(one);
			mpFilter.put(one.getField(), one.getValue());
		}
		return mpFilter;
	}

	public Map<String, Object> getAllOrders() {
		if (pageQuery == null) {
			return null;
		}
		List<Order> orders = pageQuery.getOrders();
		Map<String, Object> mpOrders = new HashMap<String, Object>();

		for (Order one : orders) {
			mpOrders.put(one.getField(), one.getDirection());
		}
		return mpOrders;
	}

	public List<String> getAllReturns() {
		if (pageQuery == null) {
			return null;
		}
		List<Return> returns = pageQuery.getReturns();
		List<String> strReturn = new ArrayList<>();
		for (Return one : returns) {
			strReturn.add(one.getField());
		}
		return strReturn;
	}



}
