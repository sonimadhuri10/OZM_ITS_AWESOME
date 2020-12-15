package com.example.ozmapp.networkManager;

import com.example.ozmapp.model.AddModel;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.LoginModel;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.model.SubCategoeyModel;
import com.example.ozmapp.model.bannermodel;
import com.example.ozmapp.model.cashapp_model;
import com.example.ozmapp.model.cashback_model;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ANDROID on 08-Jan-18.
 */

public interface APIInterface {

    @POST("signupWebService")
    Call<SignupModel> getRegister(
            @Body JsonObject body);

    @POST("loginWebService")
    Call<LoginModel> getLogin(
            @Body JsonObject body);

    @POST("forget_pass")
    Call<LoginModel> getForgot(
            @Body JsonObject body);

    @POST("forget_pass/change_pass")
    Call<SignupModel> resetPassword(
            @Body JsonObject body);

    @POST("category/admin/list")
    Call<ArrayList<HomeModel>> getHome(
            @Body JsonObject body);

    @POST("feedback")
    Call<LoginModel> getFeedback(
            @Body JsonObject body);

    @POST("userapp/appList")
    Call<ArrayList<SubCategoeyModel>> getSubcategory(
            @Body JsonObject body);

    @POST("userapp/allapp")
    Call<ArrayList<AddModel>> getAddApp(
            @Body JsonObject body);

    @POST("userapp/add")
    Call<SignupModel> getAddItem(
            @Body JsonObject body);

    @POST("userapp/delete")
    Call<SignupModel> getDeleteItem(
            @Body JsonObject body);

    @POST("cashback/user/list")
    Call<ArrayList<cashback_model>> getCashbackHistory(
            @Body JsonObject body);

    @POST("cashback/cashback_application")
    Call<ArrayList<cashapp_model>> getcashapp(
            @Body JsonObject body);

    @POST("forget_pass/change_pass")
    Call<SignupModel> applycashback(
            @Body JsonObject body);

    @POST("banner/admin/list")
    Call<ArrayList<bannermodel>> getbannerlist(
            @Body JsonObject body);


    // GET METHODS
/*
    @GET("privacy")
    Call<PrivacyModel> getPrivacyPolicy();

    @GET("Coupons")
    Call<StoreModel> getCoupans();

    @GET("wallet")
    Call<SignupModel> getWallet(@Header("Authorization") String header);


    // POST METHODS


    @Multipart
    @POST("updateProfile")
    Call<SignupModel> getUpdateProfile(@Header("Authorization") String header,
                                       @Part MultipartBody.Part file, @Part("photo") RequestBody photo,
                                       @Part("name") RequestBody name,
                                       @Part("phone") RequestBody mobileno, @Part("email") RequestBody email,
                                       @Part("address") RequestBody address,
                                       @Part("city") RequestBody city, @Part("zip") RequestBody zipcode,
                                       @Part("state") RequestBody state, @Part("country") RequestBody country,
                                       @Part("flat_no") RequestBody flat_no, @Part("building") RequestBody building);


    @FormUrlEncoded
    @POST("login/otp")
    Call<SignupModel> getOtpVerify(@Field("phone") String mobileno,@Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("category/product")
    Call<SubCategoryModel> getCategoryProduct(@Field("slug") String slug,
                                              @Field("latitude") String latitude ,
                                              @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("product")
    Call<ProductModel> getproductDetail(@Field("id") String slug);


    @FormUrlEncoded
    @POST("home")
    Call<FinalCategoryModel> getHome(@Field("latitude") String latitude ,
                                     @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("allStore")
    Call<StoreModel> getAllStore(@Field("latitude") String latitude ,
                                 @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("changePassword")
    Call<SignupModel> getChnagePassword(@Header("Authorization") String header,
                                        @Field("password") String password ,
                                        @Field("Oldpassword") String Oldpassword);*/


}

