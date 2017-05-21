package com.example.tungmai.feedy.models;

/**
 * Created by TungMai on 4/7/2017.
 */

public class ItemFeedyList {
    private String idFeedy;
    private String imageFeedy;
    private String nameFeedy;
    private String idUser;
    private String imageUser;
    private String nameUser;

//    public ItemFeedyList(String idFeedy, String imageFeedy, String nameFeedy, String imageUser, String nameUser) {
//        this.idFeedy = idFeedy;
//        this.imageFeedy = imageFeedy;
//        this.nameFeedy = nameFeedy;
////        this.idUser = idUser;
//        this.imageUser = imageUser;
//        this.nameUser = nameUser;
//    }

    public ItemFeedyList(String idFeedy, String imageFeedy, String nameFeedy, String idUser, String imageUser, String nameUser) {
        this.idFeedy = idFeedy;
        this.imageFeedy = imageFeedy;
        this.nameFeedy = nameFeedy;
        this.idUser = idUser;
        this.imageUser = imageUser;
        this.nameUser = nameUser;
    }

    public String getIdFeedy() {
        return idFeedy;
    }

    public void setIdFeedy(String idFeedy) {
        this.idFeedy = idFeedy;
    }

    public String getImageFeedy() {
        return imageFeedy;
    }

    public void setImageFeedy(String imageFeedy) {
        this.imageFeedy = imageFeedy;
    }

    public String getNameFeedy() {
        return nameFeedy;
    }

    public void setNameFeedy(String nameFeedy) {
        this.nameFeedy = nameFeedy;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
