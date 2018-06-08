package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.RsInstitution;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface RsInstitutionMapper {
    int deleteByPrimaryKey(String uid);

    int insert(RsInstitution record);

    int insertSelective(RsInstitution record);

    RsInstitution selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(RsInstitution record);

    int updateByPrimaryKey(RsInstitution record);

    int insertInstitutionList(Map<String, List<RsInstitution>> map);

    String selectParentUid(String institutionUid);

    void truncateTable();

    List<RsInstitution> selectListInstitution(String institutionUid);

    List<RsInstitution> selectAllInstitution();

    Timestamp selectLatestCreateTime();
}