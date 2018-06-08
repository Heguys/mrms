package com.hxminco.mrms.ioc.c;

import com.hxminco.mrms.comm.entry.*;
import com.hxminco.mrms.comm.model.ExamQuestion;
import com.hxminco.mrms.comm.utils.StringUtil;
import com.hxminco.mrms.ioc.s.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Employee on 2017/7/26.
 */
@Controller
@RequestMapping("/student")
public class ExamController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(MrmsServiceController.class);
    @Autowired
    private QbExamQuestionsService qbExamQuestionsService;
    @Autowired
    private RsStudentInfoService rsStudentInfoService;
    @Autowired
    private QbExamInfoService qbExamInfoService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ExamInfoSnycService examInfoSnycService;
    @Autowired

    /**
     * 根据数据库查出的考试状态为2（也就是未完成的考试）考试记录，
     * 如果查询的结果为null，表示是新的一次考试，从application域
     * 中获取考试题目。
     * 如果查询的结果不为null，表示是未完成的考试，从数据库中找出该学生的
     * 考试题目和答题信息，并对他答过的题进行回显
     *
     * @param request 请求
     * @param session 会话
     * @return
     */
    @ResponseBody
    @RequestMapping("/startExam.ajax")
    public Object studentStartExam(HttpServletRequest request, HttpSession session) {
        start();
        try {
            RsStudentInfo student = (RsStudentInfo) session.getAttribute("currentUser");
            ServletContext context = request.getServletContext();
            Map<String ,Object> examInfoMap = (Map<String ,Object>)context.getAttribute(student.getTrainPlanUid());
            Map<Integer, List<ExamQuestion>> questionMap = (Map<Integer, List<ExamQuestion>>) examInfoMap.get("questionMap");
            final QbExamInfo qbExamInfo = (QbExamInfo)session.getAttribute("qbExamInfo");
            int paperOrder = qbExamInfo.getPaperOrder();
            long timeLong = qbExamInfo.getExamRemainSeconds();
            List<ExamQuestion> lstExamQuestion = questionMap.get(paperOrder);
            List<QbExamQuestions> lstQbExamQuestions = qbExamQuestionsService.getQbExamQuestionsByQbExamInfo(qbExamInfo);
            if (lstExamQuestion != null && lstExamQuestion.size() > 0) {
                param("questions", lstExamQuestion);
                param("questionReview", lstQbExamQuestions);
                param("timeLong", timeLong);
                success(true);
            }else {
                String msg = "试题异常！";
                error(msg);
                success(false);
            }
        } catch (Exception e) {
            success(false);
            error("试题加载出现未知错误！");
            logger.error(e.getMessage(),e.getCause());
            e.printStackTrace();
        }
        return end();
    }

    /**
     * 学生答题的时候，每答一道题就向后台数据库
     * 插入一条记录，如果是修改答案，则更新对应的记录。
     *
     * @param examQuestion 数据库一条记录所对应的对象
     * @param session      会话
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveQuestionAnswer.ajax", method = RequestMethod.POST)
    public Object saveQuestionAnswer(QbExamQuestions examQuestion,@RequestParam(value="type",defaultValue = "1") String type,HttpSession session) {
        start();
        try {
            RsStudentInfo student = (RsStudentInfo) session.getAttribute("currentUser");
            RsStudentInfo dbStudent = rsStudentInfoService.getRsStudentByUid(student.getUid());
            if("3".equals(dbStudent.getIsExamFinished())){//考试结束（非自己提交，有两种可能：①老师标记违纪 ②老师强行结束考试 ）
                success(false);
                param("skip",true);//表示需要跳转到学生信息页面
                if("1".equals(dbStudent.getRuleBroken())) {
                    error("您出现违纪行为，考试结束！");
                }else{
                    error(" 监考老师已经结束考试，成绩已经自动提交！");
                }
                return end();
            }
            int examFinishTimes = student.getExamFinishTimes() == null?0:student.getExamFinishTimes().intValue();
            examQuestion.setExamtime(examFinishTimes + 1);
            examQuestion.setExamcode(student.getExamCode());
            List<String> lstCorrectAnswerId = qbExamQuestionsService.queryLstCorrectAnswerId(examQuestion.getQuestionid());
            boolean flag = false;
            QbExamQuestions dbQbExamQuestions = qbExamQuestionsService.getQbExamQuestions(examQuestion);
            if (type == "2") {//表示多选题
                String[] split = examQuestion.getAnswerid().split(",");
                List<String> strings = Arrays.asList(split);
                flag = lstCorrectAnswerId.containsAll(strings) && strings.containsAll(lstCorrectAnswerId);
            }else{
                flag = Objects.equals(examQuestion.getAnswerid(),lstCorrectAnswerId.get(0));
            }
            if(flag){
                examQuestion.setScore(examQuestion.getScore());
            }else{
                examQuestion.setScore(0.0);
            }
            //判断是修改同一道题的答案，还是不同的题！
            if (dbQbExamQuestions == null) {
                examQuestion.setId(StringUtil.getUUID());
                qbExamQuestionsService.addQbExamQuestions(examQuestion);
            } else {
                examQuestion.setId(dbQbExamQuestions.getId());
                qbExamQuestionsService.updateQbExamQuestions(examQuestion);
            }
            success(true);
        } catch (Exception e) {
            success(false);
            error("答题失败！");
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return end();
    }

    /**
     * 学生提交试卷，学生的已经考试的次数student.examFinishTimes会加1，学生分为两种，一是为补考的学生student.makeUp='1',补考的学生只有一次考试的机会，一种是为正常考试的学生student.makeUp='0'
     * 如果考试成绩通过，那么学生整场考试结束（student.isExamFinished='3'）student.theoryExamScore=当前考试成绩。student.theoryExamPassed='1'
     * 如果学生考试未通过，取出学生的student.theoryExamScore与当前考试成绩比较，大的会重新赋值给student.theoryExamScore，student.theoryExamPassed='0'
     * 当考试次数大于等于该考试计划的允许的考试次数，学生整场考试结束student.isExamFinished='3'。
     * 判断学生是否还有考试机会，如果有返回的页面就会显示开始考试，
     * @param session 会话
     * @param request 请求
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitExamPaper.ajax")
    public Object submitExamPaper(HttpSession session, HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        RsStudentInfo student = (RsStudentInfo) session.getAttribute("currentUser");
        RsTrainPlan trainPlan = examInfoSnycService.getTrainPlanByUid(student.getTrainPlanUid());
        Map<String ,Object> examInfoMap = (Map<String ,Object>)context.getAttribute(student.getTrainPlanUid());
        start();
        if("2".equals(trainPlan.getStatus())){
            success(false);
            param("skip",true);
            error("考试已经结束！");
            return end();
        }
        QbExamInfo qbExamInfo = qbExamInfoService.getQbExamInfosByExamCodeAndStatus(student.getExamCode(), "2");
        if(qbExamInfo == null){
            success(false);
            param("skip",true);
            error("试题已经提交过了，不允许重复提交！");
            return end();
        }
        QbPaper paper = (QbPaper) examInfoMap.get("paper");
        RsExamPlan examPlan = (RsExamPlan) examInfoMap.get("examPlan");
        int examFinishTimes = student.getExamFinishTimes() == null?0:student.getExamFinishTimes().intValue();
        int examTime = examFinishTimes +1;
        Double dbScore = qbExamQuestionsService.countExamScore(student.getExamCode(), examTime);
        double score = dbScore == null?0.0:dbScore.doubleValue();
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
        try {
            transactionService.updateExamInfoAndStudentInfo(qbExamInfo,student);
            success(true);
            param("skip",true);
            param("msg", "试卷提交成功！");
            return end();
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
            success(false);
            param("skip",false);
            error("试卷提交失败！");
            return end();
        }
    }
}
