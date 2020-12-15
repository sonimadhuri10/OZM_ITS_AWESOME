package com.example.ozmapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozmapp.R;
import com.example.ozmapp.activity.AddAppActivity;
import com.example.ozmapp.activity.FooterAdapter;
import com.example.ozmapp.activity.SignupActivity;
import com.example.ozmapp.activity.SubcategoryActivity;
import com.example.ozmapp.model.HomeModel;
import com.example.ozmapp.model.SubCategoeyModel;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<SubCategoeyModel.applist> al;
    Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    String cat = "" , subcat = "" ;

    public SubAppAdapter(ArrayList<SubCategoeyModel.applist> al, Context context, String cat, String subcat) {
        this.al = al;
        this.context = context;
        this.cat = cat ;
        this.subcat = subcat ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.footer_layout, parent, false);
            return new FooterViewHolder(v);
        } else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.subcat_itemlayout, parent, false);
            return new GenericViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, AddAppActivity.class);
                    in.putExtra("category",cat);
                    in.putExtra("subcategory",subcat);
                    context.startActivity(in);
                }
            });

        } else if(holder instanceof GenericViewHolder) {
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            genericViewHolder.tvName.setText (al.get(position).name);
            Picasso.with(context).load(al.get(position).androidImageUrl).into(genericViewHolder.imageView);

            genericViewHolder.llAppName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(al.get(position).link));
                    context.startActivity(browserIntent);
                }
            });
        }

    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == al.size ();
    }

    @Override
    public int getItemCount () {
        return al.size () + 1;
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        public FooterViewHolder (View itemView) {
            super (itemView);
            this.linearLayout = (LinearLayout)itemView.findViewById(R.id.llAdd);
        }
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        TextView tvName ;
        ImageView imageView ;
        LinearLayout llAppName ;

        public GenericViewHolder (View view) {
            super (view);
            this.tvName = (TextView) view.findViewById(R.id.tvAppName);
            this.imageView = (ImageView) view.findViewById(R.id.imgApp);
            this.llAppName = (LinearLayout)view.findViewById(R.id.llAppName);

        }
    }
}
