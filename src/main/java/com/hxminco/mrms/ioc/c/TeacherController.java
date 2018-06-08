package com.hxminco.mrms.ioc.c;

import com.google.gson.Gson;
import com.hxminco.mrms.comm.entry.*;
import com.hxminco.mrms.comm.model.*;
import com.hxminco.mrms.ioc.s.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Employee on 2017/7/29.
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(MrmsServiceController.class);
    @Autowired
    private ExamInfoSnycService examInfoSnycService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RsStudentInfoService rsStudentInfoService;
    @Autowired
    private InstitutionInfoSnycService institutionInfoSnycService;
    @Autowired
    private QbExamQuestionsService qbExamQuestionsService;
    @Autowired
    private QbExamInfoService qbExamInfoService;
    @Autowired
    private TransactionService transactionService;
    @ResponseBody
    @RequestMapping("/teacherAllowExamStart.ajax")
    public Object teacherAllowExamStart(@RequestParam("trainPlanUid") String trainPlanUid,HttpServletRequest request){
        start();
        try {
            ServletContext context = request.getServletContext();
            StringBuilder msg = new StringBuilder("");
            QbPaper paper = examInfoSnycService.getPaperByTrainPlanUid(trainPlanUid);
            if (paper == null){
                msg.append("试卷信息不存在!");
                error(msg.toString());
                return end();
            }
            RsTrainPlan trainPlan = examInfoSnycService.getTrainPlanByUid(trainPlanUid);
            if (trainPlan == null){
                msg.append("培训计划信息不存在!");
                error(msg.toString());
                return end();
            }
            RsExamPlan examPlan = examInfoSnycService.getExamPlanByTrainPlanUid(trainPlanUid);
            if (examPlan == null){
                msg.append("考试计划信息不存在!");
                error(msg.toString());
                return end();
            }
            Map<Integer, List<ExamQuestion>> questionMap = questionService.getQuestionMap(trainPlanUid);
            if(questionMap == null || questionMap.size() == 0){
                msg.append("试题信息不完整!");
                error(msg.toString());
                return end();
            }
            Map<String ,Object> examInfoMap = new HashMap<>();
            examInfoMap.put("paper",paper);
            examInfoMap.put("trainPlan",trainPlan);
            examInfoMap.put("examPlan",examPlan);
            examInfoMap.put("questionMap",questionMap);
            context.setAttribute(trainPlan.getUid(), examInfoMap);
            trainPlan.setStatus("1");
            trainPlan.setUpdateTime(System.currentTimeMillis());
            examInfoSnycService.updateTrainPlanSelective(trainPlan);
            examInfoMap.put("trainPlan",trainPlan);
            success(true);
        }catch(Exception e){
            success(false);
            error("开始考试失败！");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/updateInstitutionJs.ajax")
    public Object updateInstitutionJs(HttpServletRequest request,HttpSession session){
        start();
        Teacher teacher = (Teacher)session.getAttribute("currentUser");
        List<RsInstitution> lstRsInstitution = institutionInfoSnycService.getListInstitution(teacher.getInstitutionuid());
        Gson gson = new Gson();
        String json = gson.toJson(lstRsInstitution);
        FileOutputStream fos = null;
        try {
            String path = request.getServletContext().getRealPath("/")+"\\res\\js\\institution.js";
            fos = new FileOutputStream(new File(path));
            fos.write(json.getBytes("utf-8"));
            success(true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return end();
    }

    @RequestMapping("/toInvigilatorPage.html")
    public ModelAndView toInvigilatorPage(@RequestParam("trainPlanUid")String trainPlanUid){
        ModelAndView mv = null;
        start();
        try{
            List<InvigilatePageModel> lstInvigilatePageModel = rsStudentInfoService.getRsStudentInfoListByTrainPlanUid(trainPlanUid);
            InvigilateStatistics invigilateStatistics = examInfoSnycService.getInvigilateStatistics(trainPlanUid);
            ExamPlanData examPlanData = examInfoSnycService.getExamPlanDataByTrainPlanUid(trainPlanUid);
            success(true);
            mv = new ModelAndView("/teacher/invigilate");
            mv.addObject("lstInvigilatePageModel", lstInvigilatePageModel);
            mv.addObject("invigilateStatistics", invigilateStatistics);
            mv.addObject("examPlanData", examPlanData);
            return mv;
        }catch(Exception e){
            mv = new ModelAndView("redirect:/teacher/localdata.html");
            mv.addObject("msg","数据异常!");
            success(false);
            e.printStackTrace();
            logger.error(e.getMessage());
            return mv;
        }
    }

    /**
     *
     * @param trainPlanUid 培训计划uid
     * @param keyWord      关键词搜索
     * @param labelNumber 监考页面根据统计标签序号查询学生 序号（1~5）"1"表示总人数标签，"2"表示未考试人数标签，"3"表示考试中人数标签，
     *                    "4"表示已完成人数标签，"5"表示违纪人数标签
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/keyWordSearchAndRefreshing.ajax",method = RequestMethod.POST)
    public Object keyWordSearchAndRefreshing(@RequestParam("trainPlanUid")String trainPlanUid,String keyWord,String labelNumber){
        start();
        ModelAndView mv = null;
        try{
            Map<String, String> map = new HashMap<>();
                if(labelNumber != null){
                switch (labelNumber){
                    case "1":
                    map.put("status",null);//传null，该字段在mybatis的sql中不会生效，也就是该字段不会加入过滤条件(总人数不需要加入过滤条件)
                        break;
                    case "2":
                        map.put("status","1");
                        break;
                    case "3":
                        map.put("status","2");
                        break;
                    case "4":
                        map.put("status","3");
                        break;
                    case "5":
                        map.put("ruleBroken","1");
                        break;
                    default:
                        break;
                }
            }
            map.put("trainPlanUid",trainPlanUid);
            map.put("keyWord",keyWord);
            List<InvigilatePageModel> lstInvigilatePageModel = rsStudentInfoService.getRsStudentInfoListByTrainPlanUidAndKeyWord(map);
            InvigilateStatistics invigilateStatistics = examInfoSnycService.getInvigilateStatistics(trainPlanUid);
            param("lstInvigilatePageModel", lstInvigilatePageModel);
            param("invigilateStatistics", invigilateStatistics);
            success(true);
        }catch(Exception e){
            error("关键字查询失败！");
            success(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return end();
    }

    @ResponseBody
    @RequestMapping(value="/updateBrokenRuleStatus.ajax",method = RequestMethod.POST)
    public Object updateBrokenRoleStatus(RsStudentInfo student){
        start();
        try {
            RsStudentInfo dbStudent = rsStudentInfoService.getRsStudentByUid(student.getUid());
            QbExamInfo qbExamInfo = null;
            if("1".equals(student.getRuleBroken())) {
                if(!"1".equals(dbStudent.getRuleBroken())){
                   qbExamInfo = qbExamInfoService.getQbExamInfosByExamCodeOrderDesc(dbStudent.getExamCode());
                    student.setIsExamFinished("3");
                    student.setTheoryExamScore(0.0);
                    if (qbExamInfo != null) {
                        qbExamInfo.setScore(0.0);
                        qbExamInfo.setStatus("3");
                        qbExamInfo.setEndtime(new Date());
                        qbExamInfo.setPassed("0");
                        qbExamInfo.setRuleBroken("1");
                    }
                }else{
                    success(false);
                    error("该学员已是违纪状态，不能再次标记违纪！");
                    return end();
                }
            }else{
                if(!"1".equals(dbStudent.getRuleBroken())){
                    success(false);
                    error("学员未被标记违纪，不能取消违纪！");
                    return end();
                }
                qbExamInfo = qbExamInfoService.getQbExamInfosByExamCodeOrderDesc(dbStudent.getExamCode());
                if(qbExamInfo != null) {
                    qbExamInfo.setStatus("2");
                    qbExamInfo.setRuleBroken("0");
                    student.setIsExamFinished("2");
                }else{
                    student.setIsExamFinished("1");
                }
            }
            transactionService.updateExamInfoAndStudentInfo(qbExamInfo,student);
            InvigilatePageModel invigilatePageModel = rsStudentInfoService.getRsStudentInfoByStudentUid(student.getUid());
            InvigilateStatistics invigilateStatistics = examInfoSnycService.getInvigilateStatistics(dbStudent.getTrainPlanUid());
            param("invigilatePageModel",invigilatePageModel);
            param("invigilateStatistics",invigilateStatistics);
            success(true);
        }catch (Exception e){
            success(false);
            error("标记违纪失败！");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return end();
    }

    @ResponseBody
    @RequestMapping(value="/updateMissExamStatus.ajax",method = RequestMethod.POST)
    public Object updateMissExamStatus(RsStudentInfo student){
        start();
        try {
            InvigilatePageModel invigilatePageModel = rsStudentInfoService.getRsStudentInfoByStudentUid(student.getUid());
            rsStudentInfoService.updateStudentInfo(student);
            Integer missExamStudentCount = examInfoSnycService.getMissExamStudentCount(student.getTrainPlanUid());
            param("invigilatePageModel",invigilatePageModel);
            param("missExamStudentCount",missExamStudentCount);
            success(true);
        }catch (Exception e){
            success(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return end();
    }

    /**
     * 暂时没用到，老师结束考试之前提示取得是页面统计的"正在考试的学员人数"
     * @param trainPlan
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getOnTestStudentCountBeforeEndExam.ajax")
    public Object getOnTestStudentCountBeforeEndExam(RsTrainPlan trainPlan){
        start();
        Integer onTestStudentCount = examInfoSnycService.getOnTestStudentCount(trainPlan.getUid());
        success(true);
        param("onTestStudentCount",onTestStudentCount);
        return end();
    }

    @ResponseBody
    @RequestMapping(value="/setMakeUpPassword.ajax" ,method = RequestMethod.POST)
    public Object setMakeUpPassword(RsTrainPlan trainPlan){
        start();
        try {
            examInfoSnycService.updateTrainPlanSelective(trainPlan);
            success(true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            error("补考密码更新失败！");
            success(false);
        }
        return end();
    }

    /**
     * 结束考试：首先查出该计划所有未结束考试的学生（student.isExamFinished != '3'在sql中加入过滤条件）分为3种情况
     * 第一种：有未完成的考试记录，先将该考试记录结算，算出分数，是否通过，是否为补考，结束时间。给学生设置对应的examFinishTimes，theoryExamScore，theoryExamPassed
     * 第二种：缺考的学生，设置学生的theoryExamScore=0.0，theoryExamPassed='0'
     * 第三种：第一次考试未通过，第二次考试未开始。在他第一次提交试卷的时候学生的theoryExamScore，theoryExamPassed就设置好了，所以什么都不用做
     * 最后将所有未结束考试学生的isExamFinished设置为'3'
     * 然后更新培训计划，生成考试历史记录信息插入examRecord表中。
     * @param session
     * @param request
     * @param trainPlanUid
     * @return
     */
    @ResponseBody
    @RequestMapping("teacherEndExam.ajax")
    public Object teacherEndExam(HttpSession session,HttpServletRequest request,@RequestParam("trainPlanUid")String trainPlanUid){
        ServletContext context = request.getServletContext();
       Teacher teacher =(Teacher)session.getAttribute("currentUser");
        Map<String ,Object> examInfoMap = (Map<String ,Object>)context.getAttribute(trainPlanUid);
        QbPaper paper = null;
        if(examInfoMap == null){
            paper =  examInfoSnycService.getPaperByTrainPlanUid(trainPlanUid);
        }else {
            paper = (QbPaper) examInfoMap.get("paper");
        }
        List<RsStudentInfo> lstStudent =  rsStudentInfoService.queryUnFinishedStudentList(trainPlanUid);//就是student.isExamFinished不为'3'(即'1'或'2')=>包括缺考的学生，未结束考试的学生（未结束的学生分为两种：有考试记录status='2'的；还有第一次考试未通过，第二次考试未开始的。）
        Map<String,QbExamInfo> qbExamInfoMap = qbExamInfoService.queryOnTestingExamInfoMap(trainPlanUid);//有考试记录并且status='2'的记录
        if(lstStudent != null && lstStudent.size()>0){
            for (RsStudentInfo one:lstStudent){
                QbExamInfo qbExamInfo = qbExamInfoMap.get(one.getExamCode());
                if(qbExamInfo != null) { //有未完成的考试记录
                    int examFinishTimes = one.getExamFinishTimes()==null?0:one.getExamFinishTimes().intValue();
                    int examTime = examFinishTimes + 1;
                    Double dbScore = qbExamQuestionsService.countExamScore(one.getExamCode(), examTime);//计算本次考试成绩
                    double score = dbScore == null?0.0:dbScore.doubleValue();
                    qbExamInfo.setEndtime(new Date());
                    if ("0".equals(one.getMakeUp()) && examFinishTimes == 0) {
                        qbExamInfo.setMakeup(0);
                    } else {
                        qbExamInfo.setMakeup(1);
                    }
                    qbExamInfo.setScore(score);
                    qbExamInfo.setStatus("3");
                    examFinishTimes = examFinishTimes + 1;
                    one.setExamFinishTimes(examFinishTimes);
                    if (qbExamInfo.getExamtime() == 1) {
                        one.setTheoryExamScore1(score);
                    } else {
                        one.setTheoryExamScore2(score);
                    }
                    if (score >= paper.getPassmark()) {
                        qbExamInfo.setPassed("1");
                        one.setTheoryExamScore(score);
                        one.setTheoryExamPassed("1");
                    } else {
                        qbExamInfo.setPassed("0");
                        double theoryExamScore = one.getTheoryExamScore() == null?0.0:one.getTheoryExamScore().intValue();
                        double maxScore = theoryExamScore >= score ? theoryExamScore : score;
                        one.setTheoryExamScore(maxScore);
                        one.setTheoryExamPassed("0");
                    }
                }else {
                    int count = rsStudentInfoService.selectStudentExamRecord(one.getUid());
                    if (count == 0 && !"1".equals(one.getRuleBroken())) {
                        one.setMissingExamination("1");
                        one.setTheoryExamPassed("0");
                        one.setTheoryExamScore(0.0);
                    }
                }
                one.setIsExamFinished("3");
                transactionService.updateExamInfoAndStudentInfo(qbExamInfo, one);
            }
        }
        context.removeAttribute(trainPlanUid);
        start();
        try {
            examInfoSnycService.endExam(trainPlanUid,teacher.getUsername());
            success(true);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            error("结束考试失败！");
            logger.error(e.getMessage());
        }
        return end();
    }
}
