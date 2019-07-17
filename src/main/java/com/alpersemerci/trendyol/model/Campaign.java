package com.alpersemerci.trendyol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Campaign model object
 *
 * @author Alper Semerci
 */
@Getter
@Setter
@AllArgsConstructor
public class Campaign {

    private Category category;

    private Double discount;

    private Integer discountLimit;

    private DiscountType discountType;
}
