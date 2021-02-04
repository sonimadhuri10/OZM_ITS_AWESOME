package com.example.ozmapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;
import com.example.ozmapp.adapter.CategoryAdapter;
import com.example.ozmapp.adapter.SubcategoeryAdapter;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.SubCategoeyModel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class SubcategoryActivity  extends AppCompatActivity {

    String category = "",refresh="";
    RecyclerView recyclerView ;
    SubcategoeryAdapter subcategoeryAdapter ;
    ArrayList<SubCategoeyModel> subCategories_list ;
    ProgressDialog pd;
    ConnectionDetector cd;
    SessionManagment sd;
    APIInterface apiInterface ;
    LinearLayout llNodata ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_layout);

        Intent in = getIntent();
        category =  in.getStringExtra("category");
        refresh =  in.getStringExtra("refresh");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category);

        sd = new SessionManagment(SubcategoryActivity.this);
        cd = new ConnectionDetector(SubcategoryActivity.this);
        pd = new ProgressDialog(SubcategoryActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        llNodata = (LinearLayout)findViewById(R.id.llNodata);

        recyclerView = (RecyclerView)findViewById(R.id.recycle_subcategory);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager_multiview = new LinearLayoutManager(SubcategoryActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager_multiview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        subCategories_list = new ArrayList<>();

        SubCategories();

    }

    public void SubCategories() {
        try {
            JsonObject object=new JsonObject();
            object.addProperty("category",category);
            object.addProperty("userid",sd.getUSER_ID());

            pd.show();
            Call<ArrayList<SubCategoeyModel>> call = apiInterface.getSubcategory(object);
            call.enqueue(new Callback<ArrayList<SubCategoeyModel>>() {
                @Override
                public void onResponse(Call<ArrayList<SubCategoeyModel>> call, retrofit2.Response<ArrayList<SubCategoeyModel>> response) {
                    ArrayList<SubCategoeyModel> resource = response.body();
                    pd.dismiss();

                    subCategories_list = resource ;
                    if(subCategories_list.size() == 0){
                        llNodata.setVisibility(View.VISIBLE);
                    }else{
                        llNodata.setVisibility(View.GONE);
                        subcategoeryAdapter = new SubcategoeryAdapter(subCategories_list,SubcategoryActivity.this,category);
                        recyclerView.setAdapter(subcategoeryAdapter);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<SubCategoeyModel>> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(SubcategoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.offer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent in = new Intent(SubcategoryActivity.this,OfferActivity.class);
                in.putExtra("category",category);
                startActivity(in);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
