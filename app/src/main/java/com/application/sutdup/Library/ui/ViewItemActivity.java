package com.application.sutdup.Library.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sutdup.R;

public class ViewItemActivity extends AppCompatActivity {

    TextView itemname,itemprice, itemdescription;
    ImageView imageView;
    String imageString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        itemname = findViewById(R.id.itemNamevt);
        itemprice = findViewById(R.id.itemPricevt);
        itemdescription = findViewById(R.id.itemDescriptionvt);
        imageView = findViewById(R.id.itemImageStringvt);
        Button iwanttobuyBtn = findViewById(R.id.iwanttobuyBtn);


        /**Getting datas from Profile activity when cardview is clicked to edit an item**/
        Intent intent = getIntent();
        String itemnamedata = intent.getStringExtra("itemname");
        String itempricedata = intent.getStringExtra("itemprice");
        String itemiddata = intent.getStringExtra("itemid");
        String itemimagedata = intent.getStringExtra("itemimage");
        String itemdescriptiondata = intent.getStringExtra("itemdescription");
        String sellertelehandledata = intent.getStringExtra("sellertelehandle");

        byte[] decodedString = Base64.decode(itemimagedata, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);



        itemname.setText(itemnamedata);
        itemprice.setText(itempricedata);
        itemdescription.setText(itemdescriptiondata);
        imageString = itemimagedata;


        iwanttobuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent telegramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/"+sellertelehandledata));
                telegramIntent.setPackage("org.telegram.messenger");

                startActivity(telegramIntent);
            }
        });
    }
}