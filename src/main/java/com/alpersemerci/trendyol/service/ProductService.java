package com.alpersemerci.trendyol.service;

import com.alpersemerci.trendyol.model.Category;
import com.alpersemerci.trendyol.model.Product;

import java.util.HashSet;
import java.util.Set;

/**
 * Service class responsible from product operations.
 *
 * @author Alper Semerci
 */
public class ProductService {

    /**
     * Recursively finds and returns category and all parent categories of product.
     *
     * @param product product
     * @return Category set of product
     */
    public Set<Category> getAllCategoriesOfProduct(Product product) {
        Set<Category> categories = new HashSet<>();
        Category category = product.getCategory();
        categories.add(category);
        if (category.hasParent()) {
            while (true) {
                categories.add(category);
                if (!category.hasParent()) {
                    break;
                }
                category = category.getParentCategory();
            }
        }
        return categories;
    }
}
