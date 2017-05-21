package com.example.tungmai.feedy.models;

import java.util.ArrayList;

/**
 * Created by TungMai on 3/24/2017.
 */

public class ItemComment {
    private String idComment;
    private String idUser;
    private String nameUser;
    private String imageUser;
    private String comment;
    private String time;
    private int countLike;
    private boolean isLike;

    private int countReply;
    private ArrayList<ItemReply> arrItemReplies;

    private boolean isAddReply;
    private boolean isDoneAddReply;

    public ItemComment(String idComment,String idUser, String nameUser, String imageUser, String comment, String time, int countLike, boolean isLike, int countReply, ArrayList<ItemReply> arrItemReplies) {
        this.idComment = idComment;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.imageUser = imageUser;
        this.comment = comment;
        this.time = time;
        this.countLike = countLike;
        this.isLike = isLike;
        this.countReply = countReply;
        this.arrItemReplies = arrItemReplies;
        isAddReply=false;
        isDoneAddReply=false;
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

    public int getCountReply() {
        return countReply;
    }

    public void setCountReply(int countReply) {
        this.countReply = countReply;
    }

    public ArrayList<ItemReply> getArrItemReplies() {
        return arrItemReplies;
    }

    public void setArrItemReplies(ArrayList<ItemReply> arrItemReplies) {
        this.arrItemReplies = arrItemReplies;
    }

    public boolean isAddReply() {
        return isAddReply;
    }

    public void setAddReply(boolean addReply) {
        isAddReply = addReply;
    }

    public boolean isDoneAddReply() {
        return isDoneAddReply;
    }

    public void setDoneAddReply(boolean doneAddReply) {
        isDoneAddReply = doneAddReply;
    }
}
