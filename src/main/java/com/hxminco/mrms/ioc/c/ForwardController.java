package com.hxminco.mrms.ioc.c;

/**
 * Created by Employee on 2017/7/26.
 */

import com.hxminco.mrms.comm.entry.*;
import com.hxminco.mrms.comm.model.ExamPlanData;
import com.hxminco.mrms.comm.model.ExamQuestion;
import com.hxminco.mrms.comm.model.FinishedExamPlanData;
import com.hxminco.mrms.comm.utils.StringUtil;
import com.hxminco.mrms.ioc.s.ExamInfoSnycService;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import com.hxminco.mrms.ioc.s.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ForwardController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(MrmsServiceController.class);
    @Autowired
    private ExamInfoSnycService examInfoSnycService;
    @Autowired
    private QbExamInfoService qbExamInfoService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/admin/userMan.html")
    public String toUserManagerPage() {
        return "admin/userMan";
    }

    @RequestMapping("/teacher/localdata.html")
    public String toLocaldata() {

        return "teacher/localdata";
    }

    @RequestMapping("/teacher/dataDownLoad.html")
    public String dataDownLoad() {

        return "teacher/data_allocation";
    }

    @RequestMapping("/teacher/toSystemUpdatePage.html")
    public String toSystemUpdatePage() {

        return "teacher/systemUpdate";
    }
    @RequestMapping("/student/exam.html")
    public ModelAndView toExamPage(@RequestParam(value="makeUpPassword",defaultValue = "-1") String makeUpPassword,HttpServletRequest request, HttpSession session) {
        ServletContext context = request.getServletContext();
        RsStudentInfo student = (RsStudentInfo) session.getAttribute("currentUser");
        String trainPlanUid = student.getTrainPlanUid();
        RsTrainPlan trainPlan = examInfoSnycService.getTrainPlanByUid(trainPlanUid);
        StringBuilder msg = new StringBuilder("");
        if (!"1".equals(trainPlan.getStatus())) {
            if("0".equals(trainPlan.getStatus())) {
                msg.append("该培训计划考试还未开始！");
            }else{
                msg.append("该培训计划考试已经结束！");
            }
            ModelAndView mv = new ModelAndView("redirect:/student/studentInfo.html");
            mv.addObject("msg", msg);
            return mv;
        }
        Map<String ,Object> examInfoMap = (Map<String ,Object>)context.getAttribute(student.getTrainPlanUid());
        Map<Integer,List<ExamQuestion>> questionMap = null;
        RsExamPlan examPlan = null;
        QbPaper paper = null;
        if (examInfoMap == null) {
            paper = examInfoSnycService.getPaperByTrainPlanUid(trainPlanUid);
            examPlan = examInfoSnycService.getExamPlanByTrainPlanUid(trainPlanUid);
            questionMap = questionService.getQuestionMap(trainPlanUid);
            examInfoMap = new HashMap<>();
            examInfoMap.put("paper",paper);
            examInfoMap.put("trainPlan",trainPlan);
            examInfoMap.put("examPlan",examPlan);
            examInfoMap.put("questionMap",questionMap);
            context.setAttribute(trainPlan.getUid(), examInfoMap);
        }else{
            questionMap = (Map<Integer,List<ExamQuestion>>)examInfoMap.get("questionMap");
            examPlan = (RsExamPlan)examInfoMap.get("examPlan");
            paper = (QbPaper)examInfoMap.get("paper");
        }
        if("3".equals(student.getIsExamFinished())){
            ModelAndView mv = new ModelAndView("redirect:/student/studentInfo.html");
            mv.addObject("msg","考试已结束！");
            return mv;
        }
        QbExamInfo qbExamInfo = qbExamInfoService.getQbExamInfosByExamCodeAndStatus(student.getExamCode(), "2");
        if("2".equals(student.getIsExamFinished()) && student.getExamFinishTimes()>0){
            if(qbExamInfo==null) {
                if (!makeUpPassword.equals(trainPlan.getMakeUpPassword())) {
                    ModelAndView mv = new ModelAndView("redirect:/student/studentInfo.html");
                    mv.addObject("msg", "补考密码错误！");
                    return mv;
                }
            }
        }
        if (qbExamInfo == null) {//表示是一次新的考试开始
            int examFinishTimes = 0;
            if (student.getExamFinishTimes() != null) {
                examFinishTimes = student.getExamFinishTimes().intValue();
            }
            int size = questionMap.size();
            Random random = new Random();
            int paperOrder = (random.nextInt(size)) + 1;
            if (size >= examPlan.getAllowExamTimes()) {//如果试卷的份数大于学生允许的考试次数，避免学生拿到同一套试题
                List<Integer> lstPaperOrder = qbExamInfoService.getPaperOrderListByExamCode(student.getExamCode());//查出该学生考过的试卷的paperOrder
                if (lstPaperOrder != null && lstPaperOrder.size() > 0) {
                    while (lstPaperOrder.contains(Integer.valueOf(paperOrder))) {
                        paperOrder = (random.nextInt(size)) + 1;
                    }
                }
            }
            qbExamInfo = new QbExamInfo();
            qbExamInfo.setBegintime(new Date());
            qbExamInfo.setId(StringUtil.getUUID());
            qbExamInfo.setPaperOrder(paperOrder);
            qbExamInfo.setPasstime(0);
            qbExamInfo.setExamcode(student.getExamCode());
            qbExamInfo.setStatus("2");
            qbExamInfo.setScore(0.0);
            qbExamInfo.setExamtime(examFinishTimes + 1);
            qbExamInfo.setDuration(paper.getDuration());
            if ("1".equals(student.getIsExamFinished())) {
                student.setIsExamFinished("2");
                qbExamInfoService.insertExamInfoAndStudentExamStatus(qbExamInfo, student);
            } else {
                qbExamInfoService.insertExamInfo(qbExamInfo);
            }
        }
        session.setAttribute("qbExamInfo", qbExamInfo);
        ModelAndView mv = new ModelAndView("student/exam");
        mv.addObject("trainPlan",trainPlan);
        return mv;
    }

    @RequestMapping("/student/examRule.html")
    public String toExamRulePage() {
        return "student/examRule";
    }

    @RequestMapping("/student/studentInfo.html")
    public ModelAndView studentInfoPage(HttpSession session) {
        RsStudentInfo student = (RsStudentInfo) session.getAttribute("currentUser");
        //当老师结束考试或者标记某个学生违纪时，学生试卷会自动提交，学生的信息会有一些改变，此时session中的学生信息并不是最新的信息
        RsStudentInfo dbStudent = examInfoSnycService.getStudentByUid(student.getUid());
        RsTrainPlan trainPlan = examInfoSnycService.getTrainPlanByUid(student.getTrainPlanUid());
        QbExamInfo qbExamInfo = qbExamInfoService.getQbExamInfosByExamCodeOrderDesc(student.getExamCode());
        List<QbExamInfo> qbExamInfos = qbExamInfoService.getQbExamInfosByExamCode(student.getExamCode());
        ModelAndView mv = new ModelAndView("student/studentInfo");
        mv.addObject("qbExamInfo", qbExamInfo);
        session.setAttribute("currentUser", dbStudent);
        mv.addObject("qbExamInfos", qbExamInfos);
        mv.addObject("trainPlan", trainPlan);
        return mv;
    }
    /**
     * 从本地查出未完成考试计划，老师选择相应的考试科目，
     * 将数据初始化，学生才能开始考试！
     *
     * @param pageno   当前页
     * @param pagesize 每页显示的记录数
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/teacher/showLocalDataUnfinished.ajax",method= RequestMethod.POST)
    public Object showLocalExamDataUnfinished(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                                              @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize,
                                              String startTime, String endTime,String status,String querytext,String assessmentNature,String qualification) {
        start();
        Map<String, Object> map = new HashMap<>();
        map.put("start", (pageno - 1) * pagesize);
        map.put("limit", pagesize);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Long start = null;
        if(startTime != null && !startTime.isEmpty()){
            Date dateStartTime= null;
            try {
                dateStartTime = simpleDateFormat .parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(dateStartTime != null) {
                start = dateStartTime.getTime();
            }
        }
        Long end = null;
        if(endTime != null && !endTime.isEmpty()){
            Date dateEndTime= null;
            try {

                dateEndTime = simpleDateFormat .parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(dateEndTime != null) {
                end = dateEndTime.getTime();
            }
        }
        map.put("startTime",start);
        map.put("endTime",end);
        map.put("status",status);
        map.put("querytext",querytext);
        map.put("assessmentNature",assessmentNature);
        map.put("qualification",qualification);
        try {
            List<ExamPlanData> examPlanDatas = examInfoSnycService.getExamPlanDataUnfinished(map);
            int count = examInfoSnycService.getExamPlanCountUnfinished(map);
            param("rows", examPlanDatas);
            param("total", count);
            success(true);
        } catch (Exception e) {
            error("分页查询失败！");
            success(false);
            e.printStackTrace();
        }
        return end();
    }


    /**
     * 从本地查出已完成考试计划，老师选择相应的考试科目进行上传，
     * 或是上传成功的历史记录。
     *
     * @param pageno   当前页
     * @param pagesize 每页显示的记录数
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/teacher/showLocalDataFinished.ajax",method = RequestMethod.POST)
    public Object showLocalExamDataFinished(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                                            @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize,
                                            String startTime, String endTime,String status,String querytext,String assessmentNature,String qualification) {
        start();
        Map<String, Object> map = new HashMap<>();
        map.put("start", (pageno - 1) * pagesize);
        map.put("limit", pagesize);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Long start = null;
        if(startTime != null && !startTime.isEmpty()){
            Date dateStartTime= null;
            try {
                dateStartTime = simpleDateFormat .parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            if(dateStartTime != null) {
                start = dateStartTime.getTime();
            }

        }
        Long end = null;
        if(endTime != null && !endTime.isEmpty()){
            Date dateEndTime= null;
            try {

                dateEndTime = simpleDateFormat .parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(dateEndTime != null) {
                end = dateEndTime.getTime();
            }
        }
        map.put("startTime",start);
        map.put("endTime",end);
        map.put("status",status);
        map.put("querytext",querytext);
        map.put("assessmentNature",assessmentNature);
        map.put("qualification",qualification);
        try {
            List<FinishedExamPlanData> examPlanDatas = examInfoSnycService.getExamPlanDataFinished(map);
            int count = examInfoSnycService.getExamPlanCountFinished(map);
            param("rows", examPlanDatas);
            param("total", count);
            success(true);
        } catch (Exception e) {
            error("分页查询失败！");
            success(false);
            e.printStackTrace();
        }
        return end();
    }
}
