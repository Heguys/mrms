package com.hxminco.mrms.comm.entry;

public class RsTrainPlan {
    private String uid;

    private String institutionUid;

    private String name;

    private String qualificationId;

    private String certificateTypeId;

    private String companyTypeId;

    private String workClassId;

    private String workItemId;

    private String assessmentNatureId;

    private Integer dataYear;

    private Integer trainLimit;

    private String examFinished;

    private String theoryTrainResultCommitted;

    private Long examFinishedTime;

    private String status;

    private String makeUpPassword;
    private Long updateTime;

    public RsTrainPlan(String uid, String institutionUid, String name, String qualificationId, String certificateTypeId, String companyTypeId, String workClassId, String workItemId, String assessmentNatureId, Integer dataYear, Integer trainLimit, String examFinished, String theoryTrainResultCommitted, Long examFinishedTime, String status, String makeUpPassword, Long updateTime) {
        this.uid = uid;
        this.institutionUid = institutionUid;
        this.name = name;
        this.qualificationId = qualificationId;
        this.certificateTypeId = certificateTypeId;
        this.companyTypeId = companyTypeId;
        this.workClassId = workClassId;
        this.workItemId = workItemId;
        this.assessmentNatureId = assessmentNatureId;
        this.dataYear = dataYear;
        this.trainLimit = trainLimit;
        this.examFinished = examFinished;
        this.theoryTrainResultCommitted = theoryTrainResultCommitted;
        this.examFinishedTime = examFinishedTime;
        this.status = status;
        this.makeUpPassword = makeUpPassword;
        this.updateTime = updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public RsTrainPlan() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getQualificationId() {
      return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId == null ? null : qualificationId.trim();
    }

    public String getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(String certificateTypeId) {
        this.certificateTypeId = certificateTypeId == null ? null : certificateTypeId.trim();
    }

    public String getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(String companyTypeId) {
        this.companyTypeId = companyTypeId == null ? null : companyTypeId.trim();
    }

    public String getWorkClassId() {
        return workClassId;
    }

    public void setWorkClassId(String workClassId) {
        this.workClassId = workClassId == null ? null : workClassId.trim();
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId == null ? null : workItemId.trim();
    }

    public String getAssessmentNatureId() {
        return assessmentNatureId;
    }

    public void setAssessmentNatureId(String assessmentNatureId) {
        this.assessmentNatureId = assessmentNatureId == null ? null : assessmentNatureId.trim();
    }

    public Integer getDataYear() {
        return dataYear;
    }

    public void setDataYear(Integer dataYear) {
        this.dataYear = dataYear;
    }

    public Integer getTrainLimit() {
        return trainLimit;
    }

    public void setTrainLimit(Integer trainLimit) {
        this.trainLimit = trainLimit;
    }

    public String getExamFinished() {
        return examFinished;
    }

    public void setExamFinished(String examFinished) {
        this.examFinished = examFinished == null ? null : examFinished.trim();
    }

    public String getTheoryTrainResultCommitted() {
        return theoryTrainResultCommitted;
    }

    public void setTheoryTrainResultCommitted(String theoryTrainResultCommitted) {
        this.theoryTrainResultCommitted = theoryTrainResultCommitted == null ? null : theoryTrainResultCommitted.trim();
    }

    public Long getExamFinishedTime() {
        return examFinishedTime;
    }

    public void setExamFinishedTime(Long examFinishedTime) {
        this.examFinishedTime = examFinishedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMakeUpPassword() {
        return makeUpPassword;
    }

    public void setMakeUpPassword(String makeUpPassword) {

        this.makeUpPassword = makeUpPassword;
    }

    public Long getUpdateTime() {
        return updateTime;
    }
}