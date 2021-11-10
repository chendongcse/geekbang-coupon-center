package com.geekbang.coupon.calculation.controller.service.intf;

import com.geekbang.coupon.calculation.api.beans.PlaceOrder;
import org.springframework.web.bind.annotation.RequestBody;

public interface CouponCalculationService {

    public PlaceOrder computeRule(@RequestBody PlaceOrder settlement);
}
