package com.application.sutdup.Library.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{
    private SearchView searchView;
    private RecyclerView recyclerView;
    DatabaseReference databaseReference,databaseUser;
    MyAdapter myAdapter;
    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        // Retrieve the stored user ID from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString("userId_key", "");



        searchView = findViewById(R.id.serachView);
        searchView.clearFocus();
        searchView.setOnClickListener(this);
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
        shopDataArrayList = new ArrayList<>();
        userDataArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, shopDataArrayList,userDataArrayList,this);


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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopDataArrayList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    System.out.println(snapshot.getChildren());
                    ShopData shopData = dataSnapshot.getValue(ShopData.class);
                    if (shopData.getUserId().equals(userId)){
                        continue;
                    }
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
        ChipNavigationBar chipNavigationBar = findViewById(R.id.bottomNavView);
        chipNavigationBar.setItemSelected(R.id.search,true);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i){
                    case R.id.search:

                    case R.id.home:
                        chipNavigationBar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.profile:
                        chipNavigationBar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });
    }

    @Override
    public void onItemClicked(ShopData shopData) {
        String sellertelehandle = shopData.getSellerTelehandle();
        showPopup(sellertelehandle);
        android.os.Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(vibrationEffect);
        } else {
            vibrator.vibrate(50);
        }

    }

    private void showPopup(String sellertelehandle) {
        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
        alert.setMessage("Are you interested in this item? If yes, do you want to proceed to contact the seller?")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Intent telegramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/"+sellertelehandle));
                            telegramIntent.setPackage("org.telegram.messenger");
                            startActivity(telegramIntent);


                        }catch (Exception e){
                            Toast.makeText(SearchActivity.this, "Unable to open Telegram", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    @Override
    public void onClick(View v) {
        searchView.setIconified(false);
    }
}