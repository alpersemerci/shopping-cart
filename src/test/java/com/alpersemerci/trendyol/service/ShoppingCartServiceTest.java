package com.alpersemerci.trendyol.service;

import com.alpersemerci.trendyol.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.Assert.*;

public class ShoppingCartServiceTest {

    private ShoppingCartService shoppingCartService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        shoppingCartService = new ShoppingCartService();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void test_item_counts_when_adding_item_to_cart() {
        Category beverages = new Category("Beverages");
        Product iceCoffee = new Product("Ice Coffee", 10D, beverages);
        Product mineralWater = new Product("Mineral Water", 5D, beverages);

        ShoppingCart cart = new ShoppingCart();
        shoppingCartService.addItemToShoppingCart(cart, iceCoffee, 10);
        shoppingCartService.addItemToShoppingCart(cart, mineralWater, 5);

        assertEquals(Integer.valueOf(10), cart.getContents().get(iceCoffee));
        assertEquals(Integer.valueOf(5), cart.getContents().get(mineralWater));
    }

    @Test
    public void test_total_amount_when_adding_item_to_cart() {
        Category beverages = new Category("Beverages");
        Product iceCoffee = new Product("Ice Coffee", 10D, beverages);
        Product mineralWater = new Product("Mineral Water", 5D, beverages);

        ShoppingCart cart = new ShoppingCart();
        shoppingCartService.addItemToShoppingCart(cart, iceCoffee, 10);
        shoppingCartService.addItemToShoppingCart(cart, mineralWater, 5);

        assertEquals(Double.valueOf(125), cart.getTotalAmount());
    }

    @Test
    public void test_calculate_discount_total_with_rate_discount() {

        Category beverages = new Category("Beverages");
        Product iceCoffee = new Product("Ice Coffee", 10D, beverages);
        Product mineralWater = new Product("Mineral Water", 5D, beverages);

        ShoppingCart cart = new ShoppingCart();
        shoppingCartService.addItemToShoppingCart(cart, iceCoffee, 10);
        shoppingCartService.addItemToShoppingCart(cart, mineralWater, 5);

        Campaign campaign = new Campaign(beverages, 20.0, 6, DiscountType.RATE);
        shoppingCartService.applyCampaignDiscount(cart, campaign);

        assertEquals(Double.valueOf(125), cart.getTotalAmount());
        assertEquals(Double.valueOf(105), cart.getTotalAmountAfterDiscounts());
        assertEquals(Double.valueOf(20), cart.getCampaignDiscount());

    }

    @Test
    public void test_calculate_discount_total_with_amount_discount() {

        Category beverages = new Category("Beverages");
        Product iceCoffee = new Product("Ice Coffee", 10D, beverages);
        Product mineralWater = new Product("Mineral Water", 5D, beverages);

        ShoppingCart cart = new ShoppingCart();
        shoppingCartService.addItemToShoppingCart(cart, iceCoffee, 10);
        shoppingCartService.addItemToShoppingCart(cart, mineralWater, 5);

        Campaign campaign = new Campaign(beverages, 30.0, 6, DiscountType.AMOUNT);
        shoppingCartService.applyCampaignDiscount(cart, campaign);

        assertEquals(Double.valueOf(125), cart.getTotalAmount());
        assertEquals(Double.valueOf(95), cart.getTotalAmountAfterDiscounts());
        assertEquals(Double.valueOf(30), cart.getCampaignDiscount());
    }

    @Test
    public void test_calculate_discount_total_with_multiple_discounts() {

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

        Campaign campaign1 = new Campaign(beverages, 30.0, 6, DiscountType.AMOUNT);
        Campaign campaign2 = new Campaign(bakery, 2.0, 1, DiscountType.AMOUNT);
        shoppingCartService.applyCampaignDiscount(cart, campaign1, campaign2);

        assertEquals(Double.valueOf(179), cart.getTotalAmount());
        assertEquals(Double.valueOf(145), cart.getTotalAmountAfterDiscounts());
        assertEquals(Double.valueOf(34), cart.getCampaignDiscount());
    }

    @Test
    public void test_calculate_discount_total_with_multiple_discounts_and_a_valid_coupon() {

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

        Campaign campaign1 = new Campaign(beverages, 30.0, 6, DiscountType.AMOUNT);
        Campaign campaign2 = new Campaign(bakery, 2.0, 1, DiscountType.AMOUNT);
        shoppingCartService.applyCampaignDiscount(cart, campaign1, campaign2);

        Coupon coupon = new Coupon(100, 10.0);
        shoppingCartService.applyCoupon(cart, coupon);

        assertEquals(Double.valueOf(179), cart.getTotalAmount());
        assertEquals(Double.valueOf(135), cart.getTotalAmountAfterDiscounts());
        assertEquals(Double.valueOf(34), cart.getCampaignDiscount());
        assertEquals(Double.valueOf(10), cart.getCouponDiscount());
    }

    @Test
    public void test_calculate_discount_total_with_multiple_discounts_and_invalid_coupon() {

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

        Campaign campaign1 = new Campaign(beverages, 30.0, 6, DiscountType.AMOUNT);
        Campaign campaign2 = new Campaign(bakery, 2.0, 1, DiscountType.AMOUNT);
        shoppingCartService.applyCampaignDiscount(cart, campaign1, campaign2);

        Coupon coupon = new Coupon(500, 10.0);
        shoppingCartService.applyCoupon(cart, coupon);

        assertEquals(Double.valueOf(179), cart.getTotalAmount());
        assertEquals(Double.valueOf(145), cart.getTotalAmountAfterDiscounts());
        assertEquals(Double.valueOf(34), cart.getCampaignDiscount());
        assertEquals(Double.valueOf(0), cart.getCouponDiscount());
    }

    @Test
    public void test_print_chart() {

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

        Campaign campaign1 = new Campaign(beverages, 30.0, 6, DiscountType.AMOUNT);
        Campaign campaign2 = new Campaign(bakery, 2.0, 1, DiscountType.AMOUNT);
        shoppingCartService.applyCampaignDiscount(cart, campaign1, campaign2);

        Coupon coupon = new Coupon(500, 10.0);
        shoppingCartService.applyCoupon(cart, coupon);
        shoppingCartService.printCart(cart);

        assertTrue(outContent.toString().contains("Beverages"));

        assertTrue(outContent.toString().contains("\t * Ice Coffee\t10\t10.0\t100.0\n" +
        "\t * Mineral Water\t5\t5.0\t25.0\n\n"));

        assertTrue(outContent.toString().contains("\t * Bread\t6\t4.0\t24.0\n" +
                "\t * Pizza\t2\t15.0\t30.0\n" +
                "\n"));

        assertTrue(outContent.toString().contains("Total AMOUNT : 179.0"));
        assertTrue(outContent.toString().contains("Campaign Discount : 34.0"));
        assertTrue(outContent.toString().contains("Coupon Discount : 0.0"));
        assertTrue(outContent.toString().contains("Total AMOUNT After Discounts: 145.0"));
    }

    @Test
    public void test_find_distinct_product_categories() {
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

        Set<Category> distinctCategories = shoppingCartService.findDistinctProductCategoriesInCart(cart);

        assertEquals(2, distinctCategories.size());
    }
}