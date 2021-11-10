package com.geekbang.coupon.template.service.intf;


import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CouponTemplateService {

    /**
     * 创建优惠券模板
     */
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);

    /**
     * 根据参数查询优惠券模板
     */
    List<CouponTemplateInfo> searchTemplate(CouponTemplateInfo request);

    /**
     * 通过模板ID查询优惠券模板
     */
    CouponTemplateInfo loadTemplateInfo(Long id);

    /**
     * 让优惠券模板无效
     */
    void inactiveCoupon(Long id);

    /**
     * 批量读取模板
     */
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);
}
