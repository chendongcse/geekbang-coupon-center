package com.geekbang.coupon.customer.service;


import com.geekbang.coupon.calculation.api.beans.PlaceOrder;
import com.geekbang.coupon.calculation.controller.service.intf.CouponCalculationService;
import com.geekbang.coupon.customer.api.beans.RequestCoupon;
import com.geekbang.coupon.customer.api.enums.CouponStatus;
import com.geekbang.coupon.customer.dao.CouponDao;
import com.geekbang.coupon.customer.dao.entity.Coupon;
import com.geekbang.coupon.customer.service.intf.CouponCustomerService;
import com.geekbang.coupon.template.api.beans.CouponInfo;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import com.geekbang.coupon.template.service.intf.CouponTemplateService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CouponCustomerServiceImpl implements CouponCustomerService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponTemplateService templateService;

    @Autowired
    private CouponCalculationService calculationService;

    /**
     * 用户查询优惠券的接口
     */
    @Override
    public List<CouponInfo> findCoupon(Long userId, Integer status, Long shopId) {
        Coupon example = Coupon.builder()
                .userId(userId)
                .status(CouponStatus.convert(status))
                .shopId(shopId)
                .build();
        List<Coupon> coupons = couponDao.findAll(Example.of(example));
        if (coupons.isEmpty()) {
            return Lists.newArrayList();
        }

        List<Long> templateIds = coupons.stream()
                .map(Coupon::getTemplateId)
                .collect(Collectors.toList());
        Map<Long, CouponTemplateInfo> templateMap = templateService.getTemplateInfoMap(templateIds);
        coupons.stream().forEach(e -> e.setTemplateInfo(templateMap.get(e.getTemplateId())));

        return coupons.stream()
                .map(CouponConverter::convertToCoupon)
                .collect(Collectors.toList());
    }

    /**
     * 用户领取优惠券
     */
    @Override
    public Coupon requestCoupon(RequestCoupon request) {
        CouponTemplateInfo templateInfo = templateService.loadTemplateInfo(request.getCouponTemplateId());

        // 模板不存在则报错
        if (templateInfo == null) {
            log.error("invalid template id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("Invalid template id");
        }

        // 模板不能过期
        long now = Calendar.getInstance().getTimeInMillis();
        Long expTime = templateInfo.getRule().getDeadline();
        if (expTime != null && now >= expTime || BooleanUtils.isFalse(templateInfo.getAvailable())) {
            log.error("template is not available id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("template is unavailable");
        }

        // 用户领券数量超过上限
        Long count = couponDao.countByUserIdAndTemplateId(request.getUserId(), request.getCouponTemplateId());
        if (count >= templateInfo.getRule().getLimitation()) {
            log.error("exceeds maximum number");
            throw new IllegalArgumentException("exceeds maximum number");
        }

        Coupon coupon = Coupon.builder()
                .templateId(request.getCouponTemplateId())
                .userId(request.getUserId())
                .shopId(templateInfo.getShopId())
                .status(CouponStatus.AVAILABLE)
                .build();
        couponDao.save(coupon);
        return coupon;
    }

    @Override
    @Transactional
    public PlaceOrder placeOrder(PlaceOrder order) {
        if (CollectionUtils.isEmpty(order.getProducts())) {
            throw new IllegalArgumentException("empty cart");
        }

        Coupon coupon = null;
        if (order.getCouponId() != null) {
            // 如果有优惠券，验证是否可用，并且是当前客户的
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .id(order.getCouponId())
                    .status(CouponStatus.AVAILABLE)
                    .build();
            coupon = couponDao.findAll(Example.of(example))
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
            couponInfo.setTemplate(templateService.loadTemplateInfo(coupon.getTemplateId()));
            order.setCouponInfos(Lists.newArrayList(couponInfo));
        }

        // order清算
        PlaceOrder checkoutInfo = calculationService.computeRule(order);

        // 如果清算结果里没有优惠券，而用户传递了优惠券，报错提示该订单满足不了优惠条件
        if (CollectionUtils.isEmpty(checkoutInfo.getCouponInfos()) && coupon != null) {
            log.error("cannot apply coupponId={} to order", coupon.getId());
            throw new IllegalArgumentException("Not an eligible coupon");
        }

        if (coupon != null) {
            coupon.setStatus(CouponStatus.USED);
            couponDao.save(coupon);
        }
        return checkoutInfo;
    }

}
