package com.alpersemerci.trendyol.service;

import com.alpersemerci.trendyol.model.Category;
import com.alpersemerci.trendyol.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ProductServiceTest {

    private ProductService productService;

    @Before
    public void setUp() {
        productService = new ProductService();
    }

    @Test
    public void test_getting_all_categories_of_product() {
        Category dairy = new Category("Dairy");
        Category yogurt = new Category("Yogurt");
        Category diabetic = new Category("Diabetic");
        diabetic.setParentCategory(yogurt);
        yogurt.setParentCategory(dairy);

        Product product =  new Product("Diabetic Yogurt", 10.0, diabetic);

        Set<Category> allCategoriesOfProduct = productService.getAllCategoriesOfProduct(product);

        assertEquals(3, allCategoriesOfProduct.size());
        assertTrue(allCategoriesOfProduct.contains(dairy));
        assertTrue(allCategoriesOfProduct.contains(yogurt));
        assertTrue(allCategoriesOfProduct.contains(diabetic));

    }
}