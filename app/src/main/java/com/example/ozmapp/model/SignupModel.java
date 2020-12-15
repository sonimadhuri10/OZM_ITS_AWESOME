package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

public class SignupModel {

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public String result;

    @SerializedName("otp")
    public String otp;

}
