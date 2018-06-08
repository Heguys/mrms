package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.RsExamPlan;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.ExamPlanData;
import com.hxminco.mrms.comm.model.FinishedExamPlanData;

import java.util.List;
import java.util.Map;

public interface RsExamPlanMapper {
    int deleteByPrimaryKey(String uid);

    int insert(RsExamPlan record);

    int insertSelective(RsExamPlan record);

    RsExamPlan selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(RsExamPlan record);

    int updateByPrimaryKey(RsExamPlan record);

    int insertExamPlanList(Map<String, List<RsExamPlan>> examPlanMap);

    RsExamPlan selectExamPlanByTrainPlanUid(String trainPlanUid);

    int selectUntestedStudentCount(String trainPlanUid);

    Integer selectOnTestStudentCount(String trainPlanUid);

    ExamPlanData selectExamPlanDataByTrainPlanUid(String trainPlanUid);

    Integer selectTotalStudentCount(String trainPlanUid);

    Integer selectTestedStudentCount(String trainPlanUid);

    List<ExamPlanData> selectExamPlanDataUnfinished(Map<String, Object> map);

    int selectCountUnfinished(Map<String, Object> map);

    List<FinishedExamPlanData> selectExamPlanDataFinished(Map<String, Object> map);

    int selectCountFinished(Map<String, Object> map);
}