package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.RsExamPlan;
import com.hxminco.mrms.comm.entry.RsTrainPlan;

import java.util.List;
import java.util.Map;

public interface RsTrainPlanMapper {
    int deleteByPrimaryKey(String uid);

    int insert(RsTrainPlan record);

    int insertSelective(RsTrainPlan record);

    RsTrainPlan selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(RsTrainPlan record);

    int updateByPrimaryKey(RsTrainPlan record);

    int insertTainPlanList(Map<String, List<RsTrainPlan>> tainPlanMap);

    List<String> selectAllTrainPlanUid();

    void updateTrainPlanMap(Map<String, Object> map);
}