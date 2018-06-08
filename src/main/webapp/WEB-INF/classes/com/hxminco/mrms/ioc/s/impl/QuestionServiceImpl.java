package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.QbAnswer;
import com.hxminco.mrms.comm.model.ExamQuestion;
import com.hxminco.mrms.comm.model.QuestionRecord;
import com.hxminco.mrms.ioc.d.QbQuestionMapper;
import com.hxminco.mrms.ioc.s.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/26.
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QbQuestionMapper qbQuestionMapper;
    @Override
    public Map<Integer, List<ExamQuestion>> getQuestionMap(String trainPlanUid) {
        List<QuestionRecord> lstQuestionRecords = qbQuestionMapper.selectQuestionRecordList(trainPlanUid);
        String qbid=null;
        int paperOrder = 0;
        ExamQuestion tmpExamQuestion = new ExamQuestion();
        Map<Integer, List<ExamQuestion>> questionMap = new HashMap<>();
        List<ExamQuestion> lstExamQuestion = null;
        if(lstQuestionRecords != null && lstQuestionRecords.size()> 0){
            QuestionRecord firstQuestionRecord =  lstQuestionRecords.get(0);
            qbid = firstQuestionRecord.getId();
            paperOrder = firstQuestionRecord.getPaperOrder();
            tmpExamQuestion.setId(firstQuestionRecord.getId());
            tmpExamQuestion.setQuestionDescription(firstQuestionRecord.getQuestionDescription());
            tmpExamQuestion.setPaperId(firstQuestionRecord.getPaperId());
            tmpExamQuestion.setPaperOrder(firstQuestionRecord.getPaperOrder());
            tmpExamQuestion.setQuestionTypeCode(firstQuestionRecord.getQuestionTypeCode());
            tmpExamQuestion.setDifficultyLevelCode(firstQuestionRecord.getDifficultyLevelCode());
            tmpExamQuestion.setScore(firstQuestionRecord.getScore());
            tmpExamQuestion.setSort(firstQuestionRecord.getSort());
            tmpExamQuestion.setType(firstQuestionRecord.getType());
            tmpExamQuestion.setXx(firstQuestionRecord.getXx());
            for(QuestionRecord one:lstQuestionRecords) {
                if (qbid != null && qbid.equals(one.getId())) {
                    QbAnswer qbAnswer = new QbAnswer();
                    qbAnswer.setDescription(one.getAnswerDescription());
//                    qbAnswer.setIsvalid(one.getIsValid());
                    qbAnswer.setId(one.getAnswerId());
                    qbAnswer.setQuestionid(one.getQuestionId());
                    tmpExamQuestion.getAnswers().add(qbAnswer);
                } else {
                    if(paperOrder != tmpExamQuestion.getPaperOrder()) {
                        paperOrder = tmpExamQuestion.getPaperOrder();
                    }
                        lstExamQuestion = questionMap.get(paperOrder);
                        if(lstExamQuestion == null){
                            lstExamQuestion = new ArrayList<>();
                            questionMap.put(paperOrder,lstExamQuestion);
                            lstExamQuestion.add(tmpExamQuestion);
                        }else{
                            lstExamQuestion.add(tmpExamQuestion);
                        }
                    tmpExamQuestion = new ExamQuestion();
                    qbid = one.getId();
                    tmpExamQuestion.setId(one.getId());
                    tmpExamQuestion.setQuestionDescription(one.getQuestionDescription());
                    tmpExamQuestion.setPaperId(one.getPaperId());
                    tmpExamQuestion.setPaperOrder(one.getPaperOrder());
                    tmpExamQuestion.setQuestionTypeCode(one.getQuestionTypeCode());
                    tmpExamQuestion.setDifficultyLevelCode(one.getDifficultyLevelCode());
                    tmpExamQuestion.setScore(one.getScore());
                    tmpExamQuestion.setSort(one.getSort());
                    tmpExamQuestion.setType(one.getType());
                    tmpExamQuestion.setXx(one.getXx());
                    QbAnswer qbAnswer = new QbAnswer();
                    qbAnswer.setDescription(one.getAnswerDescription());
                    qbAnswer.setIsvalid(one.getIsValid());
                    qbAnswer.setId(one.getAnswerId());
                    qbAnswer.setQuestionid(one.getQuestionId());
                    tmpExamQuestion.getAnswers().add(qbAnswer);
                }
            }
        }

        if(paperOrder != tmpExamQuestion.getPaperOrder()) {
            paperOrder = tmpExamQuestion.getPaperOrder();
        }
        lstExamQuestion = questionMap.get(paperOrder);
        if(lstExamQuestion == null){
            lstExamQuestion = new ArrayList<>();
            questionMap.put(paperOrder,lstExamQuestion);
            lstExamQuestion.add(tmpExamQuestion);
        }else{
            lstExamQuestion.add(tmpExamQuestion);
        }
        return questionMap;
    }

}
