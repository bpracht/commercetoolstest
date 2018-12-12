package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.ConnectorShopItem;

public class ConnectorShopItemChangeLoggerService extends ChangeLoggerService {

    public String getShopItemChanges(ConnectorShopItem oldShopItem, ConnectorShopItem newShopItem) {
        System.out.println("getShopItemChanges");
        String result = null;
                ;
        if(oldShopItem==null && newShopItem == null) {
            throw new IllegalArgumentException("Both old and new ConnectorShopItem objects are null");
        } else if(oldShopItem == null && newShopItem != null) {
            result = "+ added listing "+newShopItem.getItemNumber()+" "+quotedDescription(newShopItem.getItemDescription());
        } else if(oldShopItem != null && newShopItem == null) {
            result = "- removed listing "+oldShopItem.getItemNumber()+" "+quotedDescription(oldShopItem.getItemDescription());
        } else {
            if(!oldShopItem.getItemDescription().equals(newShopItem.getItemDescription())) {
                result = "changed listing "
                        +oldShopItem.getItemNumber()
                        +" from "
                        +quotedDescription(oldShopItem.getItemDescription())
                        +" to "
                        +quotedDescription(newShopItem.getItemDescription());
            }

        }
        return result;

    }

}
