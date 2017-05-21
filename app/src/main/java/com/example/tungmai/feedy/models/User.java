package com.example.tungmai.feedy.models;

import java.io.Serializable;

/**
 * Created by TungMai on 3/12/2017.
 */

public class User implements Serializable{
    private String idToken;
    private String name;
    private String email;
    private boolean gender;
    private String birth;
    private String imageUser;

    public User() {
    }

    public User(String idToken, String name, String email, boolean gender, String birth, String imageUser) {
        this.idToken = idToken;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.imageUser = imageUser;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }
}
