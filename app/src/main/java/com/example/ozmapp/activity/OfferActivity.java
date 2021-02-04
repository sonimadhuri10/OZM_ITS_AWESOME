package com.example.ozmapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;
import com.example.ozmapp.adapter.AddItemAdapter;
import com.example.ozmapp.adapter.Cashback_adapter;
import com.example.ozmapp.adapter.OfferAdapter;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.AddModel;
import com.example.ozmapp.model.Offermodel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class OfferActivity extends AppCompatActivity {

    String category = "" ;
    RecyclerView recyclerView ;
    Spinner spinner ;
    ConnectionDetector cd;
    SessionManagment sd;
    APIInterface apiInterface ;
    ProgressDialog pd;
    ArrayList<AddModel> al ;
    LinearLayout llNodata ;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    OfferAdapter offerAdapter ;
    ArrayList<Offermodel> offerlist  ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_layout);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait....");
        al = new ArrayList<>();
        list = new ArrayList<String>();
        offerlist = new ArrayList<Offermodel>();
        llNodata =(LinearLayout)findViewById(R.id.llNodata);

        Intent in = getIntent();
        category = in.getStringExtra("category");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category+" Offers");

        recyclerView=(RecyclerView)findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OfferActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        spinner  =(Spinner)findViewById(R.id.spoffers);

        add_data_To_Spinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Offers(selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void add_data_To_Spinner() {
        try {
            JsonObject object=new JsonObject();
            object.addProperty("category",category);

            pd.show();
            Call<ArrayList<AddModel>> call = apiInterface.getOfferType(object);
            call.enqueue(new Callback<ArrayList<AddModel>>() {
                @Override
                public void onResponse(Call<ArrayList<AddModel>> call, retrofit2.Response<ArrayList<AddModel>> response) {
                    ArrayList<AddModel> resource = response.body();
                    pd.dismiss();
                    al = resource ;
                    if(al.size() == 0){
                        llNodata.setVisibility(View.VISIBLE);
                    }else{
                        llNodata.setVisibility(View.GONE);
                        list.add(0, "All");
                        for(int i = 0; i<al.size();  i++){
                            list.add(resource.get(i).name);
                        }

                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.sp_items,R.id.tvSp, list);
                        adapter.setDropDownViewResource(R.layout.sp_items);
                        spinner.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<AddModel>> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(OfferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

    public void Offers(String value) {
        try {
            JsonObject object=new JsonObject();

            if(value.equalsIgnoreCase("All")){
                object.addProperty("category",category);
            }else{
                object.addProperty("category",category);
                object.addProperty("offerGroup",value);
            }

            pd.show();
            Call<ArrayList<Offermodel>> call = apiInterface.getOffers(object);
            call.enqueue(new Callback<ArrayList<Offermodel>>() {
                @Override
                public void onResponse(Call<ArrayList<Offermodel>> call, retrofit2.Response<ArrayList<Offermodel>> response) {
                    ArrayList<Offermodel> resource = response.body();
                    pd.dismiss();
                    offerlist = resource ;
                    if(al.size() == 0){
                        llNodata.setVisibility(View.VISIBLE);
                    }else{
                        llNodata.setVisibility(View.GONE);
                        offerAdapter = new OfferAdapter(offerlist,OfferActivity.this);
                        recyclerView.setAdapter(offerAdapter);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Offermodel>> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(OfferActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            onBackPressed();
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
