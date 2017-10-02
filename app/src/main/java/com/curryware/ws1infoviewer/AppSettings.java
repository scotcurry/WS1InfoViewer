package com.curryware.ws1infoviewer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by scotcurry on 10/1/17.
 */

public class AppSettings {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("ws1tenant")
    @Expose
    private String issueToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIssueToken() {
        return issueToken;
    }

    public void setIssueToken(String issueToken) {
        this.issueToken = issueToken;
    }
}
