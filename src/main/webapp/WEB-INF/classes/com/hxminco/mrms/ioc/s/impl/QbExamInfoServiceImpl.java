package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.QbPaper;
import com.hxminco.mrms.comm.entry.RsExamPlan;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.ioc.d.QbExamInfoMapper;
import com.hxminco.mrms.ioc.d.QbExamQuestionsMapper;
import com.hxminco.mrms.ioc.d.RsStudentInfoMapper;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import com.hxminco.mrms.ioc.s.QbExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/25.
 */
@Service("qbExamInfoServiceImpl")
public class QbExamInfoServiceImpl implements QbExamInfoService {
    @Autowired
    private QbExamInfoMapper qbExamInfoMapper;
    @Autowired
    private RsStudentInfoMapper studentInfoMapper;
    @Autowired
    private QbExamQuestionsMapper qbExamQuestionsMapper;
    @Override
    public List<QbExamInfo> getExamInfoListByTrainPlanUid(String trainPlanUid) {
        List<QbExamInfo> lstQbExamInfos = qbExamInfoMapper.selectExamInfoListByTrainPlanUid(trainPlanUid);
        return lstQbExamInfos;
    }

    @Override
    public List<QbExamInfo> getQbExamInfosByExamCode(String examCode) {
        List<QbExamInfo> qbExamInfos =  qbExamInfoMapper.selectExamInfoListByExamCode(examCode);
        return qbExamInfos;
    }

    @Override
    public int insertExamInfo(QbExamInfo qbExamInfo) {
        int rows = qbExamInfoMapper.insertSelective(qbExamInfo);
        return rows;
    }

    @Override
    public int updateExamInfo(QbExamInfo qbExamInfo) {
        int rows = qbExamInfoMapper.updateByPrimaryKeySelective(qbExamInfo);
        return rows;
    }

    @Override
    public QbExamInfo getQbExamInfosByExamCodeAndStatus(String examCode, String status) {
        Map<String,Object> map =  new HashMap<>();
        map.put("examCode",examCode);
        map.put("status",status);
        QbExamInfo qbExamInfo = qbExamInfoMapper.selectExamInfoListByExamCodeAndStatus(map);
        return qbExamInfo;
    }

    @Override
    public QbExamInfo getQbExamInfosByExamCodeOrderDesc(String examCode) {
        QbExamInfo qbExamInfo =  qbExamInfoMapper.selectExamInfoListByExamCodeOrderDesc(examCode);
        return qbExamInfo;
    }

    @Override
    public int deleteExamInfoListByTrainPlanUid(String trainPlanUid) {
        int rows = qbExamInfoMapper.deleteExamInfoListByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public void insertExamInfoAndStudentExamStatus(QbExamInfo qbExamInfo, RsStudentInfo student) {
        qbExamInfoMapper.insertSelective(qbExamInfo);
        studentInfoMapper.updateByPrimaryKeySelective(student);
    }

    @Override
    public List<Integer> getPaperOrderListByExamCode(String examCode) {
        List<Integer> lstPaperOrder = qbExamInfoMapper.selectPaperOrderListByExamCode(examCode);
        return lstPaperOrder;
    }

    @Override
    public void updateExamInfoPassTime(QbExamInfo one) {
        qbExamInfoMapper.updateExamInfoPassTime(one);
    }

    @Override
    public QbExamInfo getMaxScoreQbExamInfoRecord(String examCode) {
        QbExamInfo qbExamInfo = qbExamInfoMapper.selectMaxScoreQbExamInfoRecord(examCode);
        return qbExamInfo;
    }

    @Override
    public Map<String, QbExamInfo> queryOnTestingExamInfoMap(String uid) {
        List<QbExamInfo> lstQbexamInfo = qbExamInfoMapper.selectOnTestingExamInfoMap(uid);
        Map<String,QbExamInfo> qbExamInfoMap= new HashMap<>();
        if(lstQbexamInfo != null && lstQbexamInfo.size()>0) {
            for (QbExamInfo one : lstQbexamInfo) {
                qbExamInfoMap.put(one.getExamcode(),one);
            }
            return qbExamInfoMap;
        }else {
            return null;
        }
    }

    public void commitMockStudentExam(QbPaper paper,RsExamPlan examPlan,RsStudentInfo student,QbExamInfo qbExamInfo){
        int examFinishTimes = 0;
        if(student.getExamFinishTimes() != null) {
            examFinishTimes = student.getExamFinishTimes().intValue();
        }
        int examTime = examFinishTimes +1;
        Map<String ,Object> map = new HashMap<>();
        map.put("examCode",student.getExamCode());
        map.put("examtime",examTime);
        Double dbScore = qbExamQuestionsMapper.countExamScore(map);
        double score = 0.0;
        if (dbScore != null){
            score = dbScore.doubleValue();
        }
        qbExamInfo.setEndtime(new Date());
        if ("0".equals(student.getMakeUp()) && examFinishTimes == 0) {
            qbExamInfo.setMakeup(0);
        } else {
            qbExamInfo.setMakeup(1);
        }
        qbExamInfo.setScore(score);
        qbExamInfo.setStatus("3");
        examFinishTimes = examFinishTimes+1;
        student.setExamFinishTimes(examFinishTimes);
        if (qbExamInfo.getExamtime() == 1) {
            student.setTheoryExamScore1(score);
        } else {
            student.setTheoryExamScore2(score);
        }
        if (score >= paper.getPassmark()) {
            qbExamInfo.setPassed("1");
            student.setIsExamFinished("3");
            student.setTheoryExamScore(score);
            student.setTheoryExamPassed("1");
        } else {
            qbExamInfo.setPassed("0");
            double theoryExamScore = 0.0;
            if(student.getTheoryExamScore() != null){
                theoryExamScore = student.getTheoryExamScore().intValue();
            }
            double maxScore = theoryExamScore >= score ? theoryExamScore : score;
            student.setTheoryExamScore(maxScore);
            student.setTheoryExamPassed("0");
            if (("1".equals(student.getMakeUp()) && examFinishTimes >= 1) ||
                    ("0".equals(student.getMakeUp()) && examFinishTimes >= examPlan.getAllowExamTimes())) {
                student.setIsExamFinished("3");
            }
        }
    }
}
