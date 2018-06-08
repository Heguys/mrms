package com.hxminco.mrms.comm.entry;

public class RsCommonOption {
    private String uid;

    private String typeId;

    private String id;

    private String name;

    private Integer serialNum;

    private String description;

    public RsCommonOption(String uid, String typeId, String id, String name, Integer serialNum, String description) {
        this.uid = uid;
        this.typeId = typeId;
        this.id = id;
        this.name = name;
        this.serialNum = serialNum;
        this.description = description;
    }

    public RsCommonOption() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}