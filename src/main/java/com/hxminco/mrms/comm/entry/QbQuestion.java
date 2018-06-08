package com.hxminco.mrms.comm.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QbQuestion {
    private String id;

    private String tqid;

    private String description;

    private String paperid;

    private Integer paperorder;

    private Integer questiontypecode;

    private Integer difficultylevelcode;

    private Double score;

    private Integer sort;

    private Integer type;

    private String xx;

    public QbQuestion(String id, String tqid, String description, String paperid, Integer paperorder, Integer questiontypecode, Integer difficultylevelcode, Double score, Integer sort, Integer type, String xx) {
        this.id = id;
        this.tqid = tqid;
        this.description = description;
        this.paperid = paperid;
        this.paperorder = paperorder;
        this.questiontypecode = questiontypecode;
        this.difficultylevelcode = difficultylevelcode;
        this.score = score;
        this.sort = sort;
        this.type = type;
        this.xx = xx;
    }

    public QbQuestion() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTqid() {
        return tqid;
    }

    public void setTqid(String tqid) {
        this.tqid = tqid == null ? null : tqid.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid == null ? null : paperid.trim();
    }

    public Integer getPaperorder() {
        return paperorder;
    }

    public void setPaperorder(Integer paperorder) {
        this.paperorder = paperorder;
    }

    public Integer getQuestiontypecode() {
        return questiontypecode;
    }

    public void setQuestiontypecode(Integer questiontypecode) {
        this.questiontypecode = questiontypecode;
    }

    public Integer getDifficultylevelcode() {
        return difficultylevelcode;
    }

    public void setDifficultylevelcode(Integer difficultylevelcode) {
        this.difficultylevelcode = difficultylevelcode;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx == null ? null : xx.trim();
    }

    @Override
    public String toString() {
        return "QbQuestion{" +
                "id='" + id + '\'' +
                ", tqid='" + tqid + '\'' +
                ", description='" + description + '\'' +
                ", paperid='" + paperid + '\'' +
                ", paperorder=" + paperorder +
                ", questiontypecode=" + questiontypecode +
                ", difficultylevelcode=" + difficultylevelcode +
                ", score=" + score +
                ", sort=" + sort +
                ", type=" + type +
                ", xx='" + xx + '\'' +
                '}';
    }
}