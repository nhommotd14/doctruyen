package com.nhommot.doctruyen.models;

/**
 * Edited by Toan on 4/30/2018.
 */

public class User {
    private String userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String imgURL;
    private String createDate;
    private String updatedDate;
    private String birth;
    private String phoneNumber;
    private int age;
    private String sex;

    public User(String userName,String firstName, String lastName, int age, String imgURL, String sex) {
        this.username = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgURL = imgURL;
        this.age = age;
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public User(String userName, Integer age, String sex, String URLImage) {
        //this.userName = userName;
        this.age = age;
        this.sex = sex;
        //this.URLImage = URLImage;
    }

    public User() {
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {

        return imgURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}