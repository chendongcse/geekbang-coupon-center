package com.geekbang.coupon.customer.feign;

import com.geekbang.coupon.customer.feign.fallback.TemplateServiceFallback;
import com.geekbang.coupon.customer.feign.fallback.TemplateServiceFallbackFactory;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@FeignClient(value = "coupon-template-serv", path = "/template",
//        fallback = TemplateServiceFallback.class,
        fallbackFactory = TemplateServiceFallbackFactory.class)
public interface TemplateService {

    // 读取优惠券
    @GetMapping("/getTemplate")
    CouponTemplateInfo getTemplate(@RequestParam("id") Long id);

    // 批量获取
    @GetMapping("/getBatch")
    Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids);

}
