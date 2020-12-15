package com.example.ozmapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;

import java.util.ArrayList;

public class FooterRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.cashback_list);

        RecyclerView recyclerView = (RecyclerView) findViewById (R.id.cashback_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        FooterAdapter footerAdapter = new FooterAdapter(FooterRecyclerActivity.this, getListItems ());
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setAdapter (footerAdapter);

    }


    public ArrayList<Generic> getListItems () {
        ArrayList<Generic> generics = new ArrayList<Generic> ();
        for (int i = 0; i < 10; i++) {
            Generic item = new Generic ();
            item.setName ("list item" + i);
            generics.add (item);
        }
        return generics;
    }


}
