package com.alpersemerci.trendyol.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CampaignTest {

    @Test
    public void test_campaign_applicable_to_product_category() {
        Category category = new Category("Bakery");
        Campaign campaign = new Campaign(category, 10.0, 20, DiscountType.AMOUNT);

        assertEquals(category, campaign.getCategory());
        assertEquals(Double.valueOf(10.0), campaign.getDiscount());
        assertEquals(Integer.valueOf(20), campaign.getDiscountLimit());
        assertEquals(DiscountType.AMOUNT, campaign.getDiscountType());
    }

}