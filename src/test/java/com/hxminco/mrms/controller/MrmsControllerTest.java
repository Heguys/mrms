package com.hxminco.mrms.controller;

import com.hxminco.mrms.comm.entry.Teacher;
import com.hxminco.mrms.ioc.s.impl.TeacherServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* Created by Employee on 2017/10/16.
        */
public class MrmsControllerTest extends AbstractContextControllerTests {
    @Autowired
    TeacherServiceImpl teacherServiceImpl;
    @Test
    @Ignore
    public void addDemo() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("teacher/commonOptionDownLoad.ajax")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test(){
        Teacher teacher = teacherServiceImpl.queryTeacherById("5371271f295748f5b14eecb13621eb92");
        System.out.println(teacher.getUsername());
    }
}
