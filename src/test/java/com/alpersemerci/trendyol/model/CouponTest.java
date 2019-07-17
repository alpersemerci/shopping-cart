package com.alpersemerci.trendyol.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CouponTest {

    @Test
    public void test_coupon_have_minimum_amount_constraint() {
        Coupon coupon = new Coupon(100, 20.0);
        assertEquals(Integer.valueOf(100), coupon.getCouponLimit());
    }

}