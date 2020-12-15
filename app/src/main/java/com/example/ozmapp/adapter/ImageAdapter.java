package com.example.ozmapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.CashbackActivity;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.bannermodel;
import com.example.ozmapp.model.cashback_model;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;

public class ImageAdapter extends PagerAdapter {

    Context mContext;
    public static ArrayList<bannermodel>sliderImageId ;
    APIInterface apiInterface;
    LayoutInflater layoutInflater;

    public ImageAdapter(Context context, ArrayList<bannermodel>sliderImageId) {
        this.mContext = context;
        this.sliderImageId=sliderImageId;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = layoutInflater.inflate(R.layout.banner, container,false);
        final bannermodel data = sliderImageId.get(position);
        ImageView iv = (ImageView) imageLayout.findViewById(R.id.img1);
        Picasso.with(mContext).load(data.imagePath.replaceAll(" ", "%20")).into(iv);

      //  Picasso.with(mContext).load("http://64.227.21.30/images/social.png").into(iv);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public int getCount() {
        return sliderImageId.size();
    }

    public static int returncount() {
        return sliderImageId.size();
    }

}