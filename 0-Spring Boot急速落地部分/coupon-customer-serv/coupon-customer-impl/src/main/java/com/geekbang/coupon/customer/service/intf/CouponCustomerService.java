package com.geekbang.coupon.customer.service.intf;


import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.api.beans.SimulationOrder;
import com.geekbang.coupon.calculation.api.beans.SimulationResponse;
import com.geekbang.coupon.customer.api.beans.RequestCoupon;
import com.geekbang.coupon.customer.dao.entity.Coupon;
import com.geekbang.coupon.template.api.beans.CouponInfo;

import java.util.List;

// 用户对接服务
public interface CouponCustomerService {

    // 查询用户优惠券
    List<CouponInfo> findCoupon(Long userId, Integer status, Long shopId);

    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 核销优惠券
    SimulationResponse simulateOrderPrice(SimulationOrder order);

}
