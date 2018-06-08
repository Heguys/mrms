package com.hxminco.mrms.comm.entry;

public class ExamRecord {
    private String trainPlanUid;

    private Integer missExamCount;

    private Integer brokenRuleCount;

    private String invigilator;

    public ExamRecord(String trainPlanUid, Integer missExamCount, Integer brokenRuleCount, String invigilator) {
        this.trainPlanUid = trainPlanUid;
        this.missExamCount = missExamCount;
        this.brokenRuleCount = brokenRuleCount;
        this.invigilator = invigilator;
    }

    public ExamRecord() {
        super();
    }

    public String getTrainPlanUid() {
        return trainPlanUid;
    }

    public void setTrainPlanUid(String trainPlanUid) {
        this.trainPlanUid = trainPlanUid == null ? null : trainPlanUid.trim();
    }

    public Integer getMissExamCount() {
        return missExamCount;
    }

    public void setMissExamCount(Integer missExamCount) {
        this.missExamCount = missExamCount;
    }

    public Integer getBrokenRuleCount() {
        return brokenRuleCount;
    }

    public void setBrokenRuleCount(Integer brokenRuleCount) {
        this.brokenRuleCount = brokenRuleCount;
    }

    public String getInvigilator() {
        return invigilator;
    }

    public void setInvigilator(String invigilator) {
        this.invigilator = invigilator == null ? null : invigilator.trim();
    }
}