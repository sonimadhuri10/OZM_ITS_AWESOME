package com.example.ozmapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ozmapp.R;

public class Aboutus_fragment extends Fragment {

    WebView web1 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aboutus_layout, container, false);

        web1 = (WebView)v.findViewById(R.id.web);

        String s1 = "Once after registering, you will get a personalised space to manage all your apps at one place Below are some suggestions to improve your experience with website.\n" +
                "Register your self (with fb/google+ or direct site).\n" +
                "Add your accounts to the window for the accounts\n" +
                "For individual account, click on the icon of the account.\n";
        // String s1 = "You can use our services in a variety of ways to manage your privacy. For example, you can sign up for a Google Account if you want to create and manage content like emails and photos, or see more relevant search results. And you can use many Google services when you’re signed out or without creating an account at all, like searching on Google or watching YouTube videos. You can also choose to browse the web privately using Chrome in Incognito mode. And across our services, you can adjust your privacy settings to control what we collect and how your information is used.";
        String text1;
        text1 = "<html><body><p align=\"justify\"> <b>";
        text1 += s1;
        text1 += "</b></p></body></html>";

        web1.loadData(text1, "text/html", "utf-8(\\\"color\\\", \\\"blue\\\");");
        WebSettings settings = web1.getSettings();
        settings.setTextZoom(75);

        web1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        web1.setLongClickable(false);
        return v;
    }
}
