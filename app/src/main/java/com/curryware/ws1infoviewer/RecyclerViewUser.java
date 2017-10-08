package com.curryware.ws1infoviewer;


public class RecyclerViewUser {

    private String userName;
    private String emailAddress;
    private int userImage;

    public RecyclerViewUser(String userName, String emailAddress, int imageId) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userImage = imageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getImageId() { return userImage; }

    public void setImageId(int imageId) {
        this.userImage = imageId;
    }
}
