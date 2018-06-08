package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.ioc.c.MrmsServiceController;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Employee on 2017/11/13.
 */
public class MyContextLoaderListener extends ContextLoaderListener {
    private final static Logger logger = LoggerFactory.getLogger(MrmsServiceController.class);
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        final QbExamInfoService qbExamInfoService = SpringContextUtil.getBean("qbExamInfoServiceImpl");
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
                 }
            });
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<QbExamInfo> qbExamInfos = QbExamInfoStorage.copyList();
                if(qbExamInfos != null && qbExamInfos.size() > 0) {
                    for (QbExamInfo one : qbExamInfos) {
                        if ("3".equals(one.getStatus()) || one.getPasstime() > one.getDuration()-1) {
                            QbExamInfoStorage.removeRecord(one);
                        }
                        one.setPasstime(one.getPasstime() + 1);
                        qbExamInfoService.updateExamInfoPassTime(one);
                    }
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
