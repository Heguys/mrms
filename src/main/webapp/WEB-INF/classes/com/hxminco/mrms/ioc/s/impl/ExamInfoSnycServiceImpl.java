package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.*;
import com.hxminco.mrms.comm.model.ExamPlanData;
import com.hxminco.mrms.comm.model.FinishedExamPlanData;
import com.hxminco.mrms.comm.model.InvigilateStatistics;
import com.hxminco.mrms.comm.utils.StringUtil;
import com.hxminco.mrms.ioc.d.*;
import com.hxminco.mrms.ioc.s.ExamInfoSnycService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/13.
 */
@Service
public class ExamInfoSnycServiceImpl implements ExamInfoSnycService {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ExamInfoSnycService.class);
    @Autowired
    private QbPaperMapper paperMapper;
    @Autowired
    private QbQuestionMapper questionMapper;
    @Autowired
    private QbAnswerMapper answerMapper;
    @Autowired
    private RsStudentInfoMapper studentInfoMapper;
    @Autowired
    private RsTrainPlanMapper trainPlanMapper;
    @Autowired
    private RsExamPlanMapper examPlanMapper;
    @Autowired
    private ExamRecordMapper examRecordMapper;

    @Override
    public void updateExamInfo(ExamInfo examInfo) {

        List<QbPaper> papers = examInfo.getPapers();
        Map<String,List<QbPaper>> paperMap = new HashMap<>();
        paperMap.put("papers",papers);
        int paperrows = paperMapper.insertPaperList(paperMap);
        logger.info("插入paper记录[{}]条",paperrows);

        List<QbQuestion> questions = examInfo.getQuestions();
        List<List<QbQuestion>> destList = new ArrayList<>();
        StringUtil.splitListTo(questions,destList,100);
        Map<String,List<QbQuestion>> questionMap = new HashMap<>();
        int questionrows = 0;
        for (List<QbQuestion> one : destList){
            questionMap.put("questions",one);
            questionrows += questionMapper.insertQuestionList(questionMap);
        }
        logger.info("插入问题[{}]条",questionrows);
        List<QbAnswer> answers = examInfo.getAnswers();
        List<List<QbAnswer>> alstList = new ArrayList<>();
        StringUtil.splitListTo(answers,alstList,300);
        Map<String,List<QbAnswer>> answerMap = new HashMap<>();
        int answerrows = 0;
        for (List<QbAnswer> one :alstList) {
            answerMap.put("answers", one);
            answerrows  += answerMapper.insertAnswerList(answerMap);
        }
        logger.info("插入答案[{}]条",answerrows);
        List<RsStudentInfo> studentInfos = examInfo.getStudentInfos();
        Map<String,List<RsStudentInfo>> studentInfoMap = new HashMap<>();
        studentInfoMap.put("studentInfos",studentInfos);
        int studentInforows = studentInfoMapper.insertStudentInfoList(studentInfoMap);
        logger.info("插入学生记录[{}]条",studentInforows);

        List<RsTrainPlan> tainPlans = examInfo.getTrainPlans();
        for (RsTrainPlan one:tainPlans){
            one.setUpdateTime(System.currentTimeMillis());
        }
        Map<String,List<RsTrainPlan>> tainPlanMap = new HashMap<>();
        tainPlanMap.put("tainPlans",tainPlans);
        int tainPlanrows = trainPlanMapper.insertTainPlanList(tainPlanMap);
        logger.info("插入培训计划[{}]条",tainPlanrows);

        List<RsExamPlan> examPlans = examInfo.getExamPlans();
        Map<String,List<RsExamPlan>> examPlanMap = new HashMap<>();
        examPlanMap.put("examPlans",examPlans);
        int examPlanrows = examPlanMapper.insertExamPlanList(examPlanMap);
        logger.info("插入考试计划[{}]条",examPlanrows);
    }

    @Override
    public QbPaper getPaperByTrainPlanUid(String trainPlanUid) {
        QbPaper paper = paperMapper.selectPaperByTrainPlanUid(trainPlanUid);
        return paper;
    }

    @Override
    public RsTrainPlan getTrainPlanByUid(String trainPlanUid) {
        RsTrainPlan trainPlan = trainPlanMapper.selectByPrimaryKey(trainPlanUid);
        return trainPlan;
    }

    @Override
    public List<ExamPlanData> getExamPlanDataUnfinished(Map<String, Object> map) {
        List<ExamPlanData> examPlanDatas = examPlanMapper.selectExamPlanDataUnfinished(map);
        return examPlanDatas;
    }

    @Override
    public int getExamPlanCountUnfinished(Map<String, Object> map) {
        int count = examPlanMapper.selectCountUnfinished(map);
        return count;
    }

    @Override
    public List<FinishedExamPlanData> getExamPlanDataFinished(Map<String, Object> map) {
        List<FinishedExamPlanData> examPlanDatas = examPlanMapper.selectExamPlanDataFinished(map);
        return examPlanDatas;
    }

    @Override
    public RsStudentInfo getStudentByUid(String uid) {
        RsStudentInfo studentInfo = studentInfoMapper.selectByPrimaryKey(uid);
        return studentInfo;
    }

    @Override
    public void updateTrainPlanMap(Map<String, Object> map) {
        trainPlanMapper.updateTrainPlanMap(map);
    }

    @Override
    public List<String> queryTrainPlanUidList() {
        List<String> lstTrainPlanUid = trainPlanMapper.selectAllTrainPlanUid();
        return lstTrainPlanUid;
    }

    @Override
    public Integer getOnTestStudentCount(String trainPlanUid) {
        Integer onTestStudentCount = examPlanMapper.selectOnTestStudentCount(trainPlanUid);
        return onTestStudentCount;
    }

    @Override
    public int getExamPlanCountFinished(Map<String, Object> map) {
        int count = examPlanMapper.selectCountFinished(map);
        return count;
    }

    @Override
    public RsExamPlan getExamPlanByTrainPlanUid(String trainPlanUid) {
        RsExamPlan examPlan = examPlanMapper.selectExamPlanByTrainPlanUid(trainPlanUid);
        return examPlan;
    }

    @Override
    public int deleteAnswersByTrainPlanUid(String trainPlanUid) {
        int rows = answerMapper.deleteAnswersByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public int deleteQuestionsByTrainPlanUid(String trainPlanUid) {
        int rows = questionMapper.deleteQuestionsByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public int deletePaperByTrainPlanUid(String trainPlanUid) {
        int rows = paperMapper.deletePaperByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public int deleteTrainPlanByTrainPlanUid(String trainPlanUid) {
        int rows = questionMapper.deleteTrainPlanByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public int deleteExamPlanByTrainPlanUid(String trainPlanUid) {
        int rows = questionMapper.deleteExamPlanByTrainPlanUid(trainPlanUid);
        return rows;
    }

    @Override
    public ExamPlanData getExamPlanDataByTrainPlanUid(String trainPlanUid) {
        ExamPlanData examPlanData = examPlanMapper.selectExamPlanDataByTrainPlanUid(trainPlanUid);
        return examPlanData;
    }


    /**
     * 结束考试，查询所有该培训计划的所有学员，将两次考试的最高分赋值给学员对象的theoryExamScore属性
     * 并判断该学员是否缺考。如果缺考将学员对象的missingExamination属性赋值为"1"
     * 将学员对象的isExamFinished属性赋值为"3"表示该考生本次培训计划的考试已经结束。
     * 更新学员对象，更新培训计划的状态和修改时间。
     * 将本次培训计划的考试情况做个统计（培训计划ud，缺考人数，违纪人数，监考员）写入examRecord表中
     * @param trainPlanUid 培训计划Uid
     * @param username 监考员的名称
     */
    @Override
    public void endExam(String trainPlanUid,String username) {
            //更新培训计划对象信息
            Map<String ,Object> mapTrainPlan =new HashMap<>();
            Long updateTime = System.currentTimeMillis();
            mapTrainPlan.put("updateTime",updateTime);
            mapTrainPlan.put("status","2");
            mapTrainPlan.put("trainPlanUid",trainPlanUid);
            trainPlanMapper.updateTrainPlanMap(mapTrainPlan);
            //将本次培训计划的考试情况做个统计（培训计划uid，缺考人数，违纪人数，监考员）写入examRecord表中
            Integer missExamCount = studentInfoMapper.selectMissExamStudentCount(trainPlanUid);
            Integer brokenRuleCount = studentInfoMapper.selectBrokenRuleStudentCount(trainPlanUid);
            ExamRecord examRecord = new ExamRecord(trainPlanUid, missExamCount, brokenRuleCount, username);
            examRecordMapper.insert(examRecord);
    }

    @Override
    public Integer getMissExamStudentCount(String trainPlanUid) {
        Integer missExamStudentCount = studentInfoMapper.selectMissExamStudentCount(trainPlanUid);
        return missExamStudentCount;
    }

    @Override
    public Integer getBrokenRuleStudentCount(String trainPlanUid) {
        Integer brokenRuleStudentCount = studentInfoMapper.selectBrokenRuleStudentCount(trainPlanUid);
        return brokenRuleStudentCount;
    }


    @Override
    public void updateTrainPlanByUid(RsTrainPlan trainPlan) {
        trainPlanMapper.updateByPrimaryKeySelective(trainPlan);
    }

    @Override
    public List<QbAnswer> getAnswerByPaperId(String paperId) {
        List<QbAnswer> answers = answerMapper.selectAnswersByPaperId(paperId);
        return answers;
    }

    @Override
    public InvigilateStatistics getInvigilateStatistics(String trainPlanUid) {
        Integer totalStudentCount = examPlanMapper.selectTotalStudentCount(trainPlanUid);
        Integer untestedStudentCount = examPlanMapper.selectUntestedStudentCount(trainPlanUid);
        Integer testedStudentCount = examPlanMapper.selectTestedStudentCount(trainPlanUid);
        Integer onTestStudentCount = examPlanMapper.selectOnTestStudentCount(trainPlanUid);
        //Integer missExamStudentCount = studentInfoMapper.selectMissExamStudentCount(trainPlanUid);
        Integer brokenRuleStudentCount = studentInfoMapper.selectBrokenRuleStudentCount(trainPlanUid);
        InvigilateStatistics invigilateStatistics = new InvigilateStatistics(totalStudentCount,untestedStudentCount,
                testedStudentCount,onTestStudentCount,null,brokenRuleStudentCount);
        return invigilateStatistics;
    }

    @Override
    public int updateTrainPlanSelective(RsTrainPlan trainPlan) {
        int rows = trainPlanMapper.updateByPrimaryKeySelective(trainPlan);
        return rows;
    }
}
