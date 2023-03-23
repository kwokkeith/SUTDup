package com.application.sutdup.Library.ui;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.sutdup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//this is meant to be sell item but im too lazy to change the name for it
public class SellItemActivity extends AppCompatActivity {

    //database reference
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sutdup-a7537-default-rtdb.asia-southeast1.firebasedatabase.app/");

    ArrayList<ShopData> shopDataArrayList;
    ArrayList<UserData> userDataArrayList;
    private String userId;

    private ImageView imageView;
    private String imageString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);

        EditText editTextItemName = findViewById(R.id.editTextItemName);
        EditText editTextItemPrice = findViewById(R.id.editTextItemPrice);
        Button upload = findViewById(R.id.uploaditemBtn);
        imageView = findViewById(R.id.imageView);

        //get login info
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString("userId_key", "");

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
                String newItemKey = databaseReference.child("items").push().getKey();

                if(itemName.isEmpty() || itemPrice.isEmpty() ){
                    Toast.makeText(SellItemActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();}

                else {
                // Do something with these values, e.g., save them to a database
                ShopData shopdata = new ShopData(userId, newItemKey, itemName, itemPrice, imageString);
                databaseReference.child("items").child(newItemKey).setValue(shopdata);

                //to go back to profile page
                Intent intent = new Intent(SellItemActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }}
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
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);

// Set the image to the ImageView
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Encode the default image to a string and save it to the database
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.anya);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }
}
