package com.hxminco.mrms.comm.entry;

import java.util.Date;

public class QbPaper {
    private String id;

    private String trainplanid;

    private Integer duration;

    private Integer summark;

    private Integer passmark;

    private Integer status;

    private Date createdate;

    private Integer papernums;

    public QbPaper(String id, String trainplanid, Integer duration, Integer summark, Integer passmark, Integer status, Date createdate, Integer papernums) {
        this.id = id;
        this.trainplanid = trainplanid;
        this.duration = duration;
        this.summark = summark;
        this.passmark = passmark;
        this.status = status;
        this.createdate = createdate;
        this.papernums = papernums;
    }

    public QbPaper() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTrainplanid() {
        return trainplanid;
    }

    public void setTrainplanid(String trainplanid) {
        this.trainplanid = trainplanid == null ? null : trainplanid.trim();
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSummark() {
        return summark;
    }

    public void setSummark(Integer summark) {
        this.summark = summark;
    }

    public Integer getPassmark() {
        return passmark;
    }

    public void setPassmark(Integer passmark) {
        this.passmark = passmark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getPapernums() {
        return papernums;
    }

    @Override
    public String toString() {
        return "QbPaper{" +
                "id='" + id + '\'' +
                ", trainplanid='" + trainplanid + '\'' +
                ", duration=" + duration +
                ", summark=" + summark +
                ", passmark=" + passmark +
                ", status=" + status +
                ", createdate=" + createdate +
                ", papernums=" + papernums +
                '}';
    }

    public void setPapernums(Integer papernums) {
        this.papernums = papernums;
    }


}