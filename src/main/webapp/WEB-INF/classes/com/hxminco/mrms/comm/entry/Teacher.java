package com.hxminco.mrms.comm.entry;

public class Teacher {
    private String uid;

    private String username;

    private String password;

    private String institutionname;

    private String institutionuid;

    private String status;

    public Teacher(String uid, String username, String password, String institutionname, String institutionuid, String status) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.institutionname = institutionname;
        this.institutionuid = institutionuid;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Teacher() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getInstitutionuid() {
        return institutionuid;
    }

    public void setInstitutionuid(String institutionuid) {
        this.institutionuid = institutionuid == null ? null : institutionuid.trim();
    }

    public String getInstitutionname() {
        return institutionname;
    }

    public void setInstitutionname(String institutionname) {
        this.institutionname = institutionname;
    }
}