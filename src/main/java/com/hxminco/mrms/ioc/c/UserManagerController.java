package com.hxminco.mrms.ioc.c;
import com.hxminco.mrms.comm.entry.Teacher;
import com.hxminco.mrms.comm.utils.PasswordUtil;
import com.hxminco.mrms.comm.utils.StringUtil;
import com.hxminco.mrms.ioc.s.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Employee on 2017/8/14.
 */
@RequestMapping("/admin")
@Controller
public class UserManagerController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(UserManagerController.class);
    @Autowired
    private TeacherService teacherService;

    /**
     * 主键删除监考账号
     * @param teacher
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTeacher.ajax")
    public Object deleteTeacher( Teacher teacher ) {
       start();
        try {
            teacher.setStatus("1");
            int cnt = teacherService.deleteTeacherById(teacher);
            success(cnt == 1);
        } catch ( Exception e ) {
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    /**
     * 修改某个监考账号
     * @param teacher
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateTeacher.ajax")
    public Object updateTeacher( Teacher teacher ) {
       start();
        try {
            teacher.setPassword(PasswordUtil.encrypt(teacher.getUsername(), teacher.getPassword(), null));
            int cnt = teacherService.updateTeacher(teacher);
            success(cnt == 1);
        } catch ( Exception e ) {
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    /**
     * 新建监考账号
     * @param teacher
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertTeacher.ajax",method = RequestMethod.POST)
    public Object insertTeacher( Teacher teacher ,HttpServletRequest request) {
       start();
        try {
            Teacher dbTeacher = teacherService.getTeacherByLoginName(teacher.getUsername());
            if(dbTeacher != null){
                error("用户名已存在！");
                return end();
            }
            String uid = StringUtil.getUUID();
            teacher.setUid(uid);
            teacher.setStatus("0");
            teacher.setPassword(PasswordUtil.encrypt(teacher.getUsername(), teacher.getPassword(), null));
            teacherService.insertTeacher(teacher);
            success(true);
        } catch ( Exception e ) {

            success(false);
        }
        return end();
    }

    /**
     * 分页查询监考账号。
     * @param querytext
     * @param pageno
     * @param pagesize
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/teacherPageQuery.ajax",method= RequestMethod.POST)
    public Object pageQuery( String querytext, Integer pageno, Integer pagesize ) {
       start();
        try {
            // 分页查询
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("start", (pageno-1)*pagesize);
            paramMap.put("size", pagesize);
            paramMap.put("querytext", querytext);

            List<Teacher> rows = teacherService.queryTeacherPage(paramMap);
            int count = teacherService.queryTeacherCount(paramMap);
            param("rows",rows);
            param("total",count);
            success(true);
        } catch ( Exception e ) {
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    /**
     * 回显监考账号的信息，用于修改账号信息时。
     * @param uid
     * @return
     */
    @ResponseBody
    @RequestMapping("/edit.ajax")
    public Object edit(@RequestParam("uid")String uid) {
        start();
        try {
            Teacher teacher = teacherService.queryTeacherById(uid);
            param("teacher", teacher);
            success(true);
        } catch (Exception e) {
            e.printStackTrace();
            success(false);
        }
        return end();
    }
}
