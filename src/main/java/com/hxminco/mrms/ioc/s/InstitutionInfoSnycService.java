package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.RsInstitution;
import com.hxminco.mrms.comm.model.InstitutionInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Employee on 2017/7/21.
 */
public interface InstitutionInfoSnycService {
    int addInstitutionInfo(InstitutionInfo institutionInfo);

    String getParentUid(String institutionUid);

    List<RsInstitution> getListInstitution(String institutionUid);

    List<RsInstitution> getAllInstitution();

    Timestamp getLatestCreateTime();
}
