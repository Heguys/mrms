package com.hxminco.mrms.comm.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Demo implements Serializable {
    private int id;
    private String  name;

    List<Demo> list = new ArrayList<>();

    public List<Demo> getList() {
        return list;
    }

    public void setList(List<Demo> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
