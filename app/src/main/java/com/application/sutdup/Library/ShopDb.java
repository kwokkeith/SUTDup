package com.application.sutdup.Library;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDb extends Database {
    //Editors: Kwok Keith

    public void insertData(String shopName, ArrayList<Integer> followers, String contactDescription,
    ArrayList<Integer> itemsOwned, String shopImage){
        try {
            if( Validator.isSentence(shopName) ){

            }
        }


        Map<String, Object> data = new HashMap<>();
        data.put("shopName", shopName);
        data.put("followers", followers);
        data.put("contactDescription", contactDescription);
        data.put("itemsOwned", itemsOwned);
        data.put("shopImage", shopImage);

        DocumentReference dataRef = insertCollectionGenID(DB_SHOPS_ID, data);


    }
}
