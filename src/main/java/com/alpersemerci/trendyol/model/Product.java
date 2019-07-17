package com.alpersemerci.trendyol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Product model object
 *
 * @author Alper Semerci
 */
@Getter
@Setter
@AllArgsConstructor
public class Product {

    private String title;

    private Double price;

    private Category category;

}
