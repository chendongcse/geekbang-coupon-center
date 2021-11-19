package com.geekbang.coupon.calculation.processor;

import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.processor.impl.DiscountTemplate;
import com.geekbang.coupon.calculation.processor.impl.DummyTemplate;
import com.geekbang.coupon.calculation.processor.impl.MoneyOffTemplate;
import com.geekbang.coupon.calculation.processor.impl.RandomReductionTemplate;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import com.geekbang.coupon.template.api.enums.CouponType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Slf4j
public class CouponTemplateFactory {

    @Autowired
    private MoneyOffTemplate moneyOffProcessor;

    @Autowired
    private DiscountTemplate discountProcessor;

    @Autowired
    private RandomReductionTemplate randomReductionProcessor;

    @Autowired
    private DummyTemplate dummyProcessor;

    public RuleTemplate getTemplate(ShoppingCart order) {
        // 不使用优惠券
        if (CollectionUtils.isEmpty(order.getCouponInfos())) {
            return dummyProcessor;
        }

        // 暂时不支持多张优惠券
        if (order.getCouponInfos().size() > 1) {
            log.error("不能使用多张优惠券");
            throw new IllegalArgumentException("Cannot apply multiple coupons to one order");
        }

        // 获取优惠券的类别
        CouponTemplateInfo template = order.getCouponInfos().get(0).getTemplate();
        CouponType category = CouponType.convert(template.getType());

        switch (category) {
            case MONEY_OFF:
                return moneyOffProcessor;
            case DISCOUNT:
                return discountProcessor;
            case RANDOM_DISCOUNT:
                return randomReductionProcessor;
            // 未知类型的券模板
            default:
                return dummyProcessor;
        }
    }

}
