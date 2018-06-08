package com.hxminco.mrms.comm.entry;

import java.util.Calendar;
import java.util.Date;

public class QbExamInfo {
    private String id;

    private String examcode;

    private Date begintime;

    private Date endtime;

    private Integer passtime;

    private String status;

    private Integer examtime;

    private Integer makeup;

    private Double score;

    private Integer paperOrder;

    private String passed;

    private String ruleBroken;

    private Integer duration;

    public QbExamInfo(String id, String examcode, Date begintime, Date endtime, Integer passtime, String status, Integer examtime, Integer makeup, Double score, Integer paperOrder, String passed, String ruleBroken) {
        this.id = id;
        this.examcode = examcode;
        this.begintime = begintime;
        this.endtime = endtime;
        this.passtime = passtime;
        this.status = status;
        this.examtime = examtime;
        this.makeup = makeup;
        this.score = score;
        this.paperOrder = paperOrder;
        this.passed = passed;
        this.ruleBroken = ruleBroken;
    }

    public QbExamInfo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getExamcode() {
        return examcode;
    }

    public void setExamcode(String examcode) {
        this.examcode = examcode == null ? null : examcode.trim();
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getPasstime() {
        return passtime;
    }

    public void setPasstime(Integer passtime) {
        this.passtime = passtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getExamtime() {
        return examtime;
    }

    public void setExamtime(Integer examtime) {
        this.examtime = examtime;
    }

    public Integer getMakeup() {
        return makeup;
    }

    public void setMakeup(Integer makeup) {
        this.makeup = makeup;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getPaperOrder() {
        return paperOrder;
    }

    public void setPaperOrder(Integer paperOrder) {
        this.paperOrder = paperOrder;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public String getRuleBroken() {
        return ruleBroken;
    }

    public void setRuleBroken(String ruleBroken) {
        this.ruleBroken = ruleBroken;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getExamCommitTime() {
        Date commitTime = null;
        if (getBegintime() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getBegintime());
            calendar.add(Calendar.MINUTE, getDuration());
            commitTime = calendar.getTime();
        }
        return commitTime;
    }

    public long getExamRemainSeconds() {
        Date now = new Date();
        long miniSeconds = getBegintime().getTime() + getDuration() * 60000 - now.getTime();
        if(miniSeconds < 0){
            return 0;
        }else{
            return miniSeconds / 1000;
        }
    }
}