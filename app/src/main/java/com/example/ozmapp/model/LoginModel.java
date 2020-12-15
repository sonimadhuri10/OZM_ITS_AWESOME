package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginModel {

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public ArrayList<MainListModel> mainlist = new ArrayList<>();

    public static class MainListModel {

        @SerializedName("_id")
        public String _id;

        @SerializedName("otp")
        public String otp;

        @SerializedName("name")
        public String name;

        @SerializedName("email")
        public String email;

        @SerializedName("phone")
        public String phone;

        @SerializedName("pass")
        public String pass;

        @SerializedName("usertype")
        public String usertype;


    }
}
