package com.example.tungmai.feedy.models;

/**
 * Created by TungMai on 4/11/2017.
 */

public class ItemPrepare {
    private String id;
    private String content;
    private String quantity;
    private String unit;
    private boolean isChecked;

    public ItemPrepare() {
    }

    public ItemPrepare(String id, String content, String quantity, String unit, boolean isChecked) {
        this.id = id;
        this.content = content;
        this.quantity = quantity;
        this.unit = unit;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
