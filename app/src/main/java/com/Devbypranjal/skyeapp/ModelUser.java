package com.Devbypranjal.skyeapp;
public class ModelUser {
    String name;

    public ModelUser() {
    }

    String onlineStatus;
    String typingTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ModelUser(String name, String onlineStatus, String typingTo, String email, String mobile, String image, String uid) {
        this.name = name;
        this.onlineStatus = onlineStatus;
        this.typingTo = typingTo;
        this.email = email;
        Mobile = mobile;
        this.image = image;
        this.uid = uid;
    }

    String email;
    String Mobile;

    public ModelUser(String mobile) {
        Mobile = mobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    String image;

    String uid;
}
