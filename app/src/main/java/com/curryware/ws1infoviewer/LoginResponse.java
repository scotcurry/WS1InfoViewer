package com.curryware.ws1infoviewer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("sessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("admin")
    @Expose
    private Boolean admin;
    @SerializedName("serverUrl")
    @Expose
    private Object serverUrl;
    @SerializedName("signingCert")
    @Expose
    private Object signingCert;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Object getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(Object serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Object getSigningCert() {
        return signingCert;
    }

    public void setSigningCert(Object signingCert) {
        this.signingCert = signingCert;
    }

}