package com.application.sutdup.Library.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.sutdup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    //database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sutdup-a7537-default-rtdb.asia-southeast1.firebasedatabase.app/");

    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText editTextItemName = findViewById(R.id.editTextItemName);
        EditText editTextItemPrice = findViewById(R.id.editTextItemPrice);
        Button upload = findViewById(R.id.uploaditemBtn);

        //get login info
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString("userId_key", "");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        // Retrieve the values entered by the user in these EditText widgets
        String itemName = editTextItemName.getText().toString().trim();
        String itemPrice = editTextItemPrice.getText().toString().trim();
        String newItemKey = databaseReference.child("items").push().getKey();

        // Do something with these values, e.g., save them to a database
        ShopData shopdata = new ShopData(userId, newItemKey, itemName, itemPrice);
        databaseReference.child("items").child(newItemKey).setValue(shopdata);

        //to go back to profile page
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();


    }
});
    };
}