package com.application.sutdup.Library;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.text.UStringsKt;

public abstract class Database extends Database_Constants{
    private final FirebaseFirestore db;



    public Database(){
        // To Connect to FirebaseFirestore instance
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    protected DocumentReference insertCollectionGenID(String collectionPath, Map<String, Object> data){
        /**
         * This function inserts the data stored in a Hashmap format into the appropriate
         * collections folder in firebase.
         *
         * @param collectionPath the collection name on firebase to store this data entry
         * @param data the HashMap of the data to be stored (key, value)
         * @return DocumentReference object with the auto-generated id
         */
        DocumentReference newDataRef = db.collection(collectionPath).document();
        newDataRef.set(data);
        return newDataRef;
    }
}