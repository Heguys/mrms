package com.hxminco.mrms.comm.entry;

public class RsInstitution {
    private String uid;

    private String name;

    private String typeId;

    private String parentUid;

    private String locked;

    public RsInstitution(String uid, String name, String typeId, String parentUid, String locked) {
        this.uid = uid;
        this.name = name;
        this.typeId = typeId;
        this.parentUid = parentUid;
        this.locked = locked;
    }

    public RsInstitution() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid == null ? null : parentUid.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
    }
}