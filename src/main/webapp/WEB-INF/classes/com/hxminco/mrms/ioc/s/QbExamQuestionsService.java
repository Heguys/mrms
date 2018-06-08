package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.QbExamQuestions;
import com.hxminco.mrms.comm.model.ExamQuestion;

import java.util.List;

/**
 * Created by Employee on 2017/7/25.
 */
public interface QbExamQuestionsService {
    List<QbExamQuestions> getQbExamQuestionsListByTrainPlanUid(String trainPlanUid);

    int addQbExamQuestions(QbExamQuestions examQuestion);

    int updateQbExamQuestions(QbExamQuestions examQuestion);

    QbExamQuestions getQbExamQuestions(QbExamQuestions qbExamQuestion);

    List<QbExamQuestions> getQbExamQuestionsByQbExamInfo(QbExamInfo qbExamInfo);

    Double countExamScore(String examCode,int examtime);

    int deleteQbExamQuestionsListByTrainPlanUid(String trainPlanUid);

    List<String> queryLstCorrectAnswerId(String questionid);
}
