package com.hxminco.mrms.comm.model;

/**
 * Created by Employee on 2017/8/15.
 */
public class InvigilateStatistics {
    private Integer totalStudentCount;

    private Integer untestedStudentCount;

    private Integer testedStudentCount;

    private Integer ontestStudentCount;

    private Integer missExamStudentCount;

    private Integer brokenRuleStudentCount;

    public InvigilateStatistics(Integer totalStudentCount, Integer untestedStudentCount, Integer testedStudentCount, Integer ontestStudentCount, Integer missExamStudentCount, Integer brokenRuleStudentCount) {
        this.totalStudentCount = totalStudentCount;
        this.untestedStudentCount = untestedStudentCount;
        this.testedStudentCount = testedStudentCount;
        this.ontestStudentCount = ontestStudentCount;
        this.missExamStudentCount = missExamStudentCount;
        this.brokenRuleStudentCount = brokenRuleStudentCount;
    }

    public Integer getTotalStudentCount() {
        return totalStudentCount;
    }

    public void setTotalStudentCount(Integer totalStudentCount) {
        this.totalStudentCount = totalStudentCount;
    }

    public Integer getUntestedStudentCount() {
        return untestedStudentCount;
    }

    public void setUntestedStudentCount(Integer untestedStudentCount) {
        this.untestedStudentCount = untestedStudentCount;
    }

    public Integer getTestedStudentCount() {
        return testedStudentCount;
    }

    public void setTestedStudentCount(Integer testedStudentCount) {
        this.testedStudentCount = testedStudentCount;
    }

    public Integer getOntestStudentCount() {
        return ontestStudentCount;
    }

    public void setOntestStudentCount(Integer ontestStudentCount) {
        this.ontestStudentCount = ontestStudentCount;
    }

    public Integer getMissExamStudentCount() {
        return missExamStudentCount;
    }

    public void setMissExamStudentCount(Integer missExamStudentCount) {
        this.missExamStudentCount = missExamStudentCount;
    }

    public Integer getBrokenRuleStudentCount() {
        return brokenRuleStudentCount;
    }

    public void setBrokenRuleStudentCount(Integer brokenRuleStudentCount) {
        this.brokenRuleStudentCount = brokenRuleStudentCount;
    }
}
