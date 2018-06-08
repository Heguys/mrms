package com.hxminco.mrms.comm.model;

/**
 * Created by Employee on 2017/8/14.
 */
import java.util.List;

public class Page<T> {

    private Integer pageno;
    private Integer totalno;
    private Integer totalsize;
    private Integer pagesize;
    private List<T> datas;
    public Integer getPageno() {
        return pageno;
    }
    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }
    public Integer getTotalno() {
        return totalno;
    }
    public void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }
    public Integer getTotalsize() {
        return totalsize;
    }
    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
    }
    public Integer getPagesize() {
        return pagesize;
    }
    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
    public List<T> getDatas() {
        return datas;
    }
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
