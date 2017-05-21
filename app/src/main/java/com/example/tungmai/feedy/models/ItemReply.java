package com.example.tungmai.feedy.models;

/**
 * Created by TungMai on 3/24/2017.
 */

public class ItemReply {
    private String idComment;
    private String nameUser;
    private String imageUser;
    private String comment;
    private String time;
    private int countLike;
    private boolean isLike;



    public ItemReply(String idComment, String nameUser, String imageUser, String comment, String time, int countLike, boolean isLike) {
        this.idComment = idComment;
        this.nameUser = nameUser;
        this.imageUser = imageUser;
        this.comment = comment;
        this.time = time;
        this.countLike = countLike;
        this.isLike = isLike;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }


}
