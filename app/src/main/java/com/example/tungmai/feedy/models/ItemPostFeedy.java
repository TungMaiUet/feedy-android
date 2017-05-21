package com.example.tungmai.feedy.models;

/**
 * Created by TungMai on 4/10/2017.
 */

public class ItemPostFeedy {
    private String contentMaking;
    private String imageMaking;

    public ItemPostFeedy(String contentMaking, String imageMaking) {
        this.contentMaking = contentMaking;
        this.imageMaking = imageMaking;
    }

    public String getContentMaking() {
        return contentMaking;
    }

    public void setContentMaking(String contentMaking) {
        this.contentMaking = contentMaking;
    }

    public String getImageMaking() {
        return imageMaking;
    }

    public void setImageMaking(String imageMaking) {
        this.imageMaking = imageMaking;
    }
}
