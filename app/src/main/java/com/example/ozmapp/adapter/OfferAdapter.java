package com.example.ozmapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ozmapp.R;
import com.example.ozmapp.model.Offermodel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class OfferAdapter extends
        RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    ArrayList<Offermodel> al;
    Context context;

    public OfferAdapter(ArrayList<Offermodel> al, Context context) {
        this.al = al;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName , tvPayment , tvShopName , tvOffer , tvAlloffer;
        ImageView imageView ;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvHeading);
            tvPayment = (TextView) itemView.findViewById(R.id.tvPayment);
            tvShopName = (TextView) itemView.findViewById(R.id.tvShopname);
            tvOffer = (TextView) itemView.findViewById(R.id.tvoffer);
            tvAlloffer = (TextView) itemView.findViewById(R.id.tvOffrall);
            imageView = (ImageView)view.findViewById(R.id.imgStore);
         }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_items_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Offermodel data = al.get(position);

        holder.tvName.setText(data.getOfferHeading());
        holder.tvShopName.setText(data.getName());
        String ar = data.getData().toString();
        String nr =  ar.replace("[","");
        String tr =  nr.replace("]","");
        String pr =  tr.replace(",","\n");

        holder.tvPayment.setText(" "+pr);

        /* for(int i=0;i<data.getData().size(); i++){
            holder.tvPayment.setText(data.getData().get(i));
        }*/

        Picasso.with(context).load(data.getIconPath()).into(holder.imageView);
        holder.tvOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.offerPage));
                context.startActivity(browserIntent);
            }
        });
        holder.tvAlloffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.allOfferPage));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }
}
