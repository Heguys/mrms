package com.hxminco.mrms.comm.entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Employee on 2017/7/10.
 */
@XmlRootElement
public class ExamInfo {

    private List<QbPaper> papers ;
    private List<QbQuestion> questions ;
    private List<QbAnswer> answers ;
    private List<RsTrainPlan> trainPlans ;
    private List<RsExamPlan> examPlans ;
    private List<RsStudentInfo> studentInfos ;

    public ExamInfo() {
    }

    public List<QbPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<QbPaper> papers) {
        this.papers = papers;
    }

    public List<QbQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QbQuestion> questions) {
        this.questions = questions;
    }

    public List<QbAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QbAnswer> answers) {
        this.answers = answers;
    }

    public List<RsTrainPlan> getTrainPlans() {
        return trainPlans;
    }

    public void setTrainPlans(List<RsTrainPlan> trainPlans) {
        this.trainPlans = trainPlans;
    }

    public List<RsExamPlan> getExamPlans() {
        return examPlans;
    }

    public void setExamPlans(List<RsExamPlan> examPlans) {
        this.examPlans = examPlans;
    }

    public List<RsStudentInfo> getStudentInfos() {
        return studentInfos;
    }

    public void setStudentInfos(List<RsStudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
    }

    @Override
    public String toString() {
        return "ExamInfo{" +
                "papers=" + papers +
                ", questions=" + questions +
                ", answers=" + answers +
                ", trainPlans=" + trainPlans +
                ", examPlans=" + examPlans +
                ", studentInfos=" + studentInfos +
                '}';
    }
}
