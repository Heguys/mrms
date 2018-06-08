package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.Teacher;
import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    int deleteByPrimaryKey(String uid);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    Teacher selectTeacher4Login(Teacher teacher);

    List<Teacher> selectTeacherByLoginName(String username);

    List<Teacher> queryTeacherPage(Map<String, Object> paramMap);

    int queryTeacherCount4Page(Map<String, Object> paramMap);
}