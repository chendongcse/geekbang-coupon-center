package com.geekbang.coupon.customer.service;

import com.geekbang.coupon.customer.dao.entity.Coupon;
import com.geekbang.coupon.template.api.beans.CouponInfo;

public class CouponConverter {

    public static CouponInfo convertToCoupon(Coupon coupon) {

        return CouponInfo.builder()
                .id(coupon.getId())
                .templateId(coupon.getTemplateId())
                .status(coupon.getStatus().getCode())
                .userId(coupon.getUserId())
                .shopId(coupon.getShopId())
                .template(coupon.getTemplateInfo())
                .build();
    }
}
