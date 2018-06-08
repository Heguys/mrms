package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.QbPaper;

import java.util.List;
import java.util.Map;

public interface QbPaperMapper {
    int insertPaperList(Map<String, List<QbPaper>> paperMap);

    QbPaper selectPaperByTrainPlanUid(String trainPlanUid);

    int deletePaperByTrainPlanUid(String trainPlanUid);
}