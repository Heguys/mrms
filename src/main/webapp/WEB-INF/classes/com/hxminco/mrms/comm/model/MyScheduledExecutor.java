package com.hxminco.mrms.comm.model;
import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import java.util.concurrent.ScheduledExecutorService;

 public class MyScheduledExecutor implements Runnable {
    private String name;
    private int limitMin;
    private int count = 0;
    public QbExamInfo qbExamInfo;
    public ScheduledExecutorService service;
    private  QbExamInfoService qbExamInfoService;
    public MyScheduledExecutor(String name,int limitMin ,QbExamInfo qbExamInfo,ScheduledExecutorService service,QbExamInfoService qbExamInfoService) {
        this.name = name;
        this.limitMin = limitMin;
        this.qbExamInfo = new QbExamInfo();
        this.service = service;
        this.qbExamInfoService = qbExamInfoService;
    }

    @Override
    public void run() {
        if (count >= limitMin) {
            service.shutdownNow();
        }
        qbExamInfo.setPasstime(++count);
        qbExamInfoService.updateExamInfo(qbExamInfo);
    }
}