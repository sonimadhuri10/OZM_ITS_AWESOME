package com.example.ozmapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.Interface.RefreshInterface;
import com.example.ozmapp.R;
import com.example.ozmapp.activity.LoginActivity;
import com.example.ozmapp.activity.SignupActivity;
import com.example.ozmapp.comman.ConnectionDetector;
import com.example.ozmapp.comman.SessionManagment;
import com.example.ozmapp.model.AddModel;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.SignupModel;
import com.example.ozmapp.model.SubCategoeyModel;
import com.example.ozmapp.networkManager.APIClient;
import com.example.ozmapp.networkManager.APIInterface;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.MyViewHolder> {
    ArrayList<AddModel> al;
    Context context;
    ProgressDialog pd;
    ConnectionDetector cd ;
    SessionManagment sd;
    APIInterface apiInterface ;
    RefreshInterface refreshInterface ;

    public AddItemAdapter(ArrayList<AddModel> al, Context context) {
        this.al = al;
        this.context = context;
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        cd = new ConnectionDetector(context);
        sd = new SessionManagment(context);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        refreshInterface = (RefreshInterface)context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName , tvNameClicked ;
        ImageView imageView , imageViewClicked;
        LinearLayout llAppName , llAppNameClicked ;
        ImageView imgChecked , imgUnChecked ;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvAppName);
            tvNameClicked = (TextView) view.findViewById(R.id.tvAppNameclicked);
            imageView = (ImageView) view.findViewById(R.id.imgApp);
            imageViewClicked = (ImageView) view.findViewById(R.id.imgAppclicked);
            llAppName = (LinearLayout)view.findViewById(R.id.llAppName);
            llAppNameClicked = (LinearLayout)view.findViewById(R.id.llAppNameunclicked);
            imgChecked = (ImageView)view.findViewById(R.id.imgChecked);
            imgUnChecked = (ImageView)view.findViewById(R.id.imgUnchecked);
        }

    }

    @NonNull
    @Override
    public AddItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subapp_layout, parent, false);
        return new AddItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AddModel data = al.get(position);

        if (data.selected.equals("true")){
            holder.llAppName.setVisibility(View.VISIBLE);
            holder.llAppNameClicked.setVisibility(View.GONE);
            holder.imgChecked.setVisibility(View.VISIBLE);
            holder.imgUnChecked.setVisibility(View.GONE);
        }else{
            holder.llAppName.setVisibility(View.GONE);
            holder.llAppNameClicked.setVisibility(View.VISIBLE);
            holder.imgChecked.setVisibility(View.GONE);
            holder.imgUnChecked.setVisibility(View.VISIBLE);
        }

        Picasso.with(context).load(data.androidImageUrl).into(holder.imageView);
        holder.tvName.setText(data.name);

        Picasso.with(context).load(data.androidImageUrl).into(holder.imageViewClicked);
        holder.tvNameClicked.setText(data.name);

      /*  holder.imgChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JsonObject object=new JsonObject();
                    object.addProperty("userid",sd.getUSER_ID());
                    object.addProperty("name",data.name);
                    object.addProperty("category",data.category);
                    object.addProperty("subCategory",data.subCategory);

                    pd.show();
                    Call<SignupModel> call = apiInterface.getDeleteItem(object);
                    call.enqueue(new Callback<SignupModel>() {
                        @Override
                        public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                            SignupModel resource = response.body();
                            pd.dismiss();
                            if (resource.result.equalsIgnoreCase("success fully remove")) {
                                Toast.makeText(context, "Successfully removed", Toast.LENGTH_SHORT).show();
                                holder.imgChecked.setVisibility(View.GONE);
                                holder.imgUnChecked.setVisibility(View.VISIBLE);
                                refreshInterface.refres();
                            } else {
                                Toast.makeText(context, "Please try after some time", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<SignupModel> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(context, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });
                }catch (Exception e){

                }

            }
        });

        holder.imgUnChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    JsonObject object=new JsonObject();
                    object.addProperty("userid",sd.getUSER_ID());
                    object.addProperty("active",data.active);
                    object.addProperty("name",data.name);
                    object.addProperty("order",data.order);
                    object.addProperty("category",data.category);
                    object.addProperty("subCategory",data.subCategory);
                    object.addProperty("link",data.link);
                    object.addProperty("fallbackUrl",data.fallbackUrl);
                    object.addProperty("fallbackUrl2",data.fallbackUrl2);
                    object.addProperty("default",data.defau);
                    object.addProperty("openByPackageName",data.openByPackageName);
                    object.addProperty("selected",data.selected);
                    object.addProperty("androidImageUrl",data.androidImageUrl);

                    pd.show();
                    Call<SignupModel> call = apiInterface.getAddItem(object);
                    call.enqueue(new Callback<SignupModel>() {
                        @Override
                        public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                            SignupModel resource = response.body();
                            pd.dismiss();
                            if (resource.status.equalsIgnoreCase("1")) {
                                Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                holder.imgChecked.setVisibility(View.VISIBLE);
                                holder.imgUnChecked.setVisibility(View.GONE);
                                refreshInterface.refres();
                            } else {
                                Toast.makeText(context, "Please try after some time", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<SignupModel> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(context, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });
                }catch (Exception e){

                }


            }
        });*/

        holder.llAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    JsonObject object=new JsonObject();
                    object.addProperty("userid",sd.getUSER_ID());
                    object.addProperty("name",data.name);
                    object.addProperty("category",data.category);
                    object.addProperty("subCategory",data.subCategory);

                    pd.show();
                    Call<SignupModel> call = apiInterface.getDeleteItem(object);
                    call.enqueue(new Callback<SignupModel>() {
                        @Override
                        public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                            SignupModel resource = response.body();
                            pd.dismiss();
                            if (resource.result.equalsIgnoreCase("success fully remove")) {
                                Toast.makeText(context, "Successfully removed", Toast.LENGTH_SHORT).show();
                                holder.imgChecked.setVisibility(View.GONE);
                                holder.imgUnChecked.setVisibility(View.VISIBLE);
                                holder.llAppName.setVisibility(View.GONE);
                                holder.llAppNameClicked.setVisibility(View.VISIBLE);
                                refreshInterface.refres();
                            } else {
                                Toast.makeText(context, "Please try after some time", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<SignupModel> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(context, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });
                }catch (Exception e){

                }

            }
        });

        holder.llAppNameClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JsonObject object=new JsonObject();
                    object.addProperty("userid",sd.getUSER_ID());
                    object.addProperty("active",data.active);
                    object.addProperty("name",data.name);
                    object.addProperty("order",data.order);
                    object.addProperty("category",data.category);
                    object.addProperty("subCategory",data.subCategory);
                    object.addProperty("link",data.link);
                    object.addProperty("fallbackUrl",data.fallbackUrl);
                    object.addProperty("fallbackUrl2",data.fallbackUrl2);
                    object.addProperty("default",data.defau);
                    object.addProperty("openByPackageName",data.openByPackageName);
                    object.addProperty("selected",data.selected);
                    object.addProperty("androidImageUrl",data.androidImageUrl);

                    pd.show();
                    Call<SignupModel> call = apiInterface.getAddItem(object);
                    call.enqueue(new Callback<SignupModel>() {
                        @Override
                        public void onResponse(Call<SignupModel> call, retrofit2.Response<SignupModel> response) {
                            SignupModel resource = response.body();
                            pd.dismiss();
                            if (resource.status.equalsIgnoreCase("1")) {
                                Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                holder.imgChecked.setVisibility(View.VISIBLE);
                                holder.imgUnChecked.setVisibility(View.GONE);
                                holder.llAppName.setVisibility(View.VISIBLE);
                                holder.llAppNameClicked.setVisibility(View.GONE);
                                refreshInterface.refres();
                            } else {
                                Toast.makeText(context, "Please try after some time", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<SignupModel> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(context, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                            call.cancel();
                        }
                    });
                }catch (Exception e){

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return al.size();
    }


}
