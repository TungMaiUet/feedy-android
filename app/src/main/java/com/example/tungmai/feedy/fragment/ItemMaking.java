package com.example.tungmai.feedy.fragment;

/**
 * Created by TungMai on 4/10/2017.
 */

public class ItemMaking {
    private String content;
    private String urlImage;

    public ItemMaking(String content, String urlImage) {
        this.content = content;
        this.urlImage = urlImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
