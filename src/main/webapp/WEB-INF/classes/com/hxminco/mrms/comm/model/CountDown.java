package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountDown {
    private volatile int limitMin; //记录倒计时时间
    private volatile int count = 0;   //记录倒计时当下时间
    @Autowired
    private  QbExamInfoService qbExamInfoService;
    public CountDown(int limitMin){
        this.limitMin = limitMin;
    }
    public void countDown(final QbExamInfo qbExamInfo) throws InterruptedException {
        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(new Runnable(){
            @Override
            public void run() {
                if (count >= limitMin) {
                    exec.shutdownNow();
                }
                qbExamInfo.setPasstime(++count);
                qbExamInfoService.updateExamInfo(qbExamInfo);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}