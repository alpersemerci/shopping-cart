package com.alpersemerci.trendyol.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void test_category_without_parent_category() {
        Category category = new Category("Drinks");
        assertEquals("Drinks", category.getTitle());
        assertNull(category.getParentCategory());
    }

    @Test
    public void test_category_with_parent_category() {
        Category parentCategory = new Category("Drinks");
        Category category = new Category("Wines", parentCategory);
        assertEquals("Wines", category.getTitle());
        assertEquals(parentCategory, category.getParentCategory());
    }

}