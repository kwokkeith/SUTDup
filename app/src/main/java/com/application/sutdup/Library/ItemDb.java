package com.application.sutdup.Library;

import com.google.firebase.firestore.DocumentReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class ItemDb extends Database{
    //Editors: Kwok Keith

    public void insertData(String itemId, String itemName, String itemDescription,
                           BigDecimal price, String itemImage) throws Exception {

        // Data Check Block
        {// Check itemId
            try {
                Validator.isValidId(itemId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check itemName
            try {
                Validator.lengthCheck(itemName, 50);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check itemDescription
            try {
                Validator.lengthCheck(itemDescription, 100);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check price
            try {
                // Compare to check if BigDecimal Price is negative
                if (price.compareTo(new BigDecimal(0)) < 0){
                    throw new InputMismatchException("Item price cannot be less than 0");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("itemId", itemId);
        data.put("itemName", itemName);
        data.put("itemDescription", itemDescription);
        data.put("price", price);
        data.put("itemImage", itemImage);

        DocumentReference dataRef = insertCollectionGenID(DB_ITEMS_ID, data);

        //TODO: Update function to update the itemID (dataRef) with the corresponding shopId



    }

}
