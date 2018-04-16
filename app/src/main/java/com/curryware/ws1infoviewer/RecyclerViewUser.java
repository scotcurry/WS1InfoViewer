package com.curryware.ws1infoviewer;


public class RecyclerViewUser {

    private String userName;
    private String emailAddress;
    private int userImage;
    private String userLocation;
    private boolean userActive;
    private String userFirstName;
    private String userLastName;
    private String userID;
    private String userDomain;
    private String userUPN;

    public RecyclerViewUser(String userName, String emailAddress, int imageId, String userLocation,
                           String userID, String userFirstName, String userLastName, String userDomain,
                           boolean userActive, String userUPN) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userImage = imageId;
        this.userLocation = userLocation;
        this.userActive = userActive;
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userDomain = userDomain;
        this.userUPN = userUPN;
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

    public String getUserLocation() { return userLocation; }

    public void setUserLocation(String userLocation) { this.userLocation = userLocation; }

    public boolean getUserActive() { return userActive; }

    public void setUserActive(boolean userActive) { this.userActive = userActive; }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getUserFirstName() { return userFirstName; }

    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserLastName() { return userLastName; }

    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }

    public String gerUserDomain() { return userDomain; }

    public void setUserDomain(String userDomain) { this.userDomain = userDomain; }

    public String getUserUPN() { return userUPN; }

    public void setUserUPN(String userUPN) {
        if (userUPN == null) {
            this.userUPN = "Empty";
        } else {
            this.userUPN = userUPN;
        }
    }
}
