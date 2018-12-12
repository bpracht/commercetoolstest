package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.ConnectorShop;
import com.pracht.commercetools.common.model.ConnectorShopItem;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ConnectorShopChangeLoggerService extends ChangeLoggerService {
    public List<String> getShopChanges(ConnectorShop oldShop, ConnectorShop newShop) {
        List<String> result = new LinkedList<>();
        if(oldShop==null && newShop == null) {
            throw new IllegalArgumentException("Both old and new ConnectorShop objects are null");
        } else if(oldShop == null && newShop != null) {
            ConnectorShopItemChangeLoggerService connectorShopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
            result.add("Adding new shop id "+newShop.getShopId()+" name "+quotedDescription(newShop.getShopName()));
        } else if(oldShop != null && newShop == null) {
            ConnectorShopItemChangeLoggerService connectorShopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
            result.add("Removing shop id "+oldShop.getShopId()+" name "+quotedDescription(oldShop.getShopName()));
        } else {
            if(!oldShop.getShopName().equals(newShop.getShopName())) {
                result.add("Shop id "+oldShop.getShopId()+" changed names from "
                        +quotedDescription(oldShop.getShopName())
                        +" to "
                        +quotedDescription(newShop.getShopName()));
            }

        }
        result.addAll(compareAllShopItems(oldShop,newShop));
        return result;
                
    }

    private List<String> compareAllShopItems(ConnectorShop oldShop, ConnectorShop newShop) {
        List<String> result = new LinkedList<>();
        if(oldShop==null && newShop == null) {
            throw new IllegalArgumentException("Both old and new ConnectorShop objects are null");
        } else if(oldShop == null && newShop != null) {
            ConnectorShopItemChangeLoggerService connectorShopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
            newShop.getConnectorStoreItems()
                    .values()
                    .stream()
                    .forEach(storeItem ->result.add(connectorShopItemChangeLogger.getShopItemChanges(null,storeItem)));
        } else if(oldShop != null && newShop == null) {
            ConnectorShopItemChangeLoggerService connectorShopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
            oldShop.getConnectorStoreItems()
                    .values()
                    .stream()
                    .forEach(storeItem ->result.add(connectorShopItemChangeLogger.getShopItemChanges(storeItem,null)));
        } else {
            ConnectorShopItemChangeLoggerService connectorShopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
            Stream<Long> oldShopItemNumberStream = oldShop.getConnectorStoreItems().keySet().stream();
            Stream<Long> newShopItemNumberStream = newShop.getConnectorStoreItems().keySet().stream();
            Stream<Long> itemNumberStream = Stream.of(oldShopItemNumberStream,newShopItemNumberStream).flatMap(i->i);
            itemNumberStream.distinct().forEach(storeItemNumber -> {
                ConnectorShopItem oldItem = oldShop.getConnectorStoreItems().get(storeItemNumber);
                ConnectorShopItem newItem = newShop.getConnectorStoreItems().get(storeItemNumber);
                result.add(connectorShopItemChangeLogger.getShopItemChanges(oldItem,newItem));
            });
            

        }
        return result;

    }
}
