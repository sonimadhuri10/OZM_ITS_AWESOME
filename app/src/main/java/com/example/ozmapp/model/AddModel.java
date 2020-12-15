package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

public class AddModel {

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

    @SerializedName("order")
    public String order;

    @SerializedName("fallbackUrl")
    public String fallbackUrl;

    @SerializedName("fallbackUrl2")
    public String fallbackUrl2;

    @SerializedName("openByPackageName")
    public String openByPackageName;

    @SerializedName("default")
    public String defau;

    @SerializedName("selected")
    public String selected;

    public AddModel(String _id, String active, String name,
                    String category, String subCategory, String link,
                    String androidImageUrl, String order, String fallbackUrl,
                    String fallbackUrl2, String openByPackageName, String defau,String selected) {
        this._id = _id;
        this.active = active;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.link = link;
        this.androidImageUrl = androidImageUrl;
        this.order = order;
        this.fallbackUrl = fallbackUrl;
        this.fallbackUrl2 = fallbackUrl2;
        this.openByPackageName = openByPackageName;
        this.defau = defau;
        this.selected = selected ;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAndroidImageUrl() {
        return androidImageUrl;
    }

    public void setAndroidImageUrl(String androidImageUrl) {
        this.androidImageUrl = androidImageUrl;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }

    public String getFallbackUrl2() {
        return fallbackUrl2;
    }

    public void setFallbackUrl2(String fallbackUrl2) {
        this.fallbackUrl2 = fallbackUrl2;
    }

    public String getOpenByPackageName() {
        return openByPackageName;
    }

    public void setOpenByPackageName(String openByPackageName) {
        this.openByPackageName = openByPackageName;
    }

    public String getDefau() {
        return defau;
    }

    public void setDefau(String defau) {
        this.defau = defau;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
