package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.QbExamQuestions;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.UpLoadInfo;
import com.hxminco.mrms.ioc.d.*;
import com.hxminco.mrms.ioc.s.QbExamQuestionsService;
import com.hxminco.mrms.ioc.s.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/9/20.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    private RsStudentInfoMapper rsStudentInfoMapper;
    @Autowired
    private QbExamInfoMapper qbExamInfoMapper;
    @Autowired
    private QbExamQuestionsMapper qbExamQuestionsMapper;
    @Autowired
    private QbPaperMapper paperMapper;
    @Autowired
    private QbQuestionMapper questionMapper;
    @Autowired
    private QbAnswerMapper answerMapper;
    @Autowired
    private RsTrainPlanMapper trainPlanMapper;
    @Override
    public void updateExamInfoAndStudentInfo(QbExamInfo qbExamInfo, RsStudentInfo student) {
        if(qbExamInfo != null) {
            qbExamInfoMapper.updateByPrimaryKeySelective(qbExamInfo);
        }
        if(student != null) {
            rsStudentInfoMapper.updateByPrimaryKeySelective(student);
        }
    }

    public void deleteUpLoadedTrainPlanDataAndUpdateStatus(String trainPlanUid){
        int qbExamInfoCount = qbExamInfoMapper.deleteExamInfoListByTrainPlanUid(trainPlanUid);
        logger.info("qb_exam_info表删除了[{}]条数据！",qbExamInfoCount);

        int qbExamQuestionsCount = qbExamQuestionsMapper.deleteQbExamQuestionsListByTrainPlanUid(trainPlanUid);
        logger.info("qb_exam_questions表删除了[{}]条数据！",qbExamQuestionsCount);

        int rsStudentInfoCount = rsStudentInfoMapper.deleteRsStudentInfoListByTrainPlanUid(trainPlanUid);
        logger.info("rs_student_info表删除了[{}]条数据！",rsStudentInfoCount);

        int answerRow = answerMapper.deleteAnswersByTrainPlanUid(trainPlanUid);
        logger.info("qb_answer表删除了[{}]条数据！",answerRow);

        int questionRow = questionMapper.deleteQuestionsByTrainPlanUid(trainPlanUid);
        logger.info("qb_question表删除了[{}]条数据！",questionRow);

        int paperRow = paperMapper.deletePaperByTrainPlanUid(trainPlanUid);
        logger.info("qb_paper表删除了[{}]条数据！",paperRow);
        Map<String, Object> map = new HashMap<>();
        map.put("trainPlanUid",trainPlanUid);
        map.put("status","3");
        Long updateTime = System.currentTimeMillis();
        map.put("updateTime",updateTime);
        trainPlanMapper.updateTrainPlanMap(map);
    }

    @Override
    public UpLoadInfo queryUpLoadData(String trainPlanUid) {
        List<QbExamInfo> lstQbExamInfos =  qbExamInfoMapper.selectExamInfoListByTrainPlanUid(trainPlanUid);
        if(lstQbExamInfos == null || lstQbExamInfos.size()==0){
            logger.info("qb_exam_info表需要上传记录为空！");
        }else {
            logger.info("qb_exam_info表需要上传[{}]条记录！", lstQbExamInfos.size());
        }
        List<QbExamQuestions> lstQbExamQuestions =  qbExamQuestionsMapper.selectQbExamQuestionsListByTrainPlanUid(trainPlanUid);
        if(lstQbExamQuestions == null || lstQbExamQuestions.size()==0){
            logger.info("qb_exam_questions表需要上传记录为空！");
        }else {
            logger.info("qb_exam_questions表需要上传[{}]条记录！", lstQbExamQuestions.size());
        }
        List<RsStudentInfo> lstRsStudentInfos =  rsStudentInfoMapper.selectStudentInfoListByTrainPlanUid(trainPlanUid);
        if(lstRsStudentInfos == null || lstRsStudentInfos.size()==0){
            logger.info("rs_student_info表需要上传记录为空！");
            return null;
        }else {
            logger.info("rs_student_info表需要上传[{}]条记录！", lstRsStudentInfos.size());
        }
        UpLoadInfo upLoadInfo = new UpLoadInfo();
        upLoadInfo.setLstQbExamInfos(lstQbExamInfos);
        upLoadInfo.setLstQbExamQuestions(lstQbExamQuestions);
        upLoadInfo.setLstRsStudentInfos(lstRsStudentInfos);
        return upLoadInfo;
    }
}
