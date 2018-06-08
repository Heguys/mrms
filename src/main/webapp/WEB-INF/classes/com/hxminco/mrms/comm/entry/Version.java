package com.hxminco.mrms.comm.entry;

import com.hxminco.mrms.comm.model.TimestampAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
@XmlAccessorType(value= XmlAccessType.FIELD)
@XmlRootElement
public class Version {
    @XmlElement
    private String uid;
    @XmlElement
    private String name;
    @XmlJavaTypeAdapter(TimestampAdapter.class)
    @XmlElement
    private Timestamp updateTime;
    @XmlElement
    private String versionNumber;
    @XmlElement
    private String updateAbout;

    public Version(String uid, String name, Timestamp updateTime, String versionNumber, String updateAbout) {
        this.uid = uid;
        this.name = name;
        this.updateTime = updateTime;
        this.versionNumber = versionNumber;
        this.updateAbout = updateAbout;
    }

    public Version() {
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    public String getUpdateAbout() {
        return updateAbout;
    }

    public void setUpdateAbout(String updateAbout) {
        this.updateAbout = updateAbout;
    }
}