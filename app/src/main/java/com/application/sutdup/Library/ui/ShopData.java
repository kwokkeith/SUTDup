package com.application.sutdup.Library.ui;

public class ShopData {

    public String userId, itemId, key, itemImage, itemName,itemPrice, itemDescription, sellerTelehandle;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getSellerTelehandle() {
        return sellerTelehandle;
    }

    public void setSellerTelehandle(String sellerTelehandle) {
        this.sellerTelehandle = sellerTelehandle;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    ShopData(String userId, String itemId , String itemName, String itemPrice, String itemImage, String itemDescription, String sellerTelehandle) {
        this.userId = userId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.itemDescription = itemDescription;
        this.sellerTelehandle = sellerTelehandle;
    }
    ShopData(){}




}
