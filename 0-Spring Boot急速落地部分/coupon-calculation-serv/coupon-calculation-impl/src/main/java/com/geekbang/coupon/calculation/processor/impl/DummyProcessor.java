package com.geekbang.coupon.calculation.processor.impl;

import com.geekbang.coupon.calculation.api.beans.PlaceOrder;
import com.geekbang.coupon.calculation.processor.AbstractRuleProcessor;
import com.geekbang.coupon.calculation.processor.RuleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 空实现
 */
@Slf4j
@Component
public class DummyProcessor extends AbstractRuleProcessor implements RuleProcessor {

    @Override
    public PlaceOrder calculate(PlaceOrder order) {
        Long totalAmount = getTotalPrice(order.getProducts());
        order.setCost(totalAmount);
        return order;
    }
}
