package com.hxminco.mrms.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

public class DemoControllerTest extends com.hxminco.mrms.controller.AbstractContextControllerTests {

    @Test
    @Ignore
    public void addDemo() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/addDemo.ajax")
                            .contentType(MediaType.APPLICATION_JSON).param("id", "999").param("name", "拉拉")
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void demoAjax() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/demo.ajax")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    //@Ignore
    public void demoHtml() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/demo.html")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
