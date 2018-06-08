package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.InvigilatePageModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/25.
 */
public interface RsStudentInfoService {

    RsStudentInfo getRsStudentInfo4Login(RsStudentInfo studentInfo);

    int updateStudentInfo(RsStudentInfo student);

    RsStudentInfo getStudentByLoginExamCode(String examCode);


    List<InvigilatePageModel> getRsStudentInfoListByTrainPlanUid(String trainPlanUid);

    void updateStudentInfoMaxScore(String trainPlanUid);

    List<RsStudentInfo> getStudentInfoListByTrainPlanUid(String trainPlanUid);

    int deleteRsStudentInfoListByTrainPlanUid(String trainPlanUid);

    List<RsStudentInfo> queryUnFinishedStudentList(String uid);

    InvigilatePageModel getRsStudentInfoByStudentUid(String uid);

    RsStudentInfo getRsStudentByUid(String uid);

    List<InvigilatePageModel> getRsStudentInfoListByTrainPlanUidAndKeyWord(Map<String,String> map);

    int selectStudentExamRecord(String uid);

    RsStudentInfo selectByExamCode(String examcode);
}
