package com.pracht.commercetools.common.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pracht.commercetools.etsy.model.model.EtsyShop;
import com.pracht.commercetools.etsy.model.model.EtsyShopListing;
import com.pracht.commercetools.etsy.model.model.EtsySingleListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectorShop{
    private long shopId;
    private String shopName;
    private Map<Long, ConnectorShopItem> connectorStoreItemMap = new HashMap<>();

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

    public ConnectorShop withShopId(long storeId) {
        this.shopId = storeId;
        return this;
    }

    public ConnectorShop withShopName(String storeName) {
        this.shopName = storeName;
        return this;
    }

    public ConnectorShop withConnectorStoreItem(ConnectorShopItem connectorShopItem){
        this.connectorStoreItemMap.put(connectorShopItem.getItemNumber(), connectorShopItem);
        return this;
    }


    public Map<Long, ConnectorShopItem> getConnectorStoreItems() {
        return this.connectorStoreItemMap;
    }

    public ConnectorShop(){

    }

    public ConnectorShop(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonString);
        if(!jsonTree.isJsonObject()) {
            throw new IllegalArgumentException("Expected ConnectorShop json string to be object but was not");
        }
        JsonObject root = jsonTree.getAsJsonObject();
        if(root.get("shop_name") == null) {
            throw new IllegalArgumentException("Missing shop name from ConnectorShop json file");
        }
        this.shopName=root.get("shop_name").getAsString();

        if(root.get("shop_id") == null) {
            throw new IllegalArgumentException("Missing shop id from ConnectorShop json file");
        }
        this.shopId=root.get("shop_id").getAsLong();

        if(!root.get("items").isJsonArray()) {
            throw new IllegalArgumentException("Expected items field to be an array but was not");
        }
        for(JsonElement listingElement : root.get("items").getAsJsonArray()) {
            JsonObject listingObject = listingElement.getAsJsonObject();
            long itemNumber = listingObject.get("item_number").getAsLong();
            String description = listingObject.get("description").getAsString();
            ConnectorShopItem connectorShopItem= new ConnectorShopItem()
                    .withItemNumber(itemNumber)
                    .withItemDescription(description);
            withConnectorStoreItem(connectorShopItem);
        }
    }

    public String getAsJsonString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append(quotedString("shop_id")+":"+getShopId());
        result.append(","+quotedString("shop_name")+":"+quotedString(getShopName()));
        result.append(","+quotedString("items")+":"+"[");
        result.append(getConnectorStoreItems().values().stream()
                .map(connectorShopItem ->
                        "{"
                        +quotedString("item_number")+":"+connectorShopItem.getItemNumber()
                        +","+quotedString("description")+":"+quotedString(connectorShopItem.getItemDescription())
                        +"}")
                .collect(Collectors.joining(",")));
        result.append("]");
        result.append("}");
        return result.toString();
    }

    private String quotedString(String value){
        return '"' + value +'"';
    }

    public ConnectorShop(EtsyShop etsyShop){
        this.shopName = etsyShop.getShopName();
        this.shopId = etsyShop.getShopId();
    }

    public ConnectorShop(EtsyShop etsyShop, EtsyShopListing etsyShopListing){
        this.shopName = etsyShop.getShopName();
        this.shopId = etsyShop.getShopId();
        for(EtsySingleListing etsySingleListing : etsyShopListing.getListings()) {
            withConnectorStoreItem(new ConnectorShopItem(etsySingleListing));
        }
    }


}
