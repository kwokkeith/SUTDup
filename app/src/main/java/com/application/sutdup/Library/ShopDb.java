package com.application.sutdup.Library;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDb extends Database {
    //Editors: Kwok Keith

    public void insertData(String shopName, ArrayList<String> followers, String contactDescription,
    String contactNumber, ArrayList<String> itemsOwned, String shopImage) throws Exception {

        // Data Check Block
        {// Check ShopName
            try {
                Validator.isSentence(shopName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check followers
            try {
                for (String followerID : followers) {
                    Validator.isValidId(followerID);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check contactDescription
            try {
                Validator.lengthCheck(contactDescription, 150);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //Check contactNumber
            try {
                Validator.isSGPhoneNumber(contactNumber);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //Check itemsOwned
            for (String item : itemsOwned) {
                Validator.isValidId(item);
            }

            //Check shopImage
            try {
                if (!Validator.isEmptyBool(shopImage))
                    Validator.isValidImageFile(shopImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("shopName", shopName);
        data.put("followers", followers);
        data.put("contactDescription", contactDescription);
        data.put("itemsOwned", itemsOwned);
        data.put("shopImage", shopImage);

        DocumentReference dataRef = insertCollectionGenID(DB_SHOPS_ID, data);

        //TODO: Update function to update the shopId (dataRef) with the corresponding userId



    }
}
