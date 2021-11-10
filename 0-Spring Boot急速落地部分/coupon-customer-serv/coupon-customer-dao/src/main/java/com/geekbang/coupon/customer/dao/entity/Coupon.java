package com.geekbang.coupon.customer.dao.entity;

import com.geekbang.coupon.customer.api.enums.CouponStatus;
import com.geekbang.coupon.customer.dao.convertor.CouponStatusConverter;
import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 对应的模板ID - 不使用one to one映射
    @Column(name = "template_id", nullable = false)
    private Long templateId;

    // 所有者的用户ID
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 冗余一个shop id方便查找
    @Column(name = "shop_id")
    private Long shopId;

    // 获取时间自动生成
    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    /** 优惠券状态 */
    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    @Transient
    private CouponTemplateInfo templateInfo;

}
