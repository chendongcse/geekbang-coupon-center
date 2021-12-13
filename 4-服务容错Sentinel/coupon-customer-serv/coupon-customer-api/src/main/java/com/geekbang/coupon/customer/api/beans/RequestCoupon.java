package com.geekbang.coupon.customer.api.beans;

import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCoupon {

    // 用户领券
    @NotNull
    private Long userId;

    @NotNull
    private Long couponTemplateId;

    /** 优惠券模板信息 */
    private CouponTemplateInfo templateSDK;

    // Loadbalancer - 用作测试流量打标
    private String trafficVersion;
}
