package com.pracht.commercetools.etsy.model.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EtsyShopListing {
    private List<EtsySingleListing> listings;
    public EtsyShopListing(){
        listings = new LinkedList<>();

    }
    public EtsyShopListing(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(jsonString);
        if(!jsonTree.isJsonObject()) {
            throw new IllegalArgumentException("Expected EtsyShop json string to be object but was not");
        }
        JsonObject root = jsonTree.getAsJsonObject();
        if(!root.get("results").isJsonArray()) {
            throw new IllegalArgumentException("Expected results field to be an array but was not");
        }

        listings = new ArrayList<>(root.get("count").getAsInt());

        for(JsonElement listingElement : root.get("results").getAsJsonArray()) {
            JsonObject listingObject = listingElement.getAsJsonObject();
            long listingId = listingObject.get("listing_id").getAsLong();
            String description = listingObject.get("description").getAsString();
            EtsySingleListing etsySingleListing = new EtsySingleListing()
                    .withId(listingId)
                    .withDescription(description);
            this.listings.add(etsySingleListing);

        }

    }


    public List<EtsySingleListing> getListings() {
        return listings;
    }
}
