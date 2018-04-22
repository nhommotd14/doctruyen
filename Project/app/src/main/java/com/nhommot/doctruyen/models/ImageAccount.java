package com.nhommot.doctruyen.models;

public class ImageAccount{
    private String userName,imageURL;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ImageAccount() {

    }

    public ImageAccount(String userName, String imageURL) {
        this.userName = userName;
        this.imageURL = imageURL;
    }
}
