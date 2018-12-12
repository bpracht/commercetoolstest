package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.ConnectorShopItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectorShopItemChangeLoggerServiceTest {
    @Test
    public void compareEmptyShopItemListings() {
        ConnectorShopItemChangeLoggerService shopChangeLogger = new ConnectorShopItemChangeLoggerService();
        try {
            shopChangeLogger.getShopItemChanges(null, null);
            fail("Expected exception thrown for both old and new null connector shop items");
        } catch(Throwable e) {
            // expected no op
        }
    }

    @Test
    public void compareNewShopItemListing() {
        ConnectorShopItemChangeLoggerService shopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
        ConnectorShopItem newConnectorShopItem = new ConnectorShopItem().withItemNumber(1L).withItemDescription("New Item");
        String changeList = shopItemChangeLogger.getShopItemChanges(null, newConnectorShopItem);
        assertNotNull(changeList,"Unexpected null change list");
        String actualChangeString = changeList;
        String expectedChangeString ="+ added listing 1 \"New Item\"";
        assertEquals(expectedChangeString,actualChangeString,"Unexpected change log for new shop item");
    }

    @Test
    public void compareRemovedShopItemListing() {
        ConnectorShopItemChangeLoggerService shopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
        ConnectorShopItem oldConnectorShopItem = new ConnectorShopItem().withItemNumber(1L).withItemDescription("Old Item");
        String changeList = shopItemChangeLogger.getShopItemChanges(oldConnectorShopItem,null);
        assertNotNull(changeList,"Unexpected null change list");
        String actualChangeString = changeList;
        String expectedChangeString ="- removed listing 1 \"Old Item\"";
        assertEquals(expectedChangeString,actualChangeString,"Unexpected change log for removed shop");
    }

    @Test
    public void compareShopItemListingNameChanged() {
        ConnectorShopItemChangeLoggerService shopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
        ConnectorShopItem oldConnectorShopItem = new ConnectorShopItem().withItemNumber(1L).withItemDescription("Old Item");
        ConnectorShopItem newConnectorShopItem = new ConnectorShopItem().withItemNumber(1L).withItemDescription("New Item");
        String changeList = shopItemChangeLogger.getShopItemChanges(oldConnectorShopItem,newConnectorShopItem);
        assertNotNull(changeList,"Unexpected null change list");
        String actualChangeString = changeList;
        String expectedChangeString ="changed listing 1 from \"Old Item\" to \"New Item\"";
        assertEquals(expectedChangeString,actualChangeString,"Unexpected change log for removed shop");
    }

    @Test
    public void compareShopItemListingNameNotChanged() {
        ConnectorShopItemChangeLoggerService shopItemChangeLogger = new ConnectorShopItemChangeLoggerService();
        ConnectorShopItem oldConnectorShopItem = new ConnectorShopItem().withItemNumber(1L).withItemDescription("Same Item");
        ConnectorShopItem newConnectorShopItem = new ConnectorShopItem().withItemNumber(1L).withItemDescription("Same Item");
        String changeList = shopItemChangeLogger.getShopItemChanges(oldConnectorShopItem,newConnectorShopItem);
        assertNull(changeList,"Unexpected null change list");
    }


}
