package com.application.sutdup.Library.ui;

public class ShopData {

    public String userId, itemId;
    public String itemName,itemPrice;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    ShopData(String userId, String itemId ,String itemName, String itemPrice) {
        this.userId = userId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
    }
    ShopData(){}




}
