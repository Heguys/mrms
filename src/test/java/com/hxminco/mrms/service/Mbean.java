package com.hxminco.mrms.service;


import org.apache.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Employee on 2017/10/25.
 */
public class Mbean {
    private static final Logger log = Logger.getLogger(Mbean.class);
    public static boolean callWebModuleMBeanMethod(String appName,String methodName) throws Exception{
        MBeanServer mBeanServer = null;

        if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
            mBeanServer = (MBeanServer) MBeanServerFactory.findMBeanServer(
                    null).get(0);
        } else {
            throw new Exception("cann't find catalina MBeanServer");
        }

        Set names = null;
        try {
            names = mBeanServer.queryNames(new ObjectName(
                    "*:j2eeType=WebModule,name=//localhost/"+appName+",*"), null);
        } catch (Exception e) {
            throw new Exception("cann't find "+appName+ " web moudule mbean! can't undeploy web app./n"+e.getMessage());
        }
        if(names==null || names.size()==0) {
            log.info("can't find "+appName+ " web moudule mbean!");
            return false;
        }

        ObjectName oname =null;
        Iterator it = names.iterator();
        if (it.hasNext()) {
            oname=(ObjectName) it.next();
        }

        if(oname==null)
            return false;
        try {
            mBeanServer.invoke(oname,methodName,null,null);
            return true;
        } catch (Exception e) {
            throw new Exception("can't "+methodName+" "+appName+ " web application!/n"+e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        callWebModuleMBeanMethod("app1","stop"); //停止web应用app1
        callWebModuleMBeanMethod("app1","start"); //启动web应用app1
    }
}
