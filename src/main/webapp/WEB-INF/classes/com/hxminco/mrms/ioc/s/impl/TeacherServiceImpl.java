package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.Teacher;
import com.hxminco.mrms.ioc.d.TeacherMapper;
import com.hxminco.mrms.ioc.s.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/31.
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacherByLoginName(String username) {
        List<Teacher> lstTeacher = teacherMapper.selectTeacherByLoginName(username);
        if(lstTeacher == null || lstTeacher.size() < 1){
            return null;
        }
        return lstTeacher.get(0);
    }

    @Override
    public int deleteTeacherById(Teacher teacher) {
        int row = teacherMapper.updateByPrimaryKeySelective(teacher);
        return row;
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        int row = teacherMapper.updateByPrimaryKeySelective(teacher);
        return row;
    }

    @Override
    public void insertTeacher(Teacher teacher) {
        teacherMapper.insertSelective(teacher);
    }

    @Override
    public List<Teacher> queryTeacherPage(Map<String, Object> paramMap) {
        List<Teacher> teachers = teacherMapper.queryTeacherPage(paramMap);
        return teachers;
    }

    @Override
    public Teacher queryTeacherById(String id) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        return teacher;
    }

    @Override
    public int queryTeacherCount(Map<String, Object> paramMap) {

        int count = teacherMapper.queryTeacherCount4Page(paramMap);
        return count;
    }

}
