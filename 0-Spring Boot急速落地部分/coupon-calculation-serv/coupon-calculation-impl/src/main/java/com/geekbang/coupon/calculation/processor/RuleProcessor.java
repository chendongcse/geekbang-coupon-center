package com.geekbang.coupon.calculation.processor;


import com.geekbang.coupon.calculation.api.beans.PlaceOrder;

public interface RuleProcessor {

    // 计算优惠券
    PlaceOrder calculate(PlaceOrder settlement);
}
