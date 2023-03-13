package com.application.sutdup.Library.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDb extends Database {
    // Editors: Kwok Keith

    public void insertData(String userId, String password, String userName,
                           ArrayList<String> shopOwned,
                           ArrayList<String> following, String userImage)
            throws Exception {

        // Data Check Block
        {// Check userID
            try {
                Validator.isWord(userId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check password
            try {
                Validator.isPassword(password);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check userName
            try {
                Validator.isSentence(userName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check shopOwned
            try {
                for (String shopOwn : shopOwned) {
                    Validator.isValidId(shopOwn);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check following
            try {
                for (String follow : following) {
                    Validator.isValidId(follow);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Check userImage
            try{
                Validator.isValidImageFile(userImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("password", password);
        data.put("userName", userName);
        data.put("shopOwned", shopOwned);
        data.put("following", following);
        data.put("userImage", userImage);

        insertCollection(DB_USERS_ID, userId, data);
    }

}
