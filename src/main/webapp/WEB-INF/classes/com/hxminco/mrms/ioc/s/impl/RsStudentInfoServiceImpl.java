package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.QbPaper;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.InvigilatePageModel;
import com.hxminco.mrms.ioc.d.QbPaperMapper;
import com.hxminco.mrms.ioc.d.RsStudentInfoMapper;
import com.hxminco.mrms.ioc.s.RsStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/25.
 */
@Service
public class RsStudentInfoServiceImpl implements RsStudentInfoService {

    @Autowired
    private RsStudentInfoMapper rsStudentInfoMapper;
    @Autowired
    private QbPaperMapper qbPaperMapper;

    @Override
    public List<RsStudentInfo> getStudentInfoListByTrainPlanUid(String trainPlanUid) {
        List<RsStudentInfo> lstRsStudentInfos = rsStudentInfoMapper.selectStudentInfoListByTrainPlanUid(trainPlanUid);
        return lstRsStudentInfos;
    }

    @Override
    public RsStudentInfo getRsStudentInfo4Login(RsStudentInfo studentInfo) {
        RsStudentInfo dbStudentInfo = rsStudentInfoMapper.selectRsStudentInfo4Login(studentInfo);
        return dbStudentInfo;
    }

    @Override
    public int updateStudentInfo(RsStudentInfo student) {
        int rows = rsStudentInfoMapper.updateByPrimaryKeySelective(student);
        return rows;
    }

    @Override
    public int deleteRsStudentInfoListByTrainPlanUid(String trainPlanUid) {
        int rows = rsStudentInfoMapper.deleteRsStudentInfoListByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public int selectStudentExamRecord(String uid) {
        int count = rsStudentInfoMapper.selectStudentExamRecord(uid);
        return count;
    }

    @Override
    public RsStudentInfo selectByExamCode(String examcode) {
        return null;
    }

    @Override
    public List<InvigilatePageModel> getRsStudentInfoListByTrainPlanUidAndKeyWord(Map<String,String> map) {
        List<InvigilatePageModel> lstDbInvigilatePageModel = rsStudentInfoMapper.selectLstInvigilatePageModelByMap(map);
        List<InvigilatePageModel> lstInvigilatePageModel = new ArrayList<>();
        String examCode = "";
        for (InvigilatePageModel one :lstDbInvigilatePageModel){
            if (!examCode.equals(one.getExamCode())){
                lstInvigilatePageModel.add(one);
                examCode = one.getExamCode();
            }
        }
        return lstInvigilatePageModel;
    }

    @Override
    public RsStudentInfo getRsStudentByUid(String uid) {
        RsStudentInfo student = rsStudentInfoMapper.selectByPrimaryKey(uid);
        return student;
    }

    @Override
    public InvigilatePageModel getRsStudentInfoByStudentUid(String uid) {
        InvigilatePageModel invigilatePageModel = rsStudentInfoMapper.selectInvigilatePageModelByStudentUid(uid);
        return invigilatePageModel;
    }

    @Override
    public List<RsStudentInfo> queryUnFinishedStudentList(String uid) {
        List<RsStudentInfo> lstStudent = rsStudentInfoMapper.selectUnFinishedStudentList(uid);
        return lstStudent;
    }


    public RsStudentInfo getStudentByLoginExamCode(String examCode) {
        List<RsStudentInfo> lstStudent = rsStudentInfoMapper.selectStudentByLoginExamCode(examCode);
        if(lstStudent == null || lstStudent.size() != 1){
            return null;
        }
        return lstStudent.get(0);
    }

    @Override
    public List<InvigilatePageModel> getRsStudentInfoListByTrainPlanUid(String trainPlanUid) {
        List<InvigilatePageModel> lstDbInvigilatePageModel = rsStudentInfoMapper.selectLstInvigilatePageModelByTrainPlanUid(trainPlanUid);
        List<InvigilatePageModel> lstInvigilatePageModel = new ArrayList<>();
        String examCode = "";
        for (InvigilatePageModel one :lstDbInvigilatePageModel){
            if (!examCode.equals(one.getExamCode())){
                lstInvigilatePageModel.add(one);
                examCode = one.getExamCode();
            }
        }
        return lstInvigilatePageModel;
    }

    @Override
    public void updateStudentInfoMaxScore(String trainPlanUid) {
        List<RsStudentInfo> lstStudent = rsStudentInfoMapper.selectRsStudentInfoListByTrainPlanUid(trainPlanUid);
        QbPaper qbPaper = qbPaperMapper.selectPaperByTrainPlanUid(trainPlanUid);
        double maxScore = 0;
        for (RsStudentInfo one:lstStudent){
            maxScore = one.getTheoryExamScore1()>one.getTheoryExamScore2()?one.getTheoryExamScore1():one.getTheoryExamScore2();
            one.setTheoryExamScore(maxScore);
            if(maxScore >= qbPaper.getPassmark()){
            one.setTheoryExamPassed("1");
            }
        }
        Map<String, List<RsStudentInfo>> map = new HashMap<>();
        map.put("lstStudent",lstStudent);
        rsStudentInfoMapper.updateListStudentInfo(map);
    }
}
