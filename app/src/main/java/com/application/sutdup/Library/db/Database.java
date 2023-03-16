package com.application.sutdup.Library.db;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public abstract class Database extends Database_Constants{
    // Editors: Kwok Keith

    private final FirebaseFirestore db;

    public Database(){
        // To Connect to FirebaseFirestore instance
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    /**
     * This function inserts the data stored in a Hashmap format into the appropriate
     * collections folder in firebase, it will generate automatically an ID for its document
     * name.
     *
     * @param collectionPath the collection name on firebase to store this data entry
     * @param data the HashMap of the data to be stored (key, value)
     * @return DocumentReference object with the auto-generated id
     */
    protected DocumentReference insertCollectionGenID(String collectionPath,
                                                      Map<String, Object> data){

        DocumentReference newDataRef = db.collection(collectionPath).document();
        newDataRef.set(data);
        return newDataRef;
    }

    /**
     * This function inserts the data stored in a Hashmap format into the appropriate
     * collections folder in firebase,
     *
     * @param collectionPath the collection name on firebase to store this data entry
     * @param Id the Id <String> used as the ID for the firebase document.
     * @param data the HashMap of the data to be stored (key, value)
     * @return null
     */
    protected void insertCollection(String collectionPath, String Id, Map<String, Object> data){


    db.collection(collectionPath).document(Id).set(data);

    }
}