package com.geekbang.coupon.calculation.processor;



import com.geekbang.coupon.calculation.api.beans.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定义通用的计算逻辑
 */
public abstract class AbstractRuleProcessor {

    // 计算总价
    protected long getTotalPrice(List<Product> products) {
        return products.stream()
                .mapToLong(product -> product.getPrice() * product.getCount())
                .sum();
    }

    // 根据门店维度计算每个门店下商品价格
    // key = shopId
    // value = 门店商品总价
    protected Map<Long, Long> getTotalPriceGroupByShop(List<Product> products) {
        Map<Long, Long> groups = products.stream()
                .collect(Collectors.groupingBy(m -> m.getShopId(),
                        Collectors.summingLong(p -> p.getPrice() * p.getCount()))
                );
        return groups;
    }

    // 最小支付金额是0.01
    protected long minCost() {
        return 1;
    }

    protected long convertToDecimal(Double value) {
        return new BigDecimal(value).setScale(0, RoundingMode.HALF_UP).longValue();
    }
}
