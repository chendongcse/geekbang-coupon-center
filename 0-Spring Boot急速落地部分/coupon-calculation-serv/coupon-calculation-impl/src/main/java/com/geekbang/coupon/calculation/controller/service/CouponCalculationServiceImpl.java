package com.geekbang.coupon.calculation.controller.service;

import com.alibaba.fastjson.JSON;
import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.controller.service.intf.CouponCalculationService;
import com.geekbang.coupon.calculation.processor.CouponProcessorFactory;
import com.geekbang.coupon.calculation.processor.RuleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Service
public class CouponCalculationServiceImpl implements CouponCalculationService {

    @Autowired
    private CouponProcessorFactory couponProcessorFactory;

    // 优惠券结算
    // 这里通过Factory类决定使用哪个底层Rule，底层规则对上层透明
    @Override
    public ShoppingCart computeRule(@RequestBody ShoppingCart settlement) {
        log.info("calculate order price: {}", JSON.toJSONString(settlement));
        RuleProcessor ruleProcessor = couponProcessorFactory.getRuleProcessor(settlement);
        return ruleProcessor.calculate(settlement);
    }
}
