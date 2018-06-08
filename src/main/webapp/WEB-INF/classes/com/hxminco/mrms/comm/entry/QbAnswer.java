package com.hxminco.mrms.comm.entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QbAnswer {
    private String id;

    private String description;

    private String questionid;

    private String isvalid;

    public QbAnswer(String id, String description, String questionid, String isvalid) {
        this.id = id;
        this.description = description;
        this.questionid = questionid;
        this.isvalid = isvalid;
    }

    public QbAnswer() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid == null ? null : questionid.trim();
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid == null ? null : isvalid.trim();
    }

    @Override
    public String toString() {
        return "QbAnswer{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", questionid='" + questionid + '\'' +
                ", isvalid='" + isvalid + '\'' +
                '}';
    }
}