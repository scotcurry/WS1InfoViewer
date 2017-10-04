package com.curryware.ws1infoviewer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUrnScimSchemasExtensionWorkspace10 {

    @SerializedName("internalUserType")
    @Expose
    private String internalUserType;
    @SerializedName("userStatus")
    @Expose
    private String userStatus;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("userStoreUuid")
    @Expose
    private String userStoreUuid;
    @SerializedName("distinguishedName")
    @Expose
    private String distinguishedName;
    @SerializedName("externalUserDisabled")
    @Expose
    private Boolean externalUserDisabled;
    @SerializedName("userPrincipalName")
    @Expose
    private String userPrincipalName;

    public String getInternalUserType() {
        return internalUserType;
    }

    public void setInternalUserType(String internalUserType) {
        this.internalUserType = internalUserType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserStoreUuid() {
        return userStoreUuid;
    }

    public void setUserStoreUuid(String userStoreUuid) {
        this.userStoreUuid = userStoreUuid;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public Boolean getExternalUserDisabled() {
        return externalUserDisabled;
    }

    public void setExternalUserDisabled(Boolean externalUserDisabled) {
        this.externalUserDisabled = externalUserDisabled;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }
}

