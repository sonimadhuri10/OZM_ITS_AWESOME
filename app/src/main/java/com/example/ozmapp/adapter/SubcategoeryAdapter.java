package com.example.ozmapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.AddAppActivity;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.SubCategoeyModel;

import java.util.ArrayList;

public class SubcategoeryAdapter extends RecyclerView.Adapter<SubcategoeryAdapter.MyViewHolder> {
    ArrayList<SubCategoeyModel> al;
    Context context;
    SubAppAdapter subAppAdapter;
    String cat ;


    public SubcategoeryAdapter(ArrayList<SubCategoeyModel> al, Context context , String cat) {
        this.al = al;
        this.context = context;
        this.cat = cat ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName ;
        RecyclerView recyclerView ;
        LinearLayout linearLayout ;

        public MyViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvSubCatName);
            linearLayout = (LinearLayout) view.findViewById(R.id.llAdd);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycle_selected_app);
            recyclerView.setHasFixedSize(false);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
            // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
      }

    }


    @NonNull
    @Override
    public SubcategoeryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_item_layout, parent, false);
            return new SubcategoeryAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SubCategoeyModel data = al.get(position);

        holder.tvName.setText(data.name);

/*
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, AddAppActivity.class);
                in.putExtra("category",cat);
                in.putExtra("subcategory",data.name);
                context.startActivity(in);
                // ((Activity)context).finish();
            }
        });*/

        subAppAdapter = new SubAppAdapter(data.app_list, context,cat,data.name);
        holder.recyclerView.setAdapter(subAppAdapter);
    }

    @Override
    public int getItemCount() {
        return this.al.size();
    }

}
