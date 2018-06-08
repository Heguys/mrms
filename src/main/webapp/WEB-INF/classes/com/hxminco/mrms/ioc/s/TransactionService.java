package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.UpLoadInfo;

/**
 * Created by Employee on 2017/9/20.
 */
public interface TransactionService {

    void updateExamInfoAndStudentInfo(QbExamInfo qbExamInfo, RsStudentInfo student);
    void deleteUpLoadedTrainPlanDataAndUpdateStatus(String trainPlanUid);
    UpLoadInfo queryUpLoadData(String trainPlanUid);
}
