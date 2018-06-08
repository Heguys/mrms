package com.hxminco.mrms.comm.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Employee on 2017/7/21.
 */
public class ExamPlanData {
    private String trainPlanUid;
    private String trainInstitutionName;
    private String trainPlanName;
    private String qualificationName;
    private String assessmentName;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date examBeginDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date examEndDate;
    private Integer examLimit;
    private String status;
    private String makeUpPassword;
    private Timestamp updateTime;


    public String getTrainPlanUid() {
        return trainPlanUid;
    }

    public void setTrainPlanUid(String trainPlanUid) {
        this.trainPlanUid = trainPlanUid;
    }

    public String getTrainInstitutionName() {
        return trainInstitutionName;
    }

    public void setTrainInstitutionName(String trainInstitutionName) {
        this.trainInstitutionName = trainInstitutionName;
    }

    public String getTrainPlanName() {
        return trainPlanName;
    }

    public void setTrainPlanName(String trainPlanName) {
        this.trainPlanName = trainPlanName;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getExamBeginDate() {
        return examBeginDate;
    }

    public void setExamBeginDate(Date examBeginDate) {
        this.examBeginDate = examBeginDate;
    }

    public Date getExamEndDate() {
        return examEndDate;
    }

    public void setExamEndDate(Date examEndDate) {
        this.examEndDate = examEndDate;
    }

    public Integer getExamLimit() {
        return examLimit;
    }

    public void setExamLimit(Integer examLimit) {
        this.examLimit = examLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getMakeUpPassword() {
        return makeUpPassword;
    }

    public void setMakeUpPassword(String makeUpPassword) {
        this.makeUpPassword = makeUpPassword;
    }
}
