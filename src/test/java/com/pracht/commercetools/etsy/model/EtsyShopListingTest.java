package com.pracht.commercetools.etsy.model;

import com.pracht.commercetools.etsy.model.model.EtsyShopListing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EtsyShopListingTest {
    @Test
    public void testCreateFromJson() {
        String jsonString = "{\"count\":1,\"results\":[{\"listing_id\":652422002,\"state\":\"active\",\"user_id\":187881002,\"category_id\":68930688,\"title\":\"Custom Shoes\",\"description\":\"Custom Shoe Services and restorations!\",\"creation_tsz\":1544474759,\"ending_tsz\":1554925559,\"original_creation_tsz\":1544474446,\"last_modified_tsz\":1544474759,\"price\":\"85.00\",\"currency_code\":\"USD\",\"quantity\":1,\"sku\":[],\"tags\":[],\"category_path\":[\"Clothing\",\"Shoes\",\"Children\"],\"category_path_ids\":[69150353,68889926,68930688],\"materials\":[],\"shop_section_id\":null,\"featured_rank\":null,\"state_tsz\":1544474447,\"url\":\"https:\\/\\/www.etsy.com\\/listing\\/652422002\\/custom-shoes?utm_source=commercetoolstest&utm_medium=api&utm_campaign=api\",\"views\":0,\"num_favorers\":0,\"shipping_template_id\":null,\"processing_min\":1,\"processing_max\":3,\"who_made\":\"i_did\",\"is_supply\":\"false\",\"when_made\":\"made_to_order\",\"item_weight\":\"80\",\"item_weight_unit\":\"oz\",\"item_length\":\"5\",\"item_width\":\"3\",\"item_height\":\"3\",\"item_dimensions_unit\":\"in\",\"is_private\":false,\"recipient\":null,\"occasion\":null,\"style\":null,\"non_taxable\":false,\"is_customizable\":false,\"is_digital\":false,\"file_data\":\"\",\"should_auto_renew\":true,\"language\":\"en-US\",\"has_variations\":true,\"taxonomy_id\":1511,\"taxonomy_path\":[\"Shoes\",\"Unisex Kids' Shoes\",\"Sneakers & Athletic Shoes\"],\"used_manufacturer\":false}],\"params\":{\"limit\":25,\"offset\":0,\"page\":null,\"shop_id\":\"19112607\",\"keywords\":null,\"sort_on\":\"created\",\"sort_order\":\"down\",\"min_price\":null,\"max_price\":null,\"color\":null,\"color_accuracy\":0,\"tags\":null,\"category\":null,\"translate_keywords\":\"false\",\"include_private\":0},\"type\":\"Listing\",\"pagination\":{\"effective_limit\":25,\"effective_offset\":0,\"next_offset\":null,\"effective_page\":1,\"next_page\":null}}";
        EtsyShopListing actualEtsyShopListing = new EtsyShopListing(jsonString);
        assertNotNull(actualEtsyShopListing.getListings(),"Unexpected null etsy listings");
        assertEquals(1
                ,actualEtsyShopListing.getListings().size()
                ,"Unexpected  etsy listings count");
    }

    @Test
    public void testCreateFromEmpty(){
        EtsyShopListing etsyShopListing = new EtsyShopListing();
        assertNotNull(etsyShopListing.getListings());
        assertEquals(0,etsyShopListing.getListings().size());
    }
}
