package com.pracht.commercetools.common.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConnectorShopItemTest {

    @Test
    public void testCreateEmptyConnectorShopItem() {
        ConnectorShopItem connectorShopItem = new ConnectorShopItem();
        assertEquals(0L,connectorShopItem.getItemNumber(),"Unexpected item number");
        assertNull(connectorShopItem.getItemDescription(),"Unexpected description");

    }

    @Test
    public void testCreateConnectorShopItemFluentSetters() {
        ConnectorShopItem connectorShopItem = new ConnectorShopItem().withItemDescription("TestItem").withItemNumber(1L);
        assertEquals(1L,connectorShopItem.getItemNumber(),"Unexpected item number");
        assertEquals("TestItem",connectorShopItem.getItemDescription(),"Unexpected description");
    }

    @Test
    public void testCreateConnectorShopItemNormalAccessors() {
        ConnectorShopItem connectorShopItem = new ConnectorShopItem();
        connectorShopItem.setItemNumber(1L);
        connectorShopItem.setItemDescription("TestItem");
        assertEquals(1L,connectorShopItem.getItemNumber(),"Unexpected item number");
        assertEquals("TestItem",connectorShopItem.getItemDescription(),"Unexpected description");
    }

}
