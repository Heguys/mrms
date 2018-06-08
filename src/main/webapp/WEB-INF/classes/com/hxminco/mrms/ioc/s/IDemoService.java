package com.hxminco.mrms.ioc.s;


import com.hxminco.mrms.comm.entry.Demo;

import java.util.List;

public interface IDemoService {

    List<Demo> getDemoList();
    List<Demo> getDemoListXml();

    void insertDemo(Demo demo);

    void deleteDemo(int id);
}
