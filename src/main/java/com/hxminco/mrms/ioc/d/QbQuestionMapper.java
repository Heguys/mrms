package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.QbQuestion;
import com.hxminco.mrms.comm.model.QuestionRecord;

import java.util.List;
import java.util.Map;

public interface QbQuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(QbQuestion record);

    int insertSelective(QbQuestion record);

    QbQuestion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QbQuestion record);

    int updateByPrimaryKey(QbQuestion record);

    int insertQuestionList(Map<String, List<QbQuestion>> questionMap);

    List<QuestionRecord> selectQuestionRecordList(String examPlanUid);

    int deleteQuestionsByTrainPlanUid(String trainPlanUid);

    int deleteTrainPlanByTrainPlanUid(String trainPlanUid);

    int deleteExamPlanByTrainPlanUid(String trainPlanUid);
}