package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

public class bannermodel {

    @SerializedName("imagePath")
    public String imagePath;


    public bannermodel(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
