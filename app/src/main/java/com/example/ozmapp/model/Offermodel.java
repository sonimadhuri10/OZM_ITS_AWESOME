package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Offermodel {

    @SerializedName("_id")
    public String _id;

    @SerializedName("offerHeading")
    public String offerHeading;

    @SerializedName("name")
    public String name;

    @SerializedName("promocode")
    public String category;

    @SerializedName("offerPage")
    public String offerPage;

    @SerializedName("allOfferPage")
    public String allOfferPage;

    @SerializedName("iconPath")
    public String iconPath;

    @SerializedName("tncs")
    public ArrayList<String> data = new ArrayList<>();


    public Offermodel(String _id, String offerHeading, String name, String category, String offerPage, String allOfferPage, String iconPath, ArrayList<String> data) {
        this._id = _id;
        this.offerHeading = offerHeading;
        this.name = name;
        this.category = category;
        this.offerPage = offerPage;
        this.allOfferPage = allOfferPage;
        this.iconPath = iconPath;
        this.data = data;
    }



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOfferHeading() {
        return offerHeading;
    }

    public void setOfferHeading(String offerHeading) {
        this.offerHeading = offerHeading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOfferPage() {
        return offerPage;
    }

    public void setOfferPage(String offerPage) {
        this.offerPage = offerPage;
    }

    public String getAllOfferPage() {
        return allOfferPage;
    }

    public void setAllOfferPage(String allOfferPage) {
        this.allOfferPage = allOfferPage;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
