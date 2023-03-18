package com.application.sutdup.Library.ui;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.sutdup.R;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ShopData> shopDataArrayList;

    public MyAdapter(Context context, ArrayList<ShopData> shopData) {
        this.context = context;
        this.shopDataArrayList = shopData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_grid_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         ShopData data = shopDataArrayList.get(position);
         holder.name.setText(data.getItemName());
        holder.itemPrice.setText(data.getItemPrice());
        holder.sellerName.setText(data.getUserId());
    }

    @Override
    public int getItemCount() {
        return shopDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView name,itemPrice,sellerName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shoptv);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            sellerName = itemView.findViewById(R.id.sellerName);
        }
    }
}
