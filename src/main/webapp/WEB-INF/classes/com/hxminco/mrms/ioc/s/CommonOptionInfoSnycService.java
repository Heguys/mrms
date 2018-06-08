package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.entry.RsCommonOption;
import com.hxminco.mrms.comm.model.CommonOptionInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Employee on 2017/7/21.
 */
public interface CommonOptionInfoSnycService {
    int addCommonOptionInfo(CommonOptionInfo commonOptionInfo);

    List<RsCommonOption> getAllCommonOption();

    Timestamp getLatestCreateTime();
}
