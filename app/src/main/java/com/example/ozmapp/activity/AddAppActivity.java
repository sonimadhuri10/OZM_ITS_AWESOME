package com.example.ozmapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.Interface.RefreshInterface;
import com.example.ozmapp.R;
import com.example.ozmapp.adapter.AddItemAdapter;
import com.example.ozmapp.adapter.SubcategoeryAdapter;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.AddModel;
import com.example.ozmapp.model.SubCategoeyModel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class AddAppActivity extends AppCompatActivity
        implements RefreshInterface , View.OnClickListener {

    RecyclerView recyclerView ;
    LinearLayout llNodata ;
    Button btnAdd;
    ProgressDialog pd ;
    ConnectionDetector cd;
    SessionManagment sd;
    APIInterface apiInterface ;
    ArrayList<AddModel> addList ;
    String category ="" , subcategory = "" ;
    AddItemAdapter addItemAdapter ;
    int count = 0 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_applayout);

        pd = new ProgressDialog(AddAppActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Intent in = getIntent();
        category=  in.getStringExtra("category");
        subcategory =  in.getStringExtra("subcategory");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage "+subcategory);

        recyclerView = (RecyclerView)findViewById(R.id.recycle_add);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(AddAppActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnAdd =(Button)findViewById(R.id.btnAddApp);
        addList = new ArrayList<>();
        llNodata = (LinearLayout)findViewById(R.id.llNodata);

        btnAdd.setOnClickListener(this);

        showallApp();

    }

    public void showallApp() {
        try {
            JsonObject object=new JsonObject();
            object.addProperty("category",category);
            object.addProperty("subCategory",subcategory);
            object.addProperty("userid",sd.getUSER_ID());

            pd.show();
            Call<ArrayList<AddModel>> call = apiInterface.getAddApp(object);
            call.enqueue(new Callback<ArrayList<AddModel>>() {
                @Override
                public void onResponse(Call<ArrayList<AddModel>> call, retrofit2.Response<ArrayList<AddModel>> response) {
                    ArrayList<AddModel> resource = response.body();
                    pd.dismiss();
                    addList = resource ;
                    if(addList.size() == 0){
                        llNodata.setVisibility(View.VISIBLE);
                        btnAdd.setVisibility(View.GONE);
                    }else{
                        llNodata.setVisibility(View.GONE);
                        btnAdd.setVisibility(View.VISIBLE);
                        addItemAdapter = new AddItemAdapter(addList,AddAppActivity.this);
                        recyclerView.setAdapter(addItemAdapter);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<AddModel>> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(AddAppActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        if(count == 0){
            finish();
        }else{
            Intent in1 = new Intent(AddAppActivity.this,SubcategoryActivity.class);
            in1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in1.putExtra("category",category);
            in1.putExtra("refresh","yes");
            startActivity(in1);
            finish();
        }
    }

    @Override
    public void refres() {
        count++;
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btnAddApp:

             if(count == 0) {
                 finish();
             }else{
                 Intent in = new Intent(AddAppActivity.this,SubcategoryActivity.class);
                 in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 in.putExtra("category",category);
                 in.putExtra("refresh","yes");
                 startActivity(in);
                 finish();
             }

             break;
         default:

             break;
      }
    }
}
