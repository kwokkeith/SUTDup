package com.application.sutdup.Library.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.sutdup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SellItemActivity extends AppCompatActivity {

    //database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sutdup-a7537-default-rtdb.asia-southeast1.firebasedatabase.app/");

    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    private String userId;

    private ImageView imageView;
    private String imageString = null;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);

        EditText editTextItemName = findViewById(R.id.editTextItemName);
        EditText editTextItemPrice = findViewById(R.id.editTextItemPrice);
        EditText editTextItemDescription = findViewById(R.id.editTextItemDescription);
        Button upload = findViewById(R.id.uploaditemBtn);
        imageView = findViewById(R.id.imageView);

        //get login info
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString("userId_key", "");


        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.anya);
        ByteArrayOutputStream defaultByteArrayOutputStream = new ByteArrayOutputStream();
        defaultBitmap.compress(Bitmap.CompressFormat.JPEG, 10, defaultByteArrayOutputStream); //suppressed some red lines so if it crashes blame this line -zh
        byte[] defaultByteArray = defaultByteArrayOutputStream.toByteArray();
        imageString = Base64.encodeToString(defaultByteArray, Base64.DEFAULT);
        imageView.setImageBitmap(defaultBitmap);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch image picker
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the values entered by the user in these EditText widgets
                String itemName = editTextItemName.getText().toString().trim();
                String itemPrice = editTextItemPrice.getText().toString().trim();
                String itemDescription = editTextItemDescription.getText().toString().trim();
                String newItemKey = databaseReference.child("items").push().getKey();

                DatabaseReference userRef = databaseReference.child("users").child(userId);

                // Retrieve the value of the telehandle for the current user and store in sellerTelehandle
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String telehandle = dataSnapshot.child("telehandle").getValue().toString();

                            if(itemName.isEmpty() || itemPrice.isEmpty() ){
                                Toast.makeText(SellItemActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                            } else {
                                // Do something with these values, e.g., save them to a database
                                ShopData shopdata = new ShopData(userId, newItemKey, itemName, itemPrice, imageString, itemDescription, telehandle);
                                databaseReference.child("items").child(newItemKey).setValue(shopdata);

                                //to go back to profile page
                                Intent intent = new Intent(SellItemActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image URI
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ExifInterface exif = new ExifInterface(getContentResolver().openInputStream(imageUri));
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Matrix matrix = new Matrix();
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.setRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.setRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.setRotate(270);
                        break;
                    default:
                        // Do nothing
                }
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);

// Set the image to the ImageView
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
