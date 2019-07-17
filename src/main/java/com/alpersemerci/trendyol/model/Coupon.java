package com.alpersemerci.trendyol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Coupon model object
 *
 * @author Alper Semerci
 */
@Getter
@Setter
@AllArgsConstructor
public class Coupon {

    private Integer couponLimit;

    private Double discount;

}
