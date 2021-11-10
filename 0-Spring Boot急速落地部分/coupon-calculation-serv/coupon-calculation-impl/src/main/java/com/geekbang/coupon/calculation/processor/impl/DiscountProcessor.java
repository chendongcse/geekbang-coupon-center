package com.geekbang.coupon.calculation.processor.impl;

import com.geekbang.coupon.calculation.api.beans.PlaceOrder;
import com.geekbang.coupon.calculation.processor.AbstractRuleProcessor;
import com.geekbang.coupon.calculation.processor.RuleProcessor;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * 打折优惠券
 */
@Slf4j
@Component
public class DiscountProcessor extends AbstractRuleProcessor implements RuleProcessor {

    @Override
    public PlaceOrder calculate(PlaceOrder order) {
        Long totalAmount = getTotalPrice(order.getProducts());
        Map<Long, Long> sumAmount = this.getTotalPriceGroupByShop(order.getProducts());

        CouponTemplateInfo template = order.getCouponInfos().get(0).getTemplate();
        Long base = template.getRule().getDiscount().getThreshold();
        Long quota = template.getRule().getDiscount().getQuota();

        Long shopId = template.getShopId();
        // 如果是全店打折，基准价格是总价，否则是该门店商品的价格
        Long actualAmount = (shopId == null) ? totalAmount : sumAmount.get(shopId);

        // 如果不符合标准, 则直接返回商品总价
        if (actualAmount == null || actualAmount < base) {
            log.debug("Totals of amount not meet");
            order.setCost(totalAmount);
            order.setCouponInfos(Collections.emptyList());
            return order;
        }

        // 计算新的总价
        Long newCost = (totalAmount - quota) > minCost() ? (totalAmount - quota) : minCost();
        order.setCost(newCost);
        log.debug("original price={}, new price={}", totalAmount, newCost);

        // 计算使用优惠券之后的价格
//        Double reducedAmount = convertToDecimal(actualAmount * (1 - quota/100));
        Long newPrice = convertToDecimal(actualAmount * (1D - quota.doubleValue()/100));
        order.setCost(newPrice < minCost() ? minCost() : newPrice);

        return order;
    }
}
