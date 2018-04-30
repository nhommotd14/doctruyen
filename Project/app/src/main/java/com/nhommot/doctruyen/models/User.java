package com.nhommot.doctruyen.models;

/**
 * Edited by Toan on 4/30/2018.
 */

public class User {
    public String userName;
    public Integer age;
    public String  sex;
    public String URLImage;

    public User(String userName, Integer age, String sex, String URLImage) {
        this.userName = userName;
        this.age = age;
        this.sex = sex;
        this.URLImage = URLImage;
    }

    public User() {
    }
}