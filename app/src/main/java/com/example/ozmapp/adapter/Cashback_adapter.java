package com.example.ozmapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.SubcategoryActivity;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.cashback_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Cashback_adapter extends RecyclerView.Adapter<Cashback_adapter.MyViewHolder> {
    ArrayList<cashback_model> al;
    Context context;

    public Cashback_adapter(ArrayList<cashback_model> al, Context context) {
        this.al = al;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName , tvAmt , tvStatus ;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvAppname);
            tvAmt = (TextView) view.findViewById(R.id.tvAmt);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cashback_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final cashback_model data = al.get(position);

        holder.tvName.setText(data.getApplication());
        holder.tvAmt.setText(data.getAmount()+" Rs");
        holder.tvStatus.setText(data.getApproved());

    }

    @Override
    public int getItemCount() {
        return al.size();
    }
}
