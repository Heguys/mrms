package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.QbAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Employee on 2017/7/26.
 */
public class ExamQuestion {

    private String id;

    private String questionDescription;

    private String paperId;

    private Integer paperOrder;

    private Integer questionTypeCode;

    private Integer difficultyLevelCode;

    private Double score;

    private Integer sort;

    private Integer type;

    private String xx;

    private List<QbAnswer> answers = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public Integer getPaperOrder() {
        return paperOrder;
    }

    public void setPaperOrder(Integer paperOrder) {
        this.paperOrder = paperOrder;
    }

    public Integer getQuestionTypeCode() {
        return questionTypeCode;
    }

    public void setQuestionTypeCode(Integer questionTypeCode) {
        this.questionTypeCode = questionTypeCode;
    }

    public Integer getDifficultyLevelCode() {
        return difficultyLevelCode;
    }

    public void setDifficultyLevelCode(Integer difficultyLevelCode) {
        this.difficultyLevelCode = difficultyLevelCode;
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
        this.xx = xx;
    }

    public List<QbAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QbAnswer> answers) {
        this.answers = answers;
    }
}
