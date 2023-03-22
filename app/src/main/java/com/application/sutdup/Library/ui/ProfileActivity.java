package com.application.sutdup.Library.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.application.sutdup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference,databaseUser;
    MyAdapter myAdapter;
    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.shopList);

        shopDataArrayList = new ArrayList<>();
        userDataArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, shopDataArrayList,userDataArrayList);

        // Retrieve the stored user ID from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString("userId_key", "");



        /** Implementing Grid View**/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);


        /**implementing the abstract class Database**/
        Database database = new Database() {
            @Override
            public void setDatabaseReference(String path) {
                super.setDatabaseReference(path);
            }

            @Override
            public DatabaseReference getDatabaseReference() {
                return super.getDatabaseReference();
            }
        };
        database.setDatabaseReference("items");
        databaseReference = database.getDatabaseReference();
        //databaseReference = FirebaseDatabase.getInstance().getReference("items"); //can delete later just leave it for now!!
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    System.out.println(snapshot.getChildren());
                    ShopData shopData = dataSnapshot.getValue(ShopData.class);
                    if (shopData.getUserId().equals(userId)){
                        Log.i("UserId",shopData.getUserId());
                        //Log.i("UserId",shopData.getUserId());
                        //add to the values to te array list
                        shopDataArrayList.add(shopData);
                    }

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        System.out.println(shopDataArrayList);
        database.setDatabaseReference("users");
        databaseUser = database.getDatabaseReference();
        //databaseReference = FirebaseDatabase.getInstance().getReference("items"); //can delete later just leave it for now!!
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    System.out.println(snapshot.getChildren());
                    UserData userData = dataSnapshot.getValue(UserData.class);
                    //add to the values to te array list
                    userDataArrayList.add(userData);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;


                }
                return false;
            }
        });
        Button sellanitem = findViewById(R.id.sellanitem);
        sellanitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, SellItemActivity.class));
            }
        });
    }

}