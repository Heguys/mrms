package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.RsCommonOption;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface RsCommonOptionMapper {
    int deleteByPrimaryKey(String uid);

    int insert(RsCommonOption record);

    int insertSelective(RsCommonOption record);

    RsCommonOption selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(RsCommonOption record);

    int updateByPrimaryKey(RsCommonOption record);

    int insertCommonOptionList(Map<String, List<RsCommonOption>> lstRsCommonOptions);

    void truncateTable();

    List<RsCommonOption> selectAllCommonOption();

    Timestamp selectLatestCreateTime();
}