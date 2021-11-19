package com.geekbang.coupon.calculation.processor;

import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.processor.impl.*;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import com.geekbang.coupon.template.api.enums.CouponType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

// 工厂方法根据订单中的优惠券信息，返回对应的Template用于计算优惠价
@Component
@Slf4j
public class CouponTemplateFactory {

    @Autowired
    private MoneyOffTemplate moneyOffTemplate;

    @Autowired
    private DiscountTemplate discountTemplate;

    @Autowired
    private RandomReductionTemplate randomReductionTemplate;

    @Autowired
    private LonelyNightTemplate lonelyNightTemplate;

    @Autowired
    private DummyTemplate dummyTemplate;

    public RuleTemplate getTemplate(ShoppingCart order) {
        // 不使用优惠券
        if (CollectionUtils.isEmpty(order.getCouponInfos())) {
            // dummy模板直接返回原价，不进行优惠计算
            return dummyTemplate;
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
            // 订单满减券
            case MONEY_OFF:
                return moneyOffTemplate;
            // 随机立减券
            case RANDOM_DISCOUNT:
                return randomReductionTemplate;
            // 午夜下单优惠翻倍
            case LONELY_NIGHT_MONEY_OFF:
                return lonelyNightTemplate;
            // 打折券
            case DISCOUNT:
                return discountTemplate;
            // 未知类型的券模板
            default:
                return dummyTemplate;
        }
    }

}
