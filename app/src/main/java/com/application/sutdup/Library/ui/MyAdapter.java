package com.application.sutdup.Library.ui;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.application.sutdup.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    DatabaseReference databaseUser;


    public void setFilteredList(ArrayList<ShopData> filteredList){
        this.shopDataArrayList = filteredList;
        notifyDataSetChanged();
    }


    public MyAdapter(Context context, ArrayList<ShopData> shopData) {
        this.context = context;
        this.shopDataArrayList = shopData;
    }
    public MyAdapter(Context context,ArrayList<ShopData> shopData, ArrayList<UserData> userData) {
        this.context = context;
        this.shopDataArrayList = shopData;
        this.userDataArrayList = userData;
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

        for (UserData user: userDataArrayList){
            if (user.getPhone().equals(data.getUserId())){
                holder.sellerName.setText(user.getName());
            }
        }
        //holder.sellerName.setText(data.getUserId());




        byte[] decodedString = Base64.decode(data.getItemImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.itemImage.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return shopDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView name,itemPrice,sellerName;
        ImageView itemImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shoptv);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            sellerName = itemView.findViewById(R.id.sellerName);
            itemImage = itemView.findViewById(R.id.imageView);
        }

    }


}
