package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.RsCommonOption;
import com.hxminco.mrms.comm.entry.RsInstitution;
import com.hxminco.mrms.comm.model.InstitutionInfo;
import com.hxminco.mrms.ioc.d.RsInstitutionMapper;
import com.hxminco.mrms.ioc.s.InstitutionInfoSnycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/21.
 */
@Service
public class InstitutionInfoSnycServiceImpl implements InstitutionInfoSnycService {
    @Autowired
    private RsInstitutionMapper rsInstitutionMapper;
    @Override
    public int addInstitutionInfo(InstitutionInfo institutionInfo) {
        rsInstitutionMapper.truncateTable();
        List<RsInstitution> lstRsInstitutions = institutionInfo.getLstRsInstitutions();
        Map<String,List<RsInstitution>> map = new HashMap<>();
        map.put("institutions",lstRsInstitutions);
        int rows = rsInstitutionMapper.insertInstitutionList(map);
        return rows;
    }

    @Override
    public String getParentUid(String institutionUid) {
        String parentUid = rsInstitutionMapper.selectParentUid(institutionUid);
        return parentUid;
    }

    @Override
    public Timestamp getLatestCreateTime() {
        Timestamp createTime = rsInstitutionMapper.selectLatestCreateTime();
        return createTime;
    }

    @Override
    public List<RsInstitution> getAllInstitution() {
        List<RsInstitution> institutions = rsInstitutionMapper.selectAllInstitution();
        return institutions;
    }

    @Override
    public List<RsInstitution> getListInstitution(String institutionUid) {
        List<RsInstitution> lstRsInstitution = rsInstitutionMapper.selectListInstitution(institutionUid);
        return lstRsInstitution;
    }
}
