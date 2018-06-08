package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.ExamRecord;

public interface ExamRecordMapper {
    int insert(ExamRecord record);

    int insertSelective(ExamRecord record);
}