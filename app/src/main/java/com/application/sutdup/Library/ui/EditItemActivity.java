package com.application.sutdup.Library.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sutdup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditItemActivity extends AppCompatActivity {
//random comment
    TextView itemname,itemprice;
    ImageView imageView;
    String imageString = "";

    Button editchangesBtn , deleteitemBtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemname = findViewById(R.id.itemNameet);
        itemprice = findViewById(R.id.itemPriceet);
        imageView = findViewById(R.id.itemImageStringet);
        editchangesBtn = findViewById(R.id.editItemBtn);
        deleteitemBtn = findViewById(R.id.deleteItemBtn);

        /**Getting datas from Profile activity when cardview is clicked to edit an item**/
        Intent intent = getIntent();
        String itemnamedata = intent.getStringExtra("itemname");
        String itempricedata = intent.getStringExtra("itemprice");
        String itemiddata = intent.getStringExtra("itemid");
        String itemimagedata = intent.getStringExtra("itemimage");

        byte[] decodedString = Base64.decode(itemimagedata, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);



        itemname.setText(itemnamedata);
        itemprice.setText(itempricedata);
        imageString = itemimagedata;



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch image picker
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });


        /**Database**/
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
        DatabaseReference reference = database.getDatabaseReference();

        editchangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values entered by the user in these EditText widgets
                String itemName = itemname.getText().toString().trim();
                String itemPrice = itemprice.getText().toString().trim();

                if(itemName.isEmpty() || itemPrice.isEmpty() ){
                    Toast.makeText(EditItemActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child(itemiddata).child("itemName").setValue(itemName);
                    reference.child(itemiddata).child("itemPrice").setValue(itemPrice);
                    reference.child(itemiddata).child("itemImage").setValue(imageString);

                    Toast.makeText(EditItemActivity.this, "Successfully edited", Toast.LENGTH_SHORT).show();
                    //to go back to profile page
                    Intent intent = new Intent(EditItemActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        deleteitemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("items").child(itemiddata);
                itemRef.removeValue();
                Toast.makeText(EditItemActivity.this, "Deleted item dont sad", Toast.LENGTH_SHORT).show();
                //to go back to profile page
                Intent intent = new Intent(EditItemActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();

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
}}