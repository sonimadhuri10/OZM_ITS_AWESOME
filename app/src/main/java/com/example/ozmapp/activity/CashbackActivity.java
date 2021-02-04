package com.example.ozmapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;
import com.example.ozmapp.adapter.Cashback_adapter;
import com.example.ozmapp.adapter.SubcategoeryAdapter;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.SubCategoeyModel;
import com.example.ozmapp.model.cashback_model;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class CashbackActivity extends AppCompatActivity {

    ProgressDialog pd;
    ConnectionDetector cd;
    SessionManagment sd;
    APIInterface apiInterface ;
    RecyclerView recyclerView ;
    Cashback_adapter cashback_adapter ;
    ArrayList<cashback_model> al ;
    LinearLayout llNodata ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cashback_list);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait....");
        al = new ArrayList<>();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cashback History");

        llNodata = (LinearLayout)findViewById(R.id.llNodata);

        recyclerView=(RecyclerView)findViewById(R.id.cashback_recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CashbackActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getCashback_history();
    }

    public void getCashback_history() {
        try {
            JsonObject object=new JsonObject();
            object.addProperty("uid",sd.getUSER_ID());

            pd.show();
            Call<ArrayList<cashback_model>> call = apiInterface.getCashbackHistory(object);
            call.enqueue(new Callback<ArrayList<cashback_model>>() {
                @Override
                public void onResponse(Call<ArrayList<cashback_model>> call, retrofit2.Response<ArrayList<cashback_model>> response) {
                    ArrayList<cashback_model> resource = response.body();
                    pd.dismiss();
                    al = resource ;
                    if(al.size() == 0){
                        llNodata.setVisibility(View.VISIBLE);
                    }else{
                        llNodata.setVisibility(View.GONE);
                        cashback_adapter = new Cashback_adapter(al,CashbackActivity.this);
                        recyclerView.setAdapter(cashback_adapter);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<cashback_model>> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(CashbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
