package com.pracht.commercetools.etsy.model.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EtsyShop {
    private long shopId;
    private String shopName;

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
    public EtsyShop withShopId(long shopId) {
        this.shopId = shopId;
        return this;
    }

    public EtsyShop withShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    /**
     * Construct EtsyShop object from JSON String
     * @param jsonString
     */
    public EtsyShop(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonString);
        if(!jsonTree.isJsonObject()) {
            throw new IllegalArgumentException("Expected EtsyShop json string to be object but was not");
        }
        JsonObject root = jsonTree.getAsJsonObject();
        if(!root.get("results").isJsonArray()) {
            throw new IllegalArgumentException("Expected results field to be an array but was not");
        }

        JsonElement resultEntry = root.get("results").getAsJsonArray().get(0);
        JsonObject resultSubobject = resultEntry.getAsJsonObject();
        setShopId(resultSubobject.get("shop_id").getAsLong());
        setShopName(resultSubobject.get("shop_name").getAsString());
    }



}
