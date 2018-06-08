package com.hxminco.mrms.ioc.d;

import com.hxminco.mrms.comm.entry.MockUsers;

import java.util.List;

public interface MockUsersMapper {
    int insert(MockUsers record);

    int insertSelective(MockUsers record);

    List<MockUsers> findUserByLoginId(String loginId);
}
