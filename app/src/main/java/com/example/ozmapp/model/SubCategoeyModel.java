package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SubCategoeyModel {

    @SerializedName("subCategory")
    public String name;

    @SerializedName("apps")
    public ArrayList<applist> app_list = new ArrayList<applist>();

    public static class applist {

        @SerializedName("_id")
        public String _id;

        @SerializedName("active")
        public String active;

        @SerializedName("name")
        public String name;

        @SerializedName("category")
        public String category;

        @SerializedName("subCategory")
        public String subCategory;


        @SerializedName("link")
        public String link;

        @SerializedName("androidImageUrl")
        public String androidImageUrl;

    }

    public SubCategoeyModel(String name, ArrayList<applist> app_list) {
        this.name = name;
        this.app_list = app_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<applist> getApp_list() {
        return app_list;
    }

    public void setApp_list(ArrayList<applist> app_list) {
        this.app_list = app_list;
    }
}
