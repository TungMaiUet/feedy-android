package com.example.tungmai.feedy.models;

import java.util.ArrayList;

/**
 * Created by TungMai on 3/15/2017.
 */

public class ItemBlog {
    private String idBlog;
    private String idUser;
    private String imageUser;
    private String name;
    private String time;
    private boolean isSave;
    private String content;

    private String[] images;

    private int likeSize;
    private int commentSize;
    private boolean isLike;
//    private String idComment;

    public ItemBlog(String idBlog,String idUser,String imageUser, String name, String time, boolean isSave, String content, String[] images, int likeSize, int commentSize,boolean isLike) {
        this.idBlog= idBlog;
        this.imageUser = imageUser;
        this.name = name;
        this.time = time;
        this.isSave = isSave;
        this.content = content;
        this.images = images;
        this.likeSize = likeSize;
        this.commentSize = commentSize;
        this.isLike = isLike;
        this.idUser = idUser;
//        this.idComment = idComment;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdBlog() {
        return idBlog;
    }

    public void setIdBlog(String idBlog) {
        this.idBlog = idBlog;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getLikeSize() {
        return likeSize;
    }

    public void setLikeSize(int likeSize) {
        this.likeSize = likeSize;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
