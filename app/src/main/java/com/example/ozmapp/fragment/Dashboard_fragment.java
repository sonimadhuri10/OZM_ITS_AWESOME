package com.example.ozmapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.LoginActivity;
import com.example.ozmapp.activity.SignupActivity;
import com.example.ozmapp.adapter.CategoryAdapter;
import com.example.ozmapp.adapter.ImageAdapter;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.model.CategoryModel;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.model.bannermodel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class Dashboard_fragment extends Fragment {

    RecyclerView recyclerView ;
    CategoryAdapter categoryAdapter ;
    LinearLayout llShare ;
    LinearLayout sliderDotspanel;
    ViewPager mViewPager ;
    ImageAdapter adapterView ;
    private int dotscount;
    private ImageView[] dots;
    Timer timer;
    APIInterface apiInterface ;
    ProgressDialog pd ;
    ArrayList<HomeModel> listCategories ;
    ArrayList<bannermodel> sliderImageId ;
    ConnectionDetector cd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_layout, container, false);

        mViewPager = (ViewPager)v.findViewById(R.id.viewpager);
        sliderDotspanel = (LinearLayout)v.findViewById(R.id.SliderDots);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(getActivity());
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        llShare = (LinearLayout)v.findViewById(R.id.llShare);

        listCategories = new ArrayList<>();

        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(), "Please check your internet connection...", Toast.LENGTH_SHORT).show();
        }

        banner(getActivity());
        Categories();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                   for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,"OZM ITS AWESOME");
                    String app_url = "\n\nDownload the OZM ITS AWESOME app by given link - \n\n"+"https://play.google.com/store/apps/details?id=com.snapchat.android";
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        return  v;
    }

    public void banner(Context context) {
        try {
            JsonObject object=new JsonObject();

            Call<ArrayList<bannermodel>> call = apiInterface.getbannerlist(object);
            call.enqueue(new Callback<ArrayList<bannermodel>>() {
                @Override
                public void onResponse(Call<ArrayList<bannermodel>> call, retrofit2.Response<ArrayList<bannermodel>> response) {
                    ArrayList<bannermodel> resource = response.body();
                    sliderImageId = resource ;
                    adapterView = new ImageAdapter(getActivity(),sliderImageId);
                    mViewPager.setAdapter(adapterView);
                    mViewPager.setCurrentItem(0);

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mViewPager.post(new Runnable(){
                                @Override
                                public void run() {
                                    mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%dots.length);
                                }
                            });
                        }
                    };

                    timer = new Timer();
                    timer.schedule(timerTask, 3000, 3000);

                    dotscount = ImageAdapter.returncount();
                    dots = new ImageView[dotscount];

                    for(int i = 0; i < dotscount; i++){
                        dots[i] = new ImageView(getActivity());
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(8, 0, 8, 0);
                        sliderDotspanel.addView(dots[i], params);
                    }

                    dots[0].setImageDrawable(ContextCompat.getDrawable(
                            getActivity(),
                            R.drawable.active_dot)
                    );



                }
                @Override
                public void onFailure(Call<ArrayList<bannermodel>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

    public void Categories() {
        try {
            JsonObject object=new JsonObject();
            //object.addProperty("","");
            pd.show();
            Call<ArrayList<HomeModel>> call = apiInterface.getHome(object);
            call.enqueue(new Callback<ArrayList<HomeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<HomeModel>> call, retrofit2.Response<ArrayList<HomeModel>> response) {
                    ArrayList<HomeModel> resource = response.body();
                    pd.dismiss();
                    listCategories = resource ;
                    categoryAdapter = new CategoryAdapter(listCategories,getActivity());
                    recyclerView.setAdapter(categoryAdapter);
                }
                @Override
                public void onFailure(Call<ArrayList<HomeModel>> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }
}
