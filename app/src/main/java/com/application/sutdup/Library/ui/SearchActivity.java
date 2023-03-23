package com.application.sutdup.Library.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sutdup.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SelectListener{
    private SearchView searchView;
    private RecyclerView recyclerView;
    DatabaseReference databaseReference,databaseUser;
    MyAdapter myAdapter;
    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        searchView = findViewById(R.id.serachView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

            private void filterList(String newText) {
                ArrayList<ShopData> filteredlist = new ArrayList<>();
                for (ShopData item: shopDataArrayList){
                    if (item.getItemName().toLowerCase().contains(newText.toLowerCase())){
                        filteredlist.add(item);
                    }
                    if (filteredlist.isEmpty()){
                        //Toast.makeText(SearchActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        myAdapter.setFilteredList(filteredlist);
                    }
                }

            }
        });
        recyclerView = findViewById(R.id.shopList);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView); //Initialize and assign variable
        bottomNavigationView.setSelectedItemId(R.id.search);  //Set Home Selected


        shopDataArrayList = new ArrayList<>();
        userDataArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, shopDataArrayList,userDataArrayList,this);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(myAdapter);

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
                    //add to the values to te array list
                    shopDataArrayList.add(shopData);
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
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClicked(ShopData shopData) {
        /**Do nothing when item is clicked**/
        return;
    }
}