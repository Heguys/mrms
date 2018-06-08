package com.hxminco.mrms.comm.entry;

public class MockUsers {
    private String id;

    private String loginId;

    private String name;

    private String password;

    private String salt;

    private String superUser;

    private Long registerTime;

    private Long lastLoginTime;

    private String institutionUid;

    private String institutionAdministrator;

    private String locked;

    private String loginSecretUid;

    private String email;

    private String mobile;

    private String findPasswordCode;

    private Long findPasswordCodeExpireTime;

    private Long lastUpdateTime;

    private String emailRetrieveUrlHash;

    private Long emailRetrieveUrlHashExpireTime;

    public MockUsers(String id, String loginId, String name, String password, String salt, String superUser, Long registerTime, Long lastLoginTime, String institutionUid, String institutionAdministrator, String locked, String loginSecretUid, String email, String mobile, String findPasswordCode, Long findPasswordCodeExpireTime, Long lastUpdateTime, String emailRetrieveUrlHash, Long emailRetrieveUrlHashExpireTime) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.superUser = superUser;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.institutionUid = institutionUid;
        this.institutionAdministrator = institutionAdministrator;
        this.locked = locked;
        this.loginSecretUid = loginSecretUid;
        this.email = email;
        this.mobile = mobile;
        this.findPasswordCode = findPasswordCode;
        this.findPasswordCodeExpireTime = findPasswordCodeExpireTime;
        this.lastUpdateTime = lastUpdateTime;
        this.emailRetrieveUrlHash = emailRetrieveUrlHash;
        this.emailRetrieveUrlHashExpireTime = emailRetrieveUrlHashExpireTime;
    }

    public MockUsers() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getSuperUser() {
        return superUser;
    }

    public void setSuperUser(String superUser) {
        this.superUser = superUser == null ? null : superUser.trim();
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getInstitutionUid() {
        return institutionUid;
    }

    public void setInstitutionUid(String institutionUid) {
        this.institutionUid = institutionUid == null ? null : institutionUid.trim();
    }

    public String getInstitutionAdministrator() {
        return institutionAdministrator;
    }

    public void setInstitutionAdministrator(String institutionAdministrator) {
        this.institutionAdministrator = institutionAdministrator == null ? null : institutionAdministrator.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
    }

    public String getLoginSecretUid() {
        return loginSecretUid;
    }

    public void setLoginSecretUid(String loginSecretUid) {
        this.loginSecretUid = loginSecretUid == null ? null : loginSecretUid.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getFindPasswordCode() {
        return findPasswordCode;
    }

    public void setFindPasswordCode(String findPasswordCode) {
        this.findPasswordCode = findPasswordCode == null ? null : findPasswordCode.trim();
    }

    public Long getFindPasswordCodeExpireTime() {
        return findPasswordCodeExpireTime;
    }

    public void setFindPasswordCodeExpireTime(Long findPasswordCodeExpireTime) {
        this.findPasswordCodeExpireTime = findPasswordCodeExpireTime;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getEmailRetrieveUrlHash() {
        return emailRetrieveUrlHash;
    }

    public void setEmailRetrieveUrlHash(String emailRetrieveUrlHash) {
        this.emailRetrieveUrlHash = emailRetrieveUrlHash == null ? null : emailRetrieveUrlHash.trim();
    }

    public Long getEmailRetrieveUrlHashExpireTime() {
        return emailRetrieveUrlHashExpireTime;
    }

    public void setEmailRetrieveUrlHashExpireTime(Long emailRetrieveUrlHashExpireTime) {
        this.emailRetrieveUrlHashExpireTime = emailRetrieveUrlHashExpireTime;
    }
}