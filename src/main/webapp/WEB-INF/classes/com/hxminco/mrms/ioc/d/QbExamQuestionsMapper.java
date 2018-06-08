package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.QbExamQuestions;
import com.hxminco.mrms.comm.model.ExamQuestion;

import java.util.List;
import java.util.Map;

public interface QbExamQuestionsMapper {
    int deleteByPrimaryKey(String id);

    int insert(QbExamQuestions record);

    int insertSelective(QbExamQuestions record);

    QbExamQuestions selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QbExamQuestions record);

    int updateByPrimaryKey(QbExamQuestions record);

    List<QbExamQuestions> selectQbExamQuestionsListByTrainPlanUid(String trainPlanUid);

    Double countExamScore(Map<String ,Object> map);

    QbExamQuestions selectQbExamQuestionsByQbExamQuestion(QbExamQuestions examQuestion);

    List<QbExamQuestions> selectQbExamQuestionsByQbExamInfo(QbExamInfo qbExamInfo);

    int deleteQbExamQuestionsListByTrainPlanUid(String trainPlanUid);

    List<String> selectLstCorrectAnswerId(String questionid);
}