package com.application.sutdup.Library.ui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Database {
    private DatabaseReference databaseReference;

    public Database(){ //no-arg constructor
        this.databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sutdup-a7537-default-rtdb.asia-southeast1.firebasedatabase.app/");
    }

    public Database(DatabaseReference databaseReference) { //arg-constructor
        this.databaseReference = databaseReference;
    }

    public void setDatabaseReference(String path){
        this.databaseReference = FirebaseDatabase.getInstance().getReference(path);;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
