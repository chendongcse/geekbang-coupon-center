package com.geekbang.coupon.template.api.beans;

import com.geekbang.coupon.template.api.beans.rules.TemplateRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

//import javax.validation.constraints.NotNull;

/**
 * 创建优惠券模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedCouponTemplateInfo {

    public List<CouponTemplateInfo> templates;

    public int page;

    public long total;

}
