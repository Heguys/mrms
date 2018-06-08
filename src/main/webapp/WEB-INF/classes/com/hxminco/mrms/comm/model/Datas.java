package com.hxminco.mrms.comm.model;

/**
 * Created by Employee on 2017/8/14.
 */
import com.hxminco.mrms.comm.entry.Teacher;

import java.util.List;

public class Datas {//用于多个删除操作。

    private List<Teacher> teachers;
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}
