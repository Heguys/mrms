package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.RsStudentInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/25.
 */
public interface QbExamInfoService {

    List<QbExamInfo> getExamInfoListByTrainPlanUid(String trainPlanUid);

    List<QbExamInfo> getQbExamInfosByExamCode(String examCode);

    int insertExamInfo(QbExamInfo qbExamInfo);

    int updateExamInfo(QbExamInfo qbExamInfo);

    QbExamInfo getQbExamInfosByExamCodeAndStatus(String examCode, String status);

    QbExamInfo getQbExamInfosByExamCodeOrderDesc(String examCode);

    int deleteExamInfoListByTrainPlanUid(String trainPlanUid);

    void insertExamInfoAndStudentExamStatus(QbExamInfo qbExamInfo, RsStudentInfo student);

    List<Integer> getPaperOrderListByExamCode(String examCode);

    Map<String,QbExamInfo> queryOnTestingExamInfoMap(String uid);

    QbExamInfo getMaxScoreQbExamInfoRecord(String examCode);

    void updateExamInfoPassTime(QbExamInfo one);
}
