package com.example.android.goodstore_adminapp.models;

/**
 * Created by Valentin on 31.05.2017.
 */

public class Discount {
    String category;
    String name;

    float value;

    public Discount(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Discount(String category, String name, float value,String id) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.value = value;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
}
