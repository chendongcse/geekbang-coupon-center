package com.geekbang.coupon.calculation.processor.impl;

import com.geekbang.coupon.calculation.processor.AbstractRuleProcessor;
import com.geekbang.coupon.calculation.processor.RuleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 打折优惠券
 */
@Slf4j
@Component
public class DiscountProcessor extends AbstractRuleProcessor implements RuleProcessor {

    @Override
    protected Long calculateNewPrice(Long totalAmount, Long shopAmount, Long quota) {
        // 计算使用优惠券之后的价格
        Long newPrice = convertToDecimal(shopAmount * (1D - quota.doubleValue()/100));
        log.debug("original price={}, new price={}", totalAmount, newPrice);
        return newPrice;
    }
}
