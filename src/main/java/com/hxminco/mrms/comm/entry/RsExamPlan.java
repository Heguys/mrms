package com.hxminco.mrms.comm.entry;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RsExamPlan {
    private String uid;

    private String institutionUid;

    private String trainPlanUid;

    private BigDecimal examBeginDate;

    private BigDecimal examEndDate;

    private Integer allowExamTimes;

    private String paperForm;

    private Integer examLimit;

    public RsExamPlan(String uid, String institutionUid, String trainPlanUid, BigDecimal examBeginDate, BigDecimal examEndDate, Integer allowExamTimes, String paperForm, Integer examLimit) {
        this.uid = uid;
        this.institutionUid = institutionUid;
        this.trainPlanUid = trainPlanUid;
        this.examBeginDate = examBeginDate;
        this.examEndDate = examEndDate;
        this.allowExamTimes = allowExamTimes;
        this.paperForm = paperForm;
        this.examLimit = examLimit;
    }

    public RsExamPlan() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getInstitutionUid() {
        return institutionUid;
    }

    public void setInstitutionUid(String institutionUid) {
        this.institutionUid = institutionUid == null ? null : institutionUid.trim();
    }

    public String getTrainPlanUid() {
        return trainPlanUid;
    }

    public void setTrainPlanUid(String trainPlanUid) {
        this.trainPlanUid = trainPlanUid == null ? null : trainPlanUid.trim();
    }

    public BigDecimal getExamBeginDate() {
        return examBeginDate;
    }

    public void setExamBeginDate(BigDecimal examBeginDate) {
        this.examBeginDate = examBeginDate;
    }

    public BigDecimal getExamEndDate() {
        return examEndDate;
    }

    public void setExamEndDate(BigDecimal examEndDate) {
        this.examEndDate = examEndDate;
    }

    public Integer getAllowExamTimes() {
        return allowExamTimes;
    }

    public void setAllowExamTimes(Integer allowExamTimes) {
        this.allowExamTimes = allowExamTimes;
    }

    public String getPaperForm() {
        return paperForm;
    }

    public void setPaperForm(String paperForm) {
        this.paperForm = paperForm == null ? null : paperForm.trim();
    }

    public Integer getExamLimit() {
        return examLimit;
    }

    public void setExamLimit(Integer examLimit) {
        this.examLimit = examLimit;
    }
}