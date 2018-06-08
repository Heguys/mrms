package com.hxminco.mrms.ioc.s;


import com.hxminco.mrms.comm.entry.MockUsers;

public interface IMockUsersService {

    int insert(MockUsers record);
    int insertSelective(MockUsers record);
    MockUsers findUserByLoginId(String loginId);
}
