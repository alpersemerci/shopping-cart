package com.alpersemerci.trendyol.service;

import com.alpersemerci.trendyol.model.Category;
import com.alpersemerci.trendyol.model.Product;
import com.alpersemerci.trendyol.model.ShoppingCart;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeliveryCostServiceTest {

    private DeliveryCostService deliveryCostService;

    private ShoppingCartService shoppingCartService;

    @Before
    public void setUp() {
        deliveryCostService = new DeliveryCostService(2.0, 4.0, 2.95);
        shoppingCartService = new ShoppingCartService();
    }

    @Test
    public void test_service_initialization() {
        assertEquals(Double.valueOf(2.0), deliveryCostService.getCostPerDelivery());
        assertEquals(Double.valueOf(4.0), deliveryCostService.getCostPerProduct());
        assertEquals(Double.valueOf(2.95), deliveryCostService.getFixedCost());
    }

    @Test
    public void test_calculate_delivery_cost() {
        Category beverages = new Category("Beverages");
        Product iceCoffee = new Product("Ice Coffee", 10D, beverages);
        Product mineralWater = new Product("Mineral Water", 5D, beverages);

        Category bakery = new Category("Bakery");
        Product bread = new Product("Bread", 4D, bakery);
        Product pizza = new Product("Pizza", 15D, bakery);

        ShoppingCart cart = new ShoppingCart();
        shoppingCartService.addItemToShoppingCart(cart, iceCoffee, 10);
        shoppingCartService.addItemToShoppingCart(cart, mineralWater, 5);
        shoppingCartService.addItemToShoppingCart(cart, bread, 6);
        shoppingCartService.addItemToShoppingCart(cart, pizza, 2);

        assertEquals(Double.valueOf(22.95), deliveryCostService.calculateDeliveryCost(cart));
    }
}