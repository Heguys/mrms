package com.hxminco.mrms.ioc.c;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Employee on 2017/7/13.
 */
public class BaseController {

    private ThreadLocal<Map<String, Object>> resultLocal = new ThreadLocal<Map<String, Object>>();
    protected void start() {
        resultLocal.set(new HashMap<String, Object>());
    }

    protected Object end() {
        Map<String, Object> resultMap = resultLocal.get();
        resultLocal.remove();
        return resultMap;
    }

    protected void success(boolean flg) {
        Map<String, Object> resultMap = resultLocal.get();
        resultMap.put("success", flg);
    }

    protected void error(String msg) {
        Map<String, Object> resultMap = resultLocal.get();
        resultMap.put("error", msg);
    }

    protected void param( String key, Object val ) {
        Map<String, Object> resultMap = resultLocal.get();
        resultMap.put(key, val);
    }
}
