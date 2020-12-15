package com.example.ozmapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ozmapp.R;
import com.example.ozmapp.comman.SessionManagment;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 9000;
    SessionManagment sd;
    ImageView imageView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        sd = new SessionManagment(this);

        imageView = (ImageView)findViewById(R.id.imgGif);
        Glide.with(this).load(R.drawable.compress).into(imageView);

        new Handler().postDelayed(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void run() {

                if (sd.getLOGIN_STATUS().equals("true")) {
                    Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                } else{
                    Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i1);
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }

            }
        }, SPLASH_TIME_OUT);

    }
}
