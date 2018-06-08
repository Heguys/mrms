package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.*;
import com.hxminco.mrms.comm.model.ExamPlanData;
import com.hxminco.mrms.comm.model.FinishedExamPlanData;
import com.hxminco.mrms.comm.model.InvigilateStatistics;

import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/13.
 */
public interface ExamInfoSnycService {
    void updateExamInfo(ExamInfo examInfo);

    QbPaper getPaperByTrainPlanUid(String trainPlanUid);

    RsTrainPlan getTrainPlanByUid(String trainPlanUid);

    RsExamPlan getExamPlanByTrainPlanUid(String trainPlanUid);

    List<ExamPlanData> getExamPlanDataUnfinished(Map<String, Object> map);

    int getExamPlanCountUnfinished(Map<String, Object> map);

    List<QbAnswer> getAnswerByPaperId(String id);

    InvigilateStatistics getInvigilateStatistics(String trainPlanUid);

    int updateTrainPlanSelective(RsTrainPlan trainPlan);

    int deleteAnswersByTrainPlanUid(String trainPlanUid);

    int deleteQuestionsByTrainPlanUid(String trainPlanUid);

    int deletePaperByTrainPlanUid(String trainPlanUid);

    int deleteTrainPlanByTrainPlanUid(String trainPlanUid);

    int deleteExamPlanByTrainPlanUid(String trainPlanUid);

    ExamPlanData getExamPlanDataByTrainPlanUid(String trainPlanUid);

    void endExam(String trainPlanUid,String username);

    void updateTrainPlanByUid(RsTrainPlan trainPlan);

    Integer getBrokenRuleStudentCount(String trainPlanUid);

    Integer getMissExamStudentCount(String trainPlanUid);


    List<FinishedExamPlanData> getExamPlanDataFinished(Map<String, Object> map);

    int getExamPlanCountFinished(Map<String, Object> map);

    Integer getOnTestStudentCount(String uid);

    List<String> queryTrainPlanUidList();

    void updateTrainPlanMap(Map<String, Object> map);

    RsStudentInfo getStudentByUid(String uid);
}
