package com.application.sutdup.Library.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sutdup.Library.PasswordEncryption;
import com.application.sutdup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //Editors : Sufi
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sutdup-a7537-default-rtdb.asia-southeast1.firebasedatabase.app/");

    //Shared preference
    SharedPreferences sharedpreferences;
    private TextView TriesLeft;
    private int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        EditText phone = findViewById(R.id.phone);
        EditText password = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        TriesLeft = findViewById(R.id.triesLeft);
        TriesLeft.setText("No. of attempts remaining: 5");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String encrypted = PasswordEncryption.encryptAndEncode("123");

                String phoneTxt = phone.getText().toString();
                String passwordTxt = password.getText().toString();

                String PasswordEncrypted = PasswordEncryption.encryptAndEncode(password.getText().toString());

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your details", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(phoneTxt)){

                                String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                String name = snapshot.child(phoneTxt).child("name").getValue(String.class);

                                if(getPassword.equals(PasswordEncrypted)){
                                    Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                    Intent pass = new Intent(LoginActivity.this, HomeActivity.class);

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("name_key", snapshot.child(phoneTxt).child("name").getValue(String.class));
                                    editor.putString("userId_key", phoneTxt);
                                    editor.apply();
                                    startActivity(pass);
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    counter--;
                                    TriesLeft.setText("No. of attempts remaining: "+counter);
                                }

                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Wrong mobile no", Toast.LENGTH_SHORT).show();
                                counter--;
                                TriesLeft.setText("No. of attempts remaining: "+counter);
                            }
                            if(counter ==0){
                                loginBtn.setEnabled(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}