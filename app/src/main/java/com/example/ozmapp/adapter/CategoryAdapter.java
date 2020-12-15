package com.example.ozmapp.adapter;

import android.app.Activity;
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
import com.example.ozmapp.model.CategoryModel;
import com.example.ozmapp.model.HomeModel;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    ArrayList<HomeModel> al;
    Context context;

    public CategoryAdapter(ArrayList<HomeModel> al, Context context) {
        this.al = al;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName ;
        ImageView imageView ;
        LinearLayout linearLayout ;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvCategory);
            imageView = (ImageView) view.findViewById(R.id.imgCategory);
            linearLayout = (LinearLayout)view.findViewById(R.id.llCategory);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final HomeModel data = al.get(position);

          Picasso.with(context).load(data.getIcon()).into(holder.imageView);
          holder.tvName.setText(data.getName());

          holder.linearLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent in = new Intent(context, SubcategoryActivity.class);
                  in.putExtra("category",data.getName());
                  in.putExtra("refresh","yes");
                  context.startActivity(in);
              }
          });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }
}
