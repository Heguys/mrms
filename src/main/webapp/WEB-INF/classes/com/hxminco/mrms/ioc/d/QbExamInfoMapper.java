package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.QbExamInfo;

import java.util.List;
import java.util.Map;

public interface QbExamInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(QbExamInfo record);

    int insertSelective(QbExamInfo record);

    QbExamInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QbExamInfo record);

    int updateByPrimaryKey(QbExamInfo record);

    List<QbExamInfo> selectExamInfoListByTrainPlanUid(String trainPlanUid);

    List<QbExamInfo> selectExamInfoListByExamCode(String examCode);

    QbExamInfo selectExamInfoListByExamCodeAndStatus(Map<String, Object> map);

    QbExamInfo selectExamInfoListByExamCodeOrderDesc(String examCode);

    int deleteExamInfoListByTrainPlanUid(String trainPlanUid);

    List<Integer> selectPaperOrderListByExamCode(String examCode);

    List<QbExamInfo> selectOnTestingExamInfoMap(String uid);

    QbExamInfo selectMaxScoreQbExamInfoRecord(String examCode);

    void updateExamInfoPassTime(QbExamInfo one);
}