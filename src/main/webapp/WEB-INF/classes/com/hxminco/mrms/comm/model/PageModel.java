package com.hxminco.mrms.comm.model;

import java.util.List;

public class PageModel<T> {

    private Long total;

    private List<T> rows;

    private int limit = 20;

    private int offset = 0;

    private int page = 0;

    private String order = "asc";

    private boolean success = false;

    private String message;

    public  void setSuccess(boolean success) {
        this.success = success;
    }

    public  boolean getSuccess(){
        return success;
    }

    public  String getMessage() {
        return message;
    }

    public  void setMessage(String message) {
        this.message = message;
    }


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPage() {
        return offset / limit + 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
