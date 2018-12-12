package com.pracht.commercetools.etsy.model;

import com.pracht.commercetools.etsy.model.model.EtsyShop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EtsyShopTest {
    @Test
    public void testCreateFromJson() {
        String jsonString = "{\"count\":1,\"results\":[{\"shop_id\":19112607,\"shop_name\":\"Ohhlaladesignss\",\"user_id\":187881002,\"creation_tsz\":1544474759,\"title\":null,\"announcement\":null,\"currency_code\":\"USD\",\"is_vacation\":false,\"vacation_message\":null,\"sale_message\":null,\"digital_sale_message\":null,\"last_updated_tsz\":1544474759,\"listing_active_count\":1,\"digital_listing_count\":0,\"login_name\":\"ohhlaladesignss\",\"accepts_custom_requests\":false,\"policy_welcome\":null,\"policy_payment\":null,\"policy_shipping\":null,\"policy_refunds\":null,\"policy_additional\":null,\"policy_seller_info\":null,\"policy_updated_tsz\":0,\"policy_has_private_receipt_info\":false,\"vacation_autoreply\":null,\"url\":\"https:\\/\\/www.etsy.com\\/shop\\/Ohhlaladesignss?utm_source=commercetoolstest&utm_medium=api&utm_campaign=api\",\"image_url_760x100\":null,\"num_favorers\":0,\"languages\":[\"en-US\"],\"upcoming_local_event_id\":null,\"icon_url_fullxfull\":null,\"is_using_structured_policies\":false,\"has_onboarded_structured_policies\":false,\"has_unstructured_policies\":false,\"include_dispute_form_link\":false,\"is_direct_checkout_onboarded\":true,\"policy_privacy\":null,\"is_calculated_eligible\":true}],\"params\":{\"shop_id\":\"19112607\"},\"type\":\"Shop\",\"pagination\":{}}";
        EtsyShop actualEtsyShop = new EtsyShop(jsonString);
        assertEquals(19112607,actualEtsyShop.getShopId(),"Unexpected shop id");
        assertEquals("Ohhlaladesignss",actualEtsyShop.getShopName(),"Unexpected shop name");
    }
}
