package com.hxminco.mrms.comm.entry;

public class RsStudentInfo {
    private String uid;

    private String candidateUid;

    private String name;

    private String identityTypeId;

    private String identityId;

    private String examCode;

    private String identityName;

    private String photoUrl;

    private String genderId;

    private String trainPlanUid;

    private String examPlanUid;

    private String qualificationId;

    private String certificateTypeId;

    private String companyTypeId;

    private String workClassId;

    private String workItemId;

    private Double theoryExamScore;

    private Double theoryExamScore1;

    private Double theoryExamScore2;

    private String theoryExamPassed;

    private String trainInstitutionUid;

    private Integer examFinishTimes;

    private String ruleBroken;

    private String ruleBrokenAbout;

    private String missingExamination;

    private String makeUp;

    private String isExamFinished;

    public RsStudentInfo(String uid, String candidateUid, String name, String identityTypeId, String identityId, String examCode, String identityName, String photoUrl, String genderId, String trainPlanUid, String examPlanUid, String qualificationId, String certificateTypeId, String companyTypeId, String workClassId, String workItemId, Double theoryExamScore, Double theoryExamScore1, Double theoryExamScore2, String theoryExamPassed, String trainInstitutionUid, Integer examFinishTimes, String ruleBroken, String ruleBrokenAbout, String missingExamination, String makeUp, String isExamFinished) {
        this.uid = uid;
        this.candidateUid = candidateUid;
        this.name = name;
        this.identityTypeId = identityTypeId;
        this.identityId = identityId;
        this.examCode = examCode;
        this.identityName = identityName;
        this.photoUrl = photoUrl;
        this.genderId = genderId;
        this.trainPlanUid = trainPlanUid;
        this.examPlanUid = examPlanUid;
        this.qualificationId = qualificationId;
        this.certificateTypeId = certificateTypeId;
        this.companyTypeId = companyTypeId;
        this.workClassId = workClassId;
        this.workItemId = workItemId;
        this.theoryExamScore = theoryExamScore;
        this.theoryExamScore1 = theoryExamScore1;
        this.theoryExamScore2 = theoryExamScore2;
        this.theoryExamPassed = theoryExamPassed;
        this.trainInstitutionUid = trainInstitutionUid;
        this.examFinishTimes = examFinishTimes;
        this.ruleBroken = ruleBroken;
        this.ruleBrokenAbout = ruleBrokenAbout;
        this.missingExamination = missingExamination;
        this.makeUp = makeUp;
        this.isExamFinished = isExamFinished;
    }

    public RsStudentInfo() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public String getCandidateUid() {
        return candidateUid;
    }

    public void setCandidateUid(String candidateUid) {
        this.candidateUid = candidateUid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentityTypeId() {
        return identityTypeId;
    }

    public void setIdentityTypeId(String identityTypeId) {
        this.identityTypeId = identityTypeId;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode == null ? null : examCode.trim();
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName == null ? null : identityName.trim();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    public String getGenderId() {
      return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId == null ? null : genderId.trim();
    }

    public String getTrainPlanUid() {
        return trainPlanUid;
    }

    public void setTrainPlanUid(String trainPlanUid) {
        this.trainPlanUid = trainPlanUid == null ? null : trainPlanUid.trim();
    }

    public String getExamPlanUid() {
        return examPlanUid;
    }

    public void setExamPlanUid(String examPlanUid) {
        this.examPlanUid = examPlanUid == null ? null : examPlanUid.trim();
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

    public Double getTheoryExamScore() {
        return theoryExamScore;
    }

    public void setTheoryExamScore(Double theoryExamScore) {
        this.theoryExamScore = theoryExamScore;
    }

    public Double getTheoryExamScore1() {
        return theoryExamScore1;
    }

    public void setTheoryExamScore1(Double theoryExamScore1) {
        this.theoryExamScore1 = theoryExamScore1;
    }

    public Double getTheoryExamScore2() {
        return theoryExamScore2;
    }

    public void setTheoryExamScore2(Double theoryExamScore2) {
        this.theoryExamScore2 = theoryExamScore2;
    }

    public String getTheoryExamPassed() {
        return theoryExamPassed;
    }

    public void setTheoryExamPassed(String theoryExamPassed) {
        this.theoryExamPassed = theoryExamPassed == null ? null : theoryExamPassed.trim();
    }

    public String getTrainInstitutionUid() {
        return trainInstitutionUid;
    }

    public void setTrainInstitutionUid(String trainInstitutionUid) {
        this.trainInstitutionUid = trainInstitutionUid == null ? null : trainInstitutionUid.trim();
    }

    public Integer getExamFinishTimes() {
        return examFinishTimes;
    }

    public void setExamFinishTimes(Integer examFinishTimes) {
        this.examFinishTimes = examFinishTimes;
    }

    public String getRuleBroken() {
        return ruleBroken;
    }

    public void setRuleBroken(String ruleBroken) {
        this.ruleBroken = ruleBroken;
    }

    public String getRuleBrokenAbout() {
        return ruleBrokenAbout;
    }

    public void setRuleBrokenAbout(String ruleBrokenAbout) {
        this.ruleBrokenAbout = ruleBrokenAbout;
    }

    public String getMissingExamination() {
        return missingExamination;
    }

    public void setMissingExamination(String missingExamination) {
        this.missingExamination = missingExamination;
    }

    public String getMakeUp() {
        return makeUp;
    }

    public void setMakeUp(String makeUp) {
        this.makeUp = makeUp;
    }

    public String getIsExamFinished() {
        return isExamFinished;
    }

    public void setIsExamFinished(String isExamFinished) {
        this.isExamFinished = isExamFinished;
    }
}