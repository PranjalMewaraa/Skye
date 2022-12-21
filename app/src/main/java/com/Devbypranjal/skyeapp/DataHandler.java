package com.Devbypranjal.skyeapp;

import android.widget.ImageView;


public class DataHandler {
 String Caption, Username,uid,plike,ptime;
 String url;
 String profilepic;

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public DataHandler(String caption, String username, String uid, String plike, String url, String profilepic, String ptime) {
        this.Caption = caption;
        this.Username = username;
        this.uid = uid;
        this.plike = plike;
        this.url = url;
        this.profilepic = profilepic;
        this.ptime =ptime;
    }
public DataHandler(){

}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlike() {
        return plike;
    }

    public void setPlike(String plike) {
        this.plike = plike;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
