package com.hxminco.mrms.service;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.ioc.s.QbExamInfoService;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Employee on 2017/10/30.
 */
public class CountDown implements Runnable {

        private int limitMin;
        private int count = 0;
        public ScheduledExecutorService service;
        public CountDown(int limitMin ,ScheduledExecutorService service) {
            this.limitMin = limitMin;
            this.service = service;
        }

        @Override
        public void run() {
            if (count >= limitMin) {
                service.shutdownNow();
            }
            System.out.println(++count);
        }
    public static void main(String[] args) throws InterruptedException{
       /* ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new CountDown(120,service),0,1, TimeUnit.SECONDS);*/
        String s = "123";
        String[] split = s.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }
}
