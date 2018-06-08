package com.hxminco.mrms.comm.entry;

public class QbExamQuestions {
    private String id;

    private String examcode;

    private String questionid;

    private String answerid;

    private Double score;

    private Integer examtime;

    public QbExamQuestions(String id, String examcode, String questionid, String answerid, Double score, Integer examtime) {
        this.id = id;
        this.examcode = examcode;
        this.questionid = questionid;
        this.answerid = answerid;
        this.score = score;
        this.examtime = examtime;
    }

    public QbExamQuestions() {
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

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid == null ? null : questionid.trim();
    }

    public String getAnswerid() {
        return answerid;
    }

    public void setAnswerid(String answerid) {
        this.answerid = answerid == null ? null : answerid.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getExamtime() {
        return examtime;
    }

    public void setExamtime(Integer examtime) {
        this.examtime = examtime;
    }
}