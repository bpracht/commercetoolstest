package com.pracht.commercetools;

import com.pracht.commercetools.common.model.Arguments;
import com.pracht.commercetools.common.model.ConnectorShop;
import com.pracht.commercetools.common.service.ArgumentParsingService;
import com.pracht.commercetools.common.service.ConnectorShopChangeLoggerService;
import com.pracht.commercetools.common.service.ConnectorShopFileService;
import com.pracht.commercetools.etsy.model.model.EtsyShop;
import com.pracht.commercetools.etsy.model.model.EtsyShopListing;
import com.pracht.commercetools.etsy.model.service.EtsyService;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String args[]) throws IOException, InterruptedException {
        ArgumentParsingService argumentParsingService = new ArgumentParsingService();
        Arguments arguments = argumentParsingService.parseArguments(args);
        if (arguments.isHelpRequested()) {
            System.out.println("Comparison tool.  Compare retrieval of etsy shops with subsequent ones");
            System.out.println("--directory <existing directory to place downloaded shop files>");
            System.out.println("--shopIds <space separated list of shop ids>");
            System.out.println("--help <prints this message>");
            System.exit(0);
        }

        log.info("Shop Directory {}", arguments.getShopDirectory());
        log.info("Shop Ids {}", arguments
                .getShopIds()
                .stream()
                .map(shopId -> (String) "" + shopId)
                .collect(Collectors.joining(",")));


        List<Long> shopIds = arguments.getShopIds();


        /**
         * Read previous ConnectorShops from disk
         */
        ConnectorShopFileService connectorShopFileService = new ConnectorShopFileService();
        Map<Long, ConnectorShop> oldConnectorShops = connectorShopFileService.getConnectorShops(arguments.getShopDirectory());


        /**
         * Fetch new EtsyShops from Etsy service
         */
        EtsyService etsyService = new EtsyService();
        /**
         * Get the details of the shop, limited to the shop name right now
         */
        Map<Long, EtsyShop> etsyShops = etsyService.getEtsyShops(shopIds);
        /**
         * Get listing of all items within a specific Etsy shop.
         */
        Map<Long, EtsyShopListing> etsyShopListings;

        etsyShopListings = etsyService.getListingsFromSpecificShops(shopIds);

        /**
         * Take Etsy Shop and EtsyShopListing and normalize them into ConnectorShop objects.
         */
        Map<Long, ConnectorShop> newConnectorShops = new HashMap<Long, ConnectorShop>();


        newConnectorShops = shopIds.stream().map(shopId -> {
            EtsyShop etsyShop = etsyShops.get(shopId);
            EtsyShopListing etsyShopListing = etsyShopListings.get(shopId);
            return new ConnectorShop(etsyShop, etsyShopListing);
        }).collect(Collectors.toMap(connectorShop -> connectorShop.getShopId(), connectorShop -> connectorShop));

        /**
         * Save the new files to disk
         */
        List<ConnectorShop> savedConnectorShops = new LinkedList<>();
        savedConnectorShops.addAll(newConnectorShops.values());

        connectorShopFileService.writeConnectorShops(arguments.getShopDirectory(),savedConnectorShops);


        /**
         * Compare the changes for each of the specified shops
         */
        ConnectorShopChangeLoggerService connectorShopChangeLoggerService = new ConnectorShopChangeLoggerService();
        for (Long shopId : shopIds) {
            ConnectorShop oldConnectorShop = oldConnectorShops.get(shopId);
            ConnectorShop newConnectorShop = newConnectorShops.get(shopId);
            System.out.println("Shop ID " + shopId);
            List<String> changes = connectorShopChangeLoggerService.getShopChanges(oldConnectorShop, newConnectorShop);
            for(String change : changes) {
                System.out.println(change);
            }
        }

        System.exit(0);


    }
}
