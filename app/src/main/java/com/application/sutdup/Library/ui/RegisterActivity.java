package com.application.sutdup.Library.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.sutdup.Library.PasswordEncryption;
import com.application.sutdup.Library.Validators;
import com.application.sutdup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    //Editors : Sufi

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sutdup-a7537-default-rtdb.asia-southeast1.firebasedatabase.app/");
    String NumberRegex= ".*[0-9].*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        EditText phone = findViewById(R.id.phone);
        EditText telehandle = findViewById(R.id.telehandle);
        Button register = findViewById(R.id.registerBtn);
        EditText password = findViewById(R.id.password);
        EditText confirmPass = findViewById(R.id.confirmPassword);
        TextView loginNowBtn = findViewById(R.id.loginNowBtn);
        EditText name = findViewById(R.id.name);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneTxt = phone.getText().toString();
                //String passwordTxt = password.getText().toString();
                //String conPasswordTxt = confirmPass.getText().toString();
                String passwordTxt = PasswordEncryption.encryptAndEncode(password.getText().toString());
                String conPasswordTxt = PasswordEncryption.encryptAndEncode(confirmPass.getText().toString());
                String nameTxt = name.getText().toString();
                String telehandleTxt = telehandle.getText().toString();

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty() || telehandleTxt.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                if (!passwordTxt.equals(conPasswordTxt)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                if (!Validators.lengthCheck(passwordTxt)) {
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                if (!Validators.isSGPhoneNumber(phoneTxt)){
                    Toast.makeText(RegisterActivity.this, "Input a valid SG phone number", Toast.LENGTH_SHORT).show();
                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(phoneTxt)){
                                Toast.makeText(RegisterActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{

                                UserData userData = new UserData(nameTxt,passwordTxt,phoneTxt,telehandleTxt);
                                databaseReference.child("users").child(phoneTxt).setValue(userData);
                                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }



            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}