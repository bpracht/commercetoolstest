package com.pracht.commercetools.etsy.model;
import com.pracht.commercetools.etsy.model.model.EtsySingleListing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EtsySingleListingTest {
    @Test
    public void testCreateEmpty() {
        EtsySingleListing etsySingleListing = new EtsySingleListing();
        assertEquals(0L,etsySingleListing.getId(),"Unexpected Etsy listing ID");
        assertNull(etsySingleListing.getDescription(),"Unexpected description");
    }

    @Test
    public void testCreateWithFluentSetters() {
        EtsySingleListing etsySingleListing = new EtsySingleListing()
                .withDescription("Description")
                .withId(3L);
        assertEquals(3L,etsySingleListing.getId(),"Unexpected Etsy listing ID");
        assertEquals("Description",etsySingleListing.getDescription(),"Unexpected Etsy listing description");

    }

    @Test
    public void testCreateWithNormalAccessors() {
        EtsySingleListing etsySingleListing = new EtsySingleListing();
        etsySingleListing.setDescription("Description");
        etsySingleListing.setId(3L);
        assertEquals(3L,etsySingleListing.getId(),"Unexpected Etsy listing ID");
        assertEquals("Description",etsySingleListing.getDescription(),"Unexpected Etsy listing description");

    }
}
