package com.application.sutdup.Library.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sutdup.R;
import com.google.firebase.database.DatabaseReference;

public class EditItemActivity extends AppCompatActivity {

    TextView itemname,itemprice;
    Button editchangesBtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemname = findViewById(R.id.itemNameet);
        itemprice = findViewById(R.id.itemPriceet);
        editchangesBtn = findViewById(R.id.editItemBtn);

        /**Getting datas from Profile activity when cardview is clicked to edit an item**/
        Intent intent = getIntent();
        String itemnamedata = intent.getStringExtra("itemname");
        String itempricedata = intent.getStringExtra("itemprice");
        String itemiddata = intent.getStringExtra("itemid");

        itemname.setText(itemnamedata);
        itemprice.setText(itempricedata);

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

                    Toast.makeText(EditItemActivity.this, "Successfully edited", Toast.LENGTH_SHORT).show();
                    //to go back to profile page
                    Intent intent = new Intent(EditItemActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}