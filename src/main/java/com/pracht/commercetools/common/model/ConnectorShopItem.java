package com.pracht.commercetools.common.model;

import com.pracht.commercetools.etsy.model.model.EtsySingleListing;

public class ConnectorShopItem {
    private long itemNumber;

    private String itemDescription;

    public long getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(long itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public ConnectorShopItem withItemNumber(long itemNumber) {
        this.itemNumber = itemNumber;
        return this;
    }

    public ConnectorShopItem withItemDescription(String itemDescription){
        this.itemDescription = itemDescription;
        return this;
    }

    public ConnectorShopItem() {

    }

    public ConnectorShopItem(EtsySingleListing etsySingleListing){
        this.itemNumber= etsySingleListing.getId();
    }

}
