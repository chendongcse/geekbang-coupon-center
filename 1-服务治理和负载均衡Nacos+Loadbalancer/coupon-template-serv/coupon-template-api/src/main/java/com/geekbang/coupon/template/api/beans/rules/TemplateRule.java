package com.geekbang.coupon.template.api.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券计算规则
 * @author 姚半仙
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {

    /** 可以享受的折扣 */
    private Discount discount;

    // 每个人最多可以领券数量
    private Integer limitation;

    // 过期时间
    private Long deadline;

}
