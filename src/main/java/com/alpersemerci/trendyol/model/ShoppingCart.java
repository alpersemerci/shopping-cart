package com.alpersemerci.trendyol.model;

import lombok.Data;

import java.util.*;

/**
 * Shopping Cart model object
 *
 * @author Alper Semerci
 */
@Data
public class ShoppingCart {

    private Map<Product, Integer> contents = new HashMap<>();

    private Double deliveryCost = 0D;

    private Double campaignDiscount = 0D;

    private Double couponDiscount = 0D;

    private Double totalAmountAfterDiscounts = 0D;

    private Double totalAmount = 0D;

}
