package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

public class cashback_model {


    @SerializedName("_id")
    public String _id;

    @SerializedName("application")
    public String application;

    @SerializedName("amount")
    public String amount;

    @SerializedName("orderDate")
    public String orderDate;

    @SerializedName("approved")
    public String approved;

    @SerializedName("phoneNumber")
    public String phoneNumber;


    public cashback_model(String _id, String application, String amount, String orderDate, String approved, String phoneNumber) {
        this._id = _id;
        this.application = application;
        this.amount = amount;
        this.orderDate = orderDate;
        this.approved = approved;
        this.phoneNumber = phoneNumber;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
