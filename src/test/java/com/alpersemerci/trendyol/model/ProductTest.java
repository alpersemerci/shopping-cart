package com.alpersemerci.trendyol.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void test_product_has_title_price_and_category() {
        Category category = new Category("Food");
        Product apple = new Product("Apple", 100.0, category);
        assertEquals("Apple", apple.getTitle());
        assertEquals(Double.valueOf(100.0), apple.getPrice());
    }

    @Test
    public void test_product_belongs_to_category() {
        Category category = new Category("Food");
        Product apple = new Product("Apple", 100.0, category);
        assertEquals(category, apple.getCategory());
    }

}