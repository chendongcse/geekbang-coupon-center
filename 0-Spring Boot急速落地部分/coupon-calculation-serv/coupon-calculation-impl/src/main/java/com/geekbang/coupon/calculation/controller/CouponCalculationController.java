package com.geekbang.coupon.calculation.controller;

import com.alibaba.fastjson.JSON;
import com.geekbang.coupon.calculation.api.beans.PlaceOrder;
import com.geekbang.coupon.calculation.controller.service.intf.CouponCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("calculator")
public class CouponCalculationController {

    @Autowired
    private CouponCalculationService couponCalculationService;

    // 优惠券结算
    @PostMapping("/checkout")
    @ResponseBody
    public PlaceOrder computeRule(@RequestBody PlaceOrder settlement) {
        log.info("do calculation: {}", JSON.toJSONString(settlement));
        return couponCalculationService.computeRule(settlement);
    }
}
