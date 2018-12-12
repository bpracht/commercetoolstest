package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.ConnectorShop;
import com.pracht.commercetools.common.model.ConnectorShopItem;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectorShopChangeLoggerServiceTest {
    @Test
    public void compareEmptyShopListings() {
        ConnectorShopChangeLoggerService shopChangeLogger = new ConnectorShopChangeLoggerService();
        try {
            shopChangeLogger.getShopChanges(null, null);
            fail("Expected exception thrown for both old and new null connector shops");
        } catch(Throwable e) {
            // expected no op
        }
    }

    @Test
    public void compareNewShopListing() {
        ConnectorShopChangeLoggerService shopChangeLogger = new ConnectorShopChangeLoggerService();
        ConnectorShop newConnectorShop = new ConnectorShop().withShopId(1).withShopName("New Name");
        List<String> changeList = shopChangeLogger.getShopChanges(null, newConnectorShop);
        assertNotNull(changeList,"Unexpected null change list");
        assertEquals(1L,changeList.size(),"Unexpected change list size");
        String actualChangeString = changeList.get(0);
        assertEquals("Adding new shop id 1 name \"New Name\"",actualChangeString,"Unexpected change log for new shop");
    }

    @Test
    public void compareRemovedShopListing() {
        ConnectorShopChangeLoggerService shopChangeLogger = new ConnectorShopChangeLoggerService();
        ConnectorShop oldConnectorShop = new ConnectorShop().withShopId(1).withShopName("Old Name");
        List<String> changeList = shopChangeLogger.getShopChanges(oldConnectorShop,null);
        assertNotNull(changeList,"Unexpected null change list");
        assertEquals(1L,changeList.size(),"Unexpected change list size");
        String actualChangeString = changeList.get(0);
        assertEquals("Removing shop id 1 name \"Old Name\"",actualChangeString,"Unexpected change log for removed shop");
    }

    @Test
    public void compareShopListingNameChanged() {
        ConnectorShopChangeLoggerService shopChangeLogger = new ConnectorShopChangeLoggerService();
        ConnectorShop oldConnectorShop = new ConnectorShop().withShopId(1).withShopName("Old Name");
        ConnectorShop newConnectorShop = new ConnectorShop().withShopId(1).withShopName("New Name");
        List<String> changeList = shopChangeLogger.getShopChanges(oldConnectorShop,newConnectorShop);
        assertNotNull(changeList,"Unexpected null change list");
        assertEquals(1L,changeList.size(),"Unexpected change list size");
        String actualChangeString = changeList.get(0);
        assertEquals("Shop id 1 changed names from \"Old Name\" to \"New Name\"",actualChangeString,"Unexpected change log for removed shop");
    }

    @Test
    public void compareShopListingNameNotChanged() {
        ConnectorShopChangeLoggerService shopChangeLogger = new ConnectorShopChangeLoggerService();
        ConnectorShop oldConnectorShop = new ConnectorShop().withShopId(1).withShopName("Same Name");
        ConnectorShop newConnectorShop = new ConnectorShop().withShopId(1).withShopName("Same Name");
        List<String> changeList = shopChangeLogger.getShopChanges(oldConnectorShop,newConnectorShop);
        assertNotNull(changeList,"Unexpected null change list");
        assertEquals(0L,changeList.size(),"Unexpected change list size");
    }

    @Test
    public void compareShopListingMultipleItems() {
        ConnectorShopChangeLoggerService shopChangeLogger = new ConnectorShopChangeLoggerService();
        ConnectorShop oldConnectorShop = new ConnectorShop().withShopId(1).withShopName("Shop Name")
                .withConnectorStoreItem(new ConnectorShopItem().withItemNumber(1L).withItemDescription("Old Item"))
                .withConnectorStoreItem(new ConnectorShopItem().withItemNumber(2L).withItemDescription("Removed Item"));

        ConnectorShop newConnectorShop = new ConnectorShop().withShopId(1).withShopName("Shop Name")
                .withConnectorStoreItem(new ConnectorShopItem().withItemNumber(1L).withItemDescription("Changed item name"))
                .withConnectorStoreItem(new ConnectorShopItem().withItemNumber(3L).withItemDescription("New Item"));

        List<String> expectedChangeList = Arrays.asList(new String[]{
                "- removed listing 2 \"Removed Item\"",
                "+ added listing 3 \"New Item\"",
                "changed listing 1 from \"Old Item\" to \"Changed item name\""
        }).stream().sorted().collect(Collectors.toList());

        List<String> actualChangeList = shopChangeLogger.getShopChanges(oldConnectorShop,newConnectorShop).stream().sorted().collect(Collectors.toList());
        assertNotNull(actualChangeList,"Unexpected null change list");
        assertEquals(expectedChangeList,actualChangeList,"Change lines to not equal");
    }
}
