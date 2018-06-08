package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.utils.StringUtil;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import com.hxminco.mrms.ioc.s.impl.QbExamInfoServiceImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Employee on 2017/11/9.
 */
public class QbExamInfoStorage {
    private static final List<QbExamInfo> list = new ArrayList<>();

    public static synchronized void addRecord(QbExamInfo qbExamInfo){
        list.add(qbExamInfo);
    }
    public static synchronized void removeRecord(QbExamInfo qbExamInfo){
        if(list.size()>0){
            Iterator<QbExamInfo> it = list.iterator();
            QbExamInfo next = null;
            while(it.hasNext()){
                next = it.next();
                if(Objects.equals(next.getId(),qbExamInfo.getId())){
                    it.remove();
                }
            }
        }
    }

    public static QbExamInfo getRecord(QbExamInfo qbExamInfo){
        QbExamInfo innerQbExamInfo = null;
        if(list.size()>0){
            Iterator<QbExamInfo> it = list.iterator();
            QbExamInfo next = null;
            while(it.hasNext()){
                next = it.next();
                if(Objects.equals(next.getId(),qbExamInfo.getId())){
                    innerQbExamInfo = next;
                }
            }
        }
        return innerQbExamInfo;
    }

    public static synchronized void modify(QbExamInfo qbExamInfo){
        if(list.size()>0){
            Iterator<QbExamInfo> it = list.iterator();
            QbExamInfo next = null;
            while(it.hasNext()){
                next = it.next();
                if(Objects.equals(next.getId(),qbExamInfo.getId())){
                    next.setStatus("3");
                }
            }
        }
    }


    public static List<QbExamInfo> copyList(){
        List<QbExamInfo> destList = new ArrayList<>();
        destList.addAll(list);
        return destList;
    }


}
//
//class MyThreadPool {
//    public static void main(String[] args) {
//    final QbExamInfoStorage storage = new QbExamInfoStorage();
//    final QbExamInfoService qbExamInfoService = new QbExamInfoServiceImpl() {
//    };
//        for (int i = 0; i < 100; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    QbExamInfo qbExamInfo = new QbExamInfo();
//                    qbExamInfo.setId(StringUtil.getUUID());
//                    qbExamInfo.setPasstime(0);
//                    storage.addRecord(qbExamInfo);
//                    System.out.println(Thread.currentThread().getName()+"放入考试计划");
//                }
//            },String.valueOf(i)).start();
//        }
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
//        service.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                List<QbExamInfo> qbExamInfos = storage.copyList();
//                for (QbExamInfo one:qbExamInfos){
//                    if(one.getPasstime()>119){
//                        storage.removeRecord(one);
//                    }
//                    one.setPasstime(one.getPasstime() + 1);
//                }
//                System.out.println(qbExamInfos.toString());
//            }
//        }, 1, 1, TimeUnit.SECONDS);
//    }
//
//}


