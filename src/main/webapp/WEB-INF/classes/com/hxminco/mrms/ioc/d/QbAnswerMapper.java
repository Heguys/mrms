package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.QbAnswer;

import java.util.List;
import java.util.Map;

public interface QbAnswerMapper {
    int deleteByPrimaryKey(String id);

    int insert(QbAnswer record);

    int insertSelective(QbAnswer record);

    QbAnswer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QbAnswer record);

    int updateByPrimaryKey(QbAnswer record);

    int insertAnswerList(Map<String, List<QbAnswer>> answerMap);

    List<QbAnswer> selectAnswersByPaperId(String paperId);

    int deleteAnswersByTrainPlanUid(String trainPlanUid);
}