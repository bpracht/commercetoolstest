package com.pracht.commercetools.etsy.service;

import com.pracht.commercetools.etsy.model.model.EtsyShopListing;
import com.pracht.commercetools.etsy.model.model.EtsyShop;
import com.pracht.commercetools.etsy.model.service.EtsyService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EtsyServiceTest {

    @Test
    public void getEtsyShops() throws InterruptedException, IOException {
        Map<Long,EtsyShop> shops = new EtsyService().getEtsyShops(Arrays.asList(new Long[]{19112607L}));
        assertNotNull(shops, "Unexpected null shops");
        assertEquals(1,shops.size(),"Unexpected size of shops list");
        EtsyShop shop = shops.get(19112607L);
        assertEquals(19112607L,shop.getShopId(),"Unexpected shop id");
        assertEquals("Ohhlaladesignss",shop.getShopName(),"Unexpected shop name");
    }

    @Test
    public void getEtsyShopListing() throws InterruptedException, IOException {
        Map<Long,EtsyShopListing> shopListings = new EtsyService().getListingsFromSpecificShops(Arrays.asList(new Long[]{19112607L}));
        assertNotNull(shopListings, "Unexpected null shopListings");
        assertEquals(1,shopListings .size(),"Unexpected size of shopListings");
        EtsyShopListing shopListing = shopListings.get(19112607L);
        assertNotNull(shopListing,"Did not find expected shop listing");
        assertEquals(1,shopListing.getListings().size(),"Unexpected size of shop listings");
    }

}
