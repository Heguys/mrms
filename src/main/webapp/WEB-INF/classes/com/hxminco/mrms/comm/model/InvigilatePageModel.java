package com.hxminco.mrms.comm.model;

/**
 * Created by Employee on 2017/8/15.
 */
public class InvigilatePageModel {
    private String uid;

    private String name;

    private String identityId;

    private String examCode;

    private String photoUrl;

    private String genderId;

    private String trainPlanUid;

    private String status;

    private Double score;

    private Integer examtime;

    private String missingExamination;

    private String ruleBroken;

    private String ruleBrokenAbout;

    private String passed;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getGenderId() {
        if(this.genderId==null){
            return null;
        }else if("male".equals(this.genderId)) {
            return "男";
        }else{
            return "女";
        }
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    public String getTrainPlanUid() {
        return trainPlanUid;
    }

    public void setTrainPlanUid(String trainPlanUid) {
        this.trainPlanUid = trainPlanUid;
    }

    public String getStatus() {
        if("1".equals(this.missingExamination)){
            status="absentIcon";
        }else if("1".equals(this.ruleBroken)){
            status="discIcon";
        }else if(status==null ||"1".equals(status)){
            status="noExamIcon";
        }else if("2".equals(status)){
            status="examingIcon";
        }else if("3".equals(status)){
            status="examedIcon";
        }
      return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getExamtime() {
        return examtime==null?1:examtime;
    }

    public void setExamtime(Integer examtime) {
        this.examtime = examtime;
    }

    public String getMissingExamination() {
        return missingExamination;
    }

    public void setMissingExamination(String missingExamination) {
        this.missingExamination = missingExamination;
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

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }
}
