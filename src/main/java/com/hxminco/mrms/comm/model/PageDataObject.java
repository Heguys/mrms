package com.hxminco.mrms.comm.model;

/**
 * Created by Employee on 2017/9/11.
 */
public class PageDataObject {
    private String trainPlanUid;
    private String trainInstitutionName;
    private String trainPlanName;
    private String qualificationName;
    private String assessmentName;
    private String examBeginDate;
    private String examEndDate;
    private String examLimit;
    private String status;

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

    public String getExamBeginDate() {
        return examBeginDate;
    }

    public void setExamBeginDate(String examBeginDate) {
        this.examBeginDate = examBeginDate;
    }

    public String getExamEndDate() {
        return examEndDate;
    }

    public void setExamEndDate(String examEndDate) {
        this.examEndDate = examEndDate;
    }

    public String getExamLimit() {
        return examLimit;
    }

    public void setExamLimit(String examLimit) {
        this.examLimit = examLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
