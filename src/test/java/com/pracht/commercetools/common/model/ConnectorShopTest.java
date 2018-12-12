package com.pracht.commercetools.common.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConnectorShopTest {

    @Test
    public void testCreateEmptyConnectorShop() {
        ConnectorShop connectorShop = new ConnectorShop();
        assertEquals(0L,connectorShop.getShopId(),"Unexpected shopId");
        assertNull(connectorShop.getShopName(),"Unexpected shopName");
        assertEquals(0L,connectorShop.getConnectorStoreItems().size());
    }

    @Test
    public void testCreateConnectorShopWithFluentSetters() {
        ConnectorShop connectorShop = new ConnectorShop().withShopId(33L).withShopName("TestName");
        assertEquals(33L,connectorShop.getShopId(),"Unexpected shopId");
        assertEquals("TestName",connectorShop.getShopName(),"Unexpected shopName");
        assertEquals(0L,connectorShop.getConnectorStoreItems().size());
        connectorShop.withConnectorStoreItem(new ConnectorShopItem().withItemDescription("NewItem").withItemNumber(1L));
        assertEquals(1L,connectorShop.getConnectorStoreItems().size());
    }

    @Test
    public void testCreateConnectorShopWithNormalAccessors() {
        ConnectorShop connectorShop = new ConnectorShop();
        connectorShop.setShopId(33L);
        connectorShop.setShopName("TestName");
        assertEquals(33L,connectorShop.getShopId(),"Unexpected shopId");
        assertEquals("TestName",connectorShop.getShopName(),"Unexpected shopName");
        assertEquals(0L,connectorShop.getConnectorStoreItems().size());
    }

    @Test
    public void testGetJsonString() {
        ConnectorShop connectorShop = new ConnectorShop()
                .withShopId(33L)
                .withShopName("TestName")
                .withConnectorStoreItem(new ConnectorShopItem().withItemDescription("Item1").withItemNumber(1L))
                .withConnectorStoreItem(new ConnectorShopItem().withItemDescription("Item2").withItemNumber(2L));
        String jsonString = connectorShop.getAsJsonString();
        assertEquals("{\"shop_id\":33,\"shop_name\":\"TestName\",\"items\":[{\"item_number\":1,\"description\":\"Item1\"},{\"item_number\":2,\"description\":\"Item2\"}]}",jsonString,"Unexpected json string");
        ConnectorShop reconstructedShop = new ConnectorShop(jsonString);
        assertNotNull(reconstructedShop,"Unexpected null reconstructedShop");
        assertEquals("TestName",reconstructedShop.getShopName(),"Unexpected shop name");
        assertEquals(33L,reconstructedShop.getShopId(),"Unexpected shop Id");
        assertEquals(2,reconstructedShop.getConnectorStoreItems().size(),"Unexpected number of store items found on reconstruction");

    }

}
