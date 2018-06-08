package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.InvigilatePageModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RsStudentInfoMapper {
    int deleteByPrimaryKey(String uid);

    int insert(RsStudentInfo record);

    int insertSelective(RsStudentInfo record);

    RsStudentInfo selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(RsStudentInfo record);

    int updateByPrimaryKey(RsStudentInfo record);

    int insertStudentInfoList(Map<String, List<RsStudentInfo>> studentInfoMap);

    List<RsStudentInfo> selectStudentInfoListByTrainPlanUid(String trainPlanUid);

    RsStudentInfo selectRsStudentInfo4Login(RsStudentInfo studentInfo);

    List<RsStudentInfo> selectStudentByLoginExamCode(String examCode);

    List<RsStudentInfo> selectRsStudentInfoListByTrainPlanUid(String trainPlanUid);

    int selectMissExamStudentCount(String trainPlanUid);

    int selectBrokenRuleStudentCount(String trainPlanUid);

    int updateListStudentInfo(Map<String, List<RsStudentInfo>> map);

    List<InvigilatePageModel> selectLstInvigilatePageModelByTrainPlanUid(String trainPlanUid);

    int deleteRsStudentInfoListByTrainPlanUid(String trainPlanUid);

    List<RsStudentInfo> selectUnFinishedStudentList(String uid);

    InvigilatePageModel selectInvigilatePageModelByStudentUid(String uid);

    List<InvigilatePageModel> selectLstInvigilatePageModelByMap(Map<String, String> map);

    int selectStudentExamRecord(String uid);
}