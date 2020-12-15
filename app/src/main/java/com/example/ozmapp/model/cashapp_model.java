package com.example.ozmapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class cashapp_model {

    @SerializedName("cashback_message")
    public String cashback_message;

    @SerializedName("cashbackApplication")
    public ArrayList<String> app_list = new ArrayList<>();


}
