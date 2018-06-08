package com.hxminco.mrms.comm.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String toJson(Object o){
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }
}
