package com.pracht.commercetools.common.service;
import com.pracht.commercetools.common.model.ConnectorShop;
import com.pracht.commercetools.common.model.ConnectorShopItem;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectorShopFileServiceTest {
    @Test
    public void writeAndReadConnector() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        ConnectorShopFileService connectorShopFileService = new ConnectorShopFileService();

        ConnectorShop connectorShop = new ConnectorShop()
                .withShopId(33L)
                .withShopName("TestName")
                .withConnectorStoreItem(new ConnectorShopItem().withItemDescription("Item1").withItemNumber(1L))
                .withConnectorStoreItem(new ConnectorShopItem().withItemDescription("Item2").withItemNumber(2L));
        List<ConnectorShop> shops = new LinkedList<>();
        shops.add(connectorShop);
        connectorShopFileService.writeConnectorShops(tempDirectory,shops);

        Map<Long,ConnectorShop> reconstructed = connectorShopFileService.getConnectorShops(tempDirectory);
        assertNotNull(reconstructed);
        assertEquals(1,reconstructed.size());

    }
}
