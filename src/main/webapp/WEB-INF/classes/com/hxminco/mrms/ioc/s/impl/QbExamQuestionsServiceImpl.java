package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.QbExamQuestions;
import com.hxminco.mrms.comm.model.ExamQuestion;
import com.hxminco.mrms.ioc.d.QbExamQuestionsMapper;
import com.hxminco.mrms.ioc.s.QbExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Employee on 2017/7/25.
 */
@Service
public class QbExamQuestionsServiceImpl implements QbExamQuestionsService {
    @Autowired
    private QbExamQuestionsMapper qbExamQuestionsMapper;

    @Override
    public List<QbExamQuestions> getQbExamQuestionsListByTrainPlanUid(String trainPlanUid) {
        List<QbExamQuestions> lstQbExamQuestions = qbExamQuestionsMapper.selectQbExamQuestionsListByTrainPlanUid(trainPlanUid);
        return lstQbExamQuestions;
    }

    @Override
    public int addQbExamQuestions(QbExamQuestions examQuestion) {
        int rows = qbExamQuestionsMapper.insert(examQuestion);
        return rows;
    }

    @Override
    public int updateQbExamQuestions(QbExamQuestions examQuestion) {
        int rows = qbExamQuestionsMapper.updateByPrimaryKeySelective(examQuestion);
        return rows;
    }

    @Override
    public QbExamQuestions getQbExamQuestions(QbExamQuestions examQuestion) {
        QbExamQuestions qbExamQuestion = qbExamQuestionsMapper.selectQbExamQuestionsByQbExamQuestion(examQuestion);
        return qbExamQuestion;
    }

    @Override
    public List<QbExamQuestions> getQbExamQuestionsByQbExamInfo(QbExamInfo qbExamInfo) {
        List<QbExamQuestions> lstQbExamQuestions = qbExamQuestionsMapper.selectQbExamQuestionsByQbExamInfo(qbExamInfo);
        return lstQbExamQuestions;
    }

    @Override
    public Double countExamScore(String examCode,int examtime) {
        Map<String ,Object> map = new HashMap<>();
        map.put("examCode",examCode);
        map.put("examtime",examtime);
        Double score = qbExamQuestionsMapper.countExamScore(map);
        return score;
    }

    @Override
    public int deleteQbExamQuestionsListByTrainPlanUid(String trainPlanUid) {
        int rows = qbExamQuestionsMapper.deleteQbExamQuestionsListByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public List<String> queryLstCorrectAnswerId(String questionid) {
        List<String> lstCorrectAnswerId = qbExamQuestionsMapper.selectLstCorrectAnswerId(questionid);
        return lstCorrectAnswerId;
    }
}
