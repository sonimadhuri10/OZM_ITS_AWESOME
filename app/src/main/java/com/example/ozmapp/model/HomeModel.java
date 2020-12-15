package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

public class HomeModel {

    @SerializedName("_id")
    private String _id;

    @SerializedName("active")
    private String active;

    @SerializedName("name")
    private String name;

    @SerializedName("iconPath")
    private String icon;

    @SerializedName("order")
    private String order;

    public HomeModel(String _id, String active, String name, String icon, String order) {
        this._id = _id;
        this.active = active;
        this.name = name;
        this.icon = icon;
        this.order = order;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
