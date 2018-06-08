package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.Teacher;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/31.
 */
public interface TeacherService {
    Teacher getTeacherByLoginName(String username);

    int deleteTeacherById(Teacher teacher);

    int updateTeacher(Teacher teacher);

    void insertTeacher(Teacher teacher);

    List<Teacher> queryTeacherPage(Map<String, Object> paramMap);

    Teacher queryTeacherById(String id);

    int queryTeacherCount(Map<String, Object> paramMap);
}
