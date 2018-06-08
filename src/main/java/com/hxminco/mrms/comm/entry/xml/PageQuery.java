package com.hxminco.mrms.comm.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("pageQuery")
public class PageQuery {
	@XStreamAsAttribute
	private String start;
	@XStreamAsAttribute
	private String limit;
	@XStreamImplicit
	private List<Filter> filters;
	@XStreamImplicit
	private List<Order> orders;
	@XStreamImplicit
	private List<Return> returns;
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public List<Return> getReturns() {
		return returns;
	}
	public void setReturns(List<Return> returns) {
		this.returns = returns;
	}
	
}
