package com.alpersemerci.trendyol.service;

import com.alpersemerci.trendyol.model.Category;
import com.alpersemerci.trendyol.model.ShoppingCart;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * Service class responsible from calculating delivery cost of given shopping cart.
 *
 * @author Alper Semerci
 */
@RequiredArgsConstructor
@Getter
public class DeliveryCostService {

    @NonNull
    private Double costPerDelivery;

    @NonNull
    private Double costPerProduct;

    @NonNull
    private Double fixedCost;

    private ShoppingCartService shoppingCartService = new ShoppingCartService();

    /**
     * Calculates delivery cost of given shopping cart.
     *
     * @param cart Shopping cart
     * @return Delivery cost
     */
    public Double calculateDeliveryCost(ShoppingCart cart) {
        Integer numberOfDeliveries = getNumberOfDeliveries(cart);
        Integer numberOfProducts = getNumberOfProducts(cart);
        return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;
    }

    /**
     * Returns number of delivery of given shopping cart.
     *
     * @param cart Shoppping cart
     * @return Number of delivery
     */
    private Integer getNumberOfDeliveries(ShoppingCart cart) {
        Set<Category> distinctCategories = shoppingCartService.findDistinctProductCategoriesInCart(cart);
        return distinctCategories.size();
    }

    /**
     * Returns number of products in given shopping cart.
     *
     * @param cart Shopping cart.
     * @return Number of products.
     */
    private Integer getNumberOfProducts(ShoppingCart cart) {
        return cart.getContents().keySet().size();
    }
}
