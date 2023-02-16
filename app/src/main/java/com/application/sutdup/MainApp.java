package com.application.sutdup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.application.sutdup.Library.Database;

public class MainApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // TODO: To set layout of UI
        //setContentView(R.layout.activity_main);

        Database.main();
    }
}

