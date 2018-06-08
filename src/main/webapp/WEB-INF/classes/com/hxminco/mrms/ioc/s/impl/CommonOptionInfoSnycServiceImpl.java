package com.hxminco.mrms.ioc.s.impl;

import com.hxminco.mrms.comm.entry.RsCommonOption;
import com.hxminco.mrms.comm.model.CommonOptionInfo;
import com.hxminco.mrms.ioc.d.RsCommonOptionMapper;
import com.hxminco.mrms.ioc.s.CommonOptionInfoSnycService;
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
public class CommonOptionInfoSnycServiceImpl implements CommonOptionInfoSnycService {
    @Autowired
    private RsCommonOptionMapper rsCommonOptionMapper;

    @Override
    public Timestamp getLatestCreateTime() {
        Timestamp createTime = rsCommonOptionMapper.selectLatestCreateTime();
        return createTime;
    }

    @Override
    public int addCommonOptionInfo(CommonOptionInfo commonOptionInfo) {
        rsCommonOptionMapper.truncateTable();
        List<RsCommonOption> lstRsCommonOptions = commonOptionInfo.getLstRsCommonOptions();
        Map<String,List<RsCommonOption>> map = new HashMap<>();
        map.put("commonOptions",lstRsCommonOptions);
        int rows = rsCommonOptionMapper.insertCommonOptionList(map);
        return rows;
    }

    @Override
    public List<RsCommonOption> getAllCommonOption() {
        List<RsCommonOption> commonOptions = rsCommonOptionMapper.selectAllCommonOption();
        return commonOptions;
    }
}
