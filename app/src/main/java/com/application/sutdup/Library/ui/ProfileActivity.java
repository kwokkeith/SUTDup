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
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.application.sutdup.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    DatabaseReference databaseReference,databaseUser;
    MyAdapter myAdapter;
    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    private String userId;
    private FloatingActionButton addItemfab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.shopList);
        addItemfab =findViewById(R.id.fab);

        shopDataArrayList = new ArrayList<>();
        userDataArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, shopDataArrayList,userDataArrayList,this);

        // Retrieve the stored user ID from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString("userId_key", "");



        /** Implementing Grid View**/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    // Scrolled up, show floating action button
                    addItemfab.show();
                } else if (dy > 0) {
                    // Scrolled down, hide floating action button
                    addItemfab.hide();
                }
            }
        });


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
                    //Toast.makeText(ProfileActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
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
        });*/


        ChipNavigationBar chipNavigationBar = findViewById(R.id.bottomNavView);
        chipNavigationBar.setItemSelected(R.id.profile,true);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.profile:
                }
            }
        });

        /**Implementing Floating Action button**/
        addItemfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SellItemActivity.class));
            }
        });
    }

    @Override
    public void onItemClicked(ShopData shopData) {
        /**Push Data to another activity when cardView is clicked**/
        String itemname = shopData.getItemName();
        String itemprice = shopData.getItemPrice();
        String itemid = shopData.getItemId();
        String itemimage = shopData.getItemImage();
        String itemdescription = shopData.getItemDescription();
        Intent intent = new Intent(getApplicationContext(),EditItemActivity.class);
        intent.putExtra("itemname",itemname);
        intent.putExtra("itemprice",itemprice);
        intent.putExtra("itemid",itemid);
        intent.putExtra("itemimage",itemimage);
        intent.putExtra("itemdescription",itemdescription);
        startActivity(intent);

    }
}