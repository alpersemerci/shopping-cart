package com.alpersemerci.trendyol.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShoppingCartTest {

    @Test
    public void test_cart_contains_products_with_quantity_info() {
        Category category = new Category("Food");
        Product apple = new Product("Apple", 100.0, category);
        ShoppingCart cart = new ShoppingCart();
        cart.getContents().put(apple, 10);
        assertEquals(Integer.valueOf(10), cart.getContents().get(apple));
    }

}