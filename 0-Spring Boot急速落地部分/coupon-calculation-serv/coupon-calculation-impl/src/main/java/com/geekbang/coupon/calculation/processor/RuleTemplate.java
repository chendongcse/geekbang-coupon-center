package com.geekbang.coupon.calculation.processor;


import com.geekbang.coupon.calculation.api.beans.ShoppingCart;

public interface RuleTemplate {

    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
