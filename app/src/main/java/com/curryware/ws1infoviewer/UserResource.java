package com.curryware.ws1infoviewer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResource {

    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("meta")
    @Expose
    private UserMeta meta;
    @SerializedName("name")
    @Expose
    private UserName name;
    @SerializedName("emails")
    @Expose
    private List<UserEmail> emails = null;
    @SerializedName("groups")
    @Expose
    private List<UserGroup> groups = null;
    @SerializedName("roles")
    @Expose
    private List<UserRole> roles = null;
    @SerializedName("urn:scim:schemas:extension:workspace:1.0")
    @Expose
    private UserUrnScimSchemasExtensionWorkspace10 urnScimSchemasExtensionWorkspace10;
    @SerializedName("externalId")
    @Expose
    private String externalId;
    @SerializedName("phoneNumbers")
    @Expose
    private List<UserPhoneNumber> phoneNumbers = null;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserMeta getMeta() {
        return meta;
    }

    public void setMeta(UserMeta meta) {
        this.meta = meta;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

    public List<UserEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<UserEmail> emails) {
        this.emails = emails;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public UserUrnScimSchemasExtensionWorkspace10 getUrnScimSchemasExtensionWorkspace10() {
        return urnScimSchemasExtensionWorkspace10;
    }

    public void setUrnScimSchemasExtensionWorkspace10(UserUrnScimSchemasExtensionWorkspace10 urnScimSchemasExtensionWorkspace10) {
        this.urnScimSchemasExtensionWorkspace10 = urnScimSchemasExtensionWorkspace10;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public List<UserPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<UserPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

}
