package com.geekbang.coupon.calculation.processor.impl;

import com.geekbang.coupon.calculation.processor.AbstractRuleProcessor;
import com.geekbang.coupon.calculation.processor.RuleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 满减优惠券计算规则
 */
@Slf4j
@Component
public class MoneyOffProcessor extends AbstractRuleProcessor implements RuleProcessor {

    @Override
    protected Long calculateNewPrice(Long totalAmount, Long shopAmount, Long quota) {
        // benefitAmount是扣减的价格
        // 如果当前门店的商品总价<quota，那么最多只能扣减shopAmount的钱数
        Long benefitAmount = shopAmount < quota ? shopAmount : quota;
        return totalAmount - benefitAmount;
    }
}
