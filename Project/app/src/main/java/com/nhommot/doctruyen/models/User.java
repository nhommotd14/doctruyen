package com.nhommot.doctruyen.models;

/**
 * Edited by Toan on 4/30/2018.
 */

public class User {
    public String firstName;
    public String lastName;
    public Integer age;
    public String  sex;
    public String URLImage;

    public User(String firstName, String lastName, Integer age, String sex, String URLImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.URLImage = URLImage;
    }

    public User() {
    }
}