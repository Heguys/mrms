package com.hxminco.mrms.ioc.c;

import com.hxminco.mrms.api.MrmsService;
import com.hxminco.mrms.comm.entry.*;
import com.hxminco.mrms.comm.entry.xml.*;
import com.hxminco.mrms.comm.model.*;
import com.hxminco.mrms.comm.utils.*;
import com.hxminco.mrms.ext.xml.XmlSqlUtil;
import com.hxminco.mrms.ioc.s.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.*;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Employee on 2017/7/13.
 */
@Controller
@RequestMapping("/teacher")
public class MrmsServiceController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(MrmsServiceController.class);
    @Autowired
    private MrmsService mrmsService;
    @Autowired
    private ExamInfoSnycService examInfoSnycService;
    @Autowired
    private CommonOptionInfoSnycService commonOptionInfoSnycService;
    @Autowired
    private InstitutionInfoSnycService institutionInfoSnycService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/data_allocation.html")
    public String dataPage(HttpSession session) {
        return "/teacher/data";
    }

    @ResponseBody
    @RequestMapping(value = "/examInfoDownLoad.ajax", method = RequestMethod.POST)
    public Object examInfoDownLoad(@RequestParam("trainPlanUid") String trainPlanUid, HttpServletRequest request) {
        start();
        RsTrainPlan trainPlan = examInfoSnycService.getTrainPlanByUid(trainPlanUid);
        if (trainPlan != null) {
            error("该计划已经存在，下载失败！");
            success(false);
            return end();
        }
        String xml = null;
        String diskNumber = null;
        try {
            diskNumber = PCSerialNumberManager.getDiskNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            xml = mrmsService.examInfoDownLoad(trainPlanUid+ "_" + diskNumber);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else if (e instanceof MyException) {
                error(e.getMessage());
            } else {
                error("下载出现问题！");
            }
            logger.error(e.getMessage());
            success(false);
            return end();
        }
        if (xml == null || xml.isEmpty()) {
            String msg = "考试所需相关信息不完善，下载失败！";
            error(msg);
            success(false);
            return end();
        }
        ExamInfo examInfo = null;
        try {
            examInfo = JAXBUtils.unmarshaller(xml, ExamInfo.class);
        } catch (JAXBException e) {
            success(false);
            error("数据转换异常！");
            return end();
        }
        if (examInfo == null) {
            String msg = "没有相关的考试信息";
            error(msg);
            success(false);
            return end();
        }
        try {
            List<QbQuestion> questions = examInfo.getQuestions();
            List<QbAnswer> answers = examInfo.getAnswers();
            List<RsStudentInfo> studentInfos = examInfo.getStudentInfos();
            //String path = System.getenv("catalina_home")+"\\webapps\\monitor\\res\\img\\" + trainPlanUid;//绝对路径
            String bakPath = System.getenv("catalina_home")+"\\examImg\\" + trainPlanUid;//下载到本项目下之后备份到本地的绝对路径
            String path = request.getServletContext().getRealPath("/")+ "/res/examImg/"+trainPlanUid; //下载到本项目的目录
            FileUtil.createDir(path);
            //String getImgPath = Tools.getValue("imgPathPrefix")+trainPlanUid+"/";
            String getImgPath = request.getContextPath()+"/res/examImg/"+trainPlanUid+"/";
            String imgString = "";
            String[] split = null;
            String suffix = "";
            String imgStr = "";
            String imgName = "";
            for (RsStudentInfo oneStu : studentInfos) {
                imgString = oneStu.getPhotoUrl();
                split = imgString.split("#_#_#");
                suffix = split[0];
                imgStr = split[1];
                imgName = StringUtil.getUUID() + "." + suffix;
                oneStu.setPhotoUrl(getImgPath + imgName);
                oneStu.setUid(StringUtil.getUUID());
                String imgFilePath = path + "\\" + imgName;
                Base64Image.generateImage(imgStr, String.valueOf(new File(imgFilePath)));
            }
            Map<String, String> map = new HashMap<>();
            String tmpUrl = "";
            String destUrl = "";
            String imgFilePath = "";

            for (QbQuestion one : questions) {
                String content = one.getDescription();
                if (content.contains("<img")) {
                    tmpUrl = content.substring(content.indexOf("http"), content.indexOf("/>"));
                    destUrl = tmpUrl.substring(0, tmpUrl.indexOf("\""));
                    String name = destUrl.substring(destUrl.lastIndexOf("/") + 1, destUrl.length());
                    imgFilePath = path + "\\" + name;//图片存储的真实路径
                    one.setDescription(content.replace(destUrl, getImgPath+name));//设置图片的访问路径
                    map.put(destUrl, imgFilePath);
                }
            }
            for (QbAnswer one : answers) {
                String content = one.getDescription();
                if (content.contains("<img")) {
                    tmpUrl = content.substring(content.indexOf("http"), content.indexOf("/>"));
                    destUrl = tmpUrl.substring(0, tmpUrl.indexOf("\'"));
                    String name = destUrl.substring(destUrl.lastIndexOf("/") + 1, destUrl.length());
                    imgFilePath = path + "\\" + name;
                    one.setDescription(content.replace(destUrl,getImgPath +name));
                    map.put(destUrl, imgFilePath);
                }
            }
            ImageDownLoad.saveToFile(map);//将图片从MongoDB下载到mrms项目目录下
            File home = new File(bakPath);
            if (home != null && home.exists() && home.isDirectory()) {
                FileUtils.deleteDirectory(home);
            }
            FileUtils.copyDirectoryToDirectory(new File(path),home);//将项目下的考试相关的图片复制到本地Tomcat目录下
            examInfoSnycService.updateExamInfo(examInfo);
            success(true);
        }catch (Exception e){
            success(false);
            error("插入数据库异常，试题下载失败！");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping(value = "/commonOptionDownLoad.ajax", method = RequestMethod.POST)
    public Object commonOptionDownLoad() {
        start();
        try {
            String xml = mrmsService.commonOptionDownLoad();
            CommonOptionInfo commonOptionInfo = null;
            try {
                commonOptionInfo = JAXBUtils.unmarshaller(xml, CommonOptionInfo.class);
            } catch (JAXBException e) {
                success(false);
                e.printStackTrace();
            }
            if (commonOptionInfo == null) {
                String msg = "没有相关的基本信息";
                error(msg);
                success(false);
                return end();
            }
            int num = commonOptionInfo.getLstRsCommonOptions().size();
            int rows = commonOptionInfoSnycService.addCommonOptionInfo(commonOptionInfo);
            success(num == rows);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("下载出现问题，基本信息下载失败！");
            }
            logger.error(e.getMessage());
            success(false);
            e.printStackTrace();
        }
        return end();
    }


    @ResponseBody
    @RequestMapping(value = "/institutionInfoDownLoad.ajax", method = RequestMethod.POST)
    public Object institutionIfoDownLoad() {
        start();
        try {
            String xml = mrmsService.institutionIfoDownLoad();
            InstitutionInfo institutionInfo = null;
            try {
                institutionInfo = JAXBUtils.unmarshaller(xml, InstitutionInfo.class);
            } catch (JAXBException e) {
                success(false);
                e.printStackTrace();
            }
            if (institutionInfo == null) {
                String msg = "没有相关的机构信息";
                error(msg);
                success(false);
                return end();
            }
            int num = institutionInfo.getLstRsInstitutions().size();
            int rows = institutionInfoSnycService.addInstitutionInfo(institutionInfo);
            success(num == rows);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("下载出现问题，机构信息下载失败！");
            }
            logger.error(e.getMessage());
            success(false);
            e.printStackTrace();
        }
        return end();
    }

    @ResponseBody
    @RequestMapping(value = "trainPlanPageQuery.ajax", method = RequestMethod.POST)
    public Object showExamPlanDataList(HttpSession session, String querytext, Integer pageno, Integer pagesize,
                                       String startTime, String endTime, String assessmentNature, String qualification) {
        Teacher teacher = (Teacher) session.getAttribute("currentUser");
        String institutionUid = teacher.getInstitutionuid();
        PageQueryRoot root = new PageQueryRoot();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setLimit(String.valueOf(pagesize));
        pageQuery.setStart(String.valueOf((pageno - 1) * pagesize));
        Map<String, String> mapFilterString = new HashMap<>();
        mapFilterString.put("querytext", querytext);
        mapFilterString.put("assessmentNature", assessmentNature);
        mapFilterString.put("qualification", qualification);
        mapFilterString.put("startTime", startTime);
        mapFilterString.put("endTime", endTime);
        mapFilterString.put("institutionUid", institutionUid);
        List<Filter> filters = XmlSqlUtil.makeFilters(mapFilterString);
        List<String> listReturnString = new ArrayList<>();
        listReturnString.add("trainPlanUid");
        listReturnString.add("trainInstitutionName");
        listReturnString.add("trainPlanName");
        listReturnString.add("qualificationName");
        listReturnString.add("assessmentName");
        listReturnString.add("examBeginDate");
        listReturnString.add("examEndDate");
        listReturnString.add("examLimit");
        List<Return> returns = XmlSqlUtil.makeReturns(listReturnString);
        pageQuery.setReturns(returns);
        pageQuery.setFilters(filters);
        root.setPageQuery(pageQuery);
        start();
        String xml = null;
        try {
            XStream xs = new XStream(new DomDriver("utf8"));
            xs.processAnnotations(PageQueryRoot.class);
            xs.processAnnotations(PageQuery.class);
            xs.processAnnotations(Filter.class);
            xml = xs.toXML(root);
            String returnXml = mrmsService.examPlanDataSync(xml);
            PageDataRoot pageDataRoot = XmlSqlUtil.toBean(returnXml, PageDataRoot.class);
            PageData pageDate = pageDataRoot.getPageData();
            List<PageDataObject> rows = new ArrayList<>();
            List<Record> records = pageDate.getRecords();
            List<String> lstTrainPlanUid = examInfoSnycService.queryTrainPlanUidList();
            if (records != null && records.size() > 0) {
                for (Record one : records) {
                    PageDataObject page = new PageDataObject();
                    if (lstTrainPlanUid != null && lstTrainPlanUid.size() > 0 && lstTrainPlanUid.contains(one.getFields().get(0).getValue())) {
                            page.setStatus("5");
                            rows.add(page);
                    } else {
                            page.setStatus("4");
                            rows.add(page);
                    }
                    page.setTrainPlanUid(one.getFields().get(0).getValue());
                    page.setTrainInstitutionName(one.getFields().get(1).getValue());
                    page.setTrainPlanName(one.getFields().get(2).getValue());
                    page.setQualificationName(one.getFields().get(3).getValue());
                    page.setAssessmentName(one.getFields().get(4).getValue());
                    page.setExamBeginDate(one.getFields().get(5).getValue());
                    page.setExamEndDate(one.getFields().get(6).getValue());
                    page.setExamLimit(one.getFields().get(7).getValue());
                }
            }
            success(true);
            param("rows", rows);
            param("total", pageDate.getTotal());
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("分页查询失败！");
            }
            logger.error(e.getMessage());
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/checkCommonOptionDataUpdate.ajax")
    public Object checkCommonOptionDataUpdate(){
        Timestamp createTime = commonOptionInfoSnycService.getLatestCreateTime();
        String time = String.valueOf(createTime);
        String flag = null;
        start();
        try{
            flag = mrmsService.checkCommonOptionDataUpdate(time);
        }catch (Exception e){
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("检测基础数据更新失败！");
            }
            logger.error(e.getMessage());
            success(false);
            return end();
        }
        if("true".equals(flag)){
            success(true);
            param("isUpdate", true);
            param("msg","基础数据有更新，请同步！");
        }else{
            success(true);
            param("isUpdate", false);
            param("msg", "基础数据已是最新数据！");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/getLatestCreateTime.ajax")
    public Object getLatestCreateTime(){
        start();
        Timestamp dbInstitutionCreateTime = institutionInfoSnycService.getLatestCreateTime();
        String institutionCreateTime = String.valueOf(dbInstitutionCreateTime).substring(0,11);
        Timestamp dbCommonOptionCreateTime = commonOptionInfoSnycService.getLatestCreateTime();
        String commonOptionCreateTime = String.valueOf(dbCommonOptionCreateTime).substring(0,11);
        param("institutionCreateTime",institutionCreateTime);
        param("commonOptionCreateTime",commonOptionCreateTime);
        success(true);
        return end();
    }


    @ResponseBody
    @RequestMapping("/checkInstitutionDataUpdate.ajax")
    public Object checkInstitutionDataUpdate(){
        Timestamp createTime = institutionInfoSnycService.getLatestCreateTime();
        String time = String.valueOf(createTime);
        String flag = null;
        start();
        try{
            flag = mrmsService.checkInstitutionDataUpdate(time);
        }catch (Exception e){
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("检测机构信息更新失败！");
            }
            logger.error(e.getMessage());
            success(false);
            return end();
        }
        if("true".equals(flag)){
            success(true);
            param("isUpdate", true);
            param("msg","机构信息有更新，请同步！");
        }else{
            success(false);
            param("isUpdate", false);
            param("msg","机构信息已是最新数据！");
        }
        return end();
    }


    @ResponseBody
    @RequestMapping("/checkProjectVersionUpdate.ajax")
    public Object checkProjectVersionUpdate(){
        String oldVersionNumber =Tools.getValue("version");
        String xml = null;
        start();
        try{
            xml = mrmsService.checkProjectVersionUpdate(oldVersionNumber);
            if(xml != null){
                Version version = null;
                try {
                    version = JAXBUtils.unmarshaller(xml, Version.class);
                } catch (JAXBException e) {
                    success(false);
                    e.printStackTrace();
                    error("xml对象转换失败！");
                    return end();
                }
                success(true);
                param("isUpdate",true);
                param("version", version);
                param("oldVersionNumber",oldVersionNumber);
            }else{
                success(true);
                param("isUpdate",false);
                param("oldVersionNumber",oldVersionNumber);
                param("msg", "当前系统已是最新版本！");
            }
        }catch (Exception e){
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("检测系统版本更新失败！");
            }
            logger.error(e.getMessage());
            param("oldVersionNumber",oldVersionNumber);
            success(false);
            return end();
        }
        return end();
    }

    /**
     * 上传该计划的考试信息，主要有三张表，学生信息表(rs_student_info),考试记录表(qb_exam_info),学生答题记录表(qb_exam_questions).
     * 上传成功删除该计划存在本地的的图片
     * @param trainPlanUid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upLoadExamInfo.ajax", method = RequestMethod.POST)
    public Object upLoadExamInfo(@RequestParam("trainPlanUid") String trainPlanUid, HttpServletRequest request) {
        String xml = null;
        start();
        UpLoadInfo upLoadInfo = transactionService.queryUpLoadData(trainPlanUid);
        if (upLoadInfo == null) {
            error("上传的信息不完整，没有发现学员的考试记录！");
            return end();
        }
        try {
            xml = JAXBUtils.marshaller(UpLoadInfo.class, upLoadInfo);
        } catch (JAXBException jaxb) {
            jaxb.printStackTrace();
            error("数据->XML转换异常！");
            return end();
        }
        String retXml = null;
        try {
            retXml = mrmsService.examInfoUpLoad(xml);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("上传出现问题，上传失败！");
            }
            logger.error(e.getMessage());
            success(false);
            return end();
        }
        if ("true".equals(retXml)) {
            String path = System.getenv("catalina_home")+"\\webapps\\monitor\\res\\img\\" + trainPlanUid;
            FileUtil.delDir(new File(path));
            logger.info("本计划图片文件删除成功！");
            try {
                transactionService.deleteUpLoadedTrainPlanDataAndUpdateStatus(trainPlanUid);
                success(true);
                logger.info("上传成功!");
            } catch (Exception var) {
                error("本地数据表删除异常，上传失败！");
            }
        } else {
            logger.error("在线数据库插入异常，上传失败！");
            error("在线数据库插入异常，上传失败！");
            success(false);
        }
        return end();
    }

    /**
     * 下载mrms项目的war包
     * @return
     */
    @ResponseBody
    @RequestMapping("/download.ajax")
    public Object upload() {
        start();
        String fileNamePrefix = System.getenv("catalina_home")+"\\webapps\\";
        CxfFileWrapper file = null;
        String version =Tools.getValue("version");
        try {
            file = mrmsService.download(version);
        } catch (Exception e) {
             if (e.getCause() != null && e.getCause() instanceof ConnectException) {
                error("网络连接异常！");
            } else if (e instanceof SOAPFaultException && "The security token could not be authenticated or authorized".equals(e.getMessage())) {
                error("没有权限！");
            } else {
                error("下载出现问题，下载失败！");
            }
            return end();
        }
        OutputStream os = null;
        InputStream is = null;
        BufferedOutputStream bos = null;
        if(file == null){
            success(true);
            param("msg", "该项目已是最新版本！");
            return end();
        }
        try {
            is = file.getFile().getInputStream();
            File dest = new File(fileNamePrefix + "\\" + file.getFileName());
            os = new FileOutputStream(dest);
            bos = new BufferedOutputStream(os);
            byte[] buffer = new byte[1024 * 1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            success(true);
            param("msg","更新成功！");
        } catch (Exception var1) {
            success(false);
            error("数据传输失败！");
            var1.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception var2) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception var3) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception var4) {
                }
            }
        }
        return end();
    }
}
