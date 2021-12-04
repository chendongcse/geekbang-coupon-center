package com.geekbang.coupon.calculation.template.impl;

import com.geekbang.coupon.calculation.template.AbstractRuleTemplate;
import com.geekbang.coupon.calculation.template.RuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// PAU客户专用优惠计算逻辑，每笔订单享受996暴击
@Slf4j
@Component
public class AntiPauTemplate extends AbstractRuleTemplate implements RuleTemplate {

    // 啥也不说了，下面的例子均为重度PAU的表现，以下名句均摘抄自某公司员工入职发放的本子
    // 1）因为相信，所以看见
    //  （解读：我画的饼在这，不管你信不信，反正我是信了）
    // 2）今天最好的表现，是明天最低的要求
    //  （解读：卷到至死方休）
    // 3) 当你觉得不舒服的时候，就是成长的时候
    //  （解读：对待PAU你要默默承受，我PAU你这是让你成长）
    // 4) 开会不能总讲和气，越和气就越微妙
    //  （解读：鼓励大家向四面八方输出PAU文化）
    // 5) 生命在于折腾，不在折腾中崩溃，就在折腾中涅槃
    //  （解读：公司文化在于PAU，不在PAU中崩溃离职，就在PAU中涅槃去PAU其他人）
    //
    // PAU的精髓：
    //    你写这段代码的底层逻辑是什么，有没有体现出你的思考和沉淀？最终的交付价值是什么？
    //    如何和兄弟团队形成矩阵联动和共振，打出一套组合拳从而赋能整个生态体系?
    //    如何形成闭环？这里面的抓手在哪里？你的代码有什么亮点，换一个人写会不不一样？
    //    你代码里做的事情都是我告诉你的，没有自己的思考，你要学会无中生有自己创造价值和商业模式
    //    你写这段代码的顶层设计在哪里？它解决了用户什么痛点？如何培养用户心智？
    //
    //    没有加班搞不定的事情，如果加一天班不行，那就加两天
    //
    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        // 凡是在职场碰到上述满口价值观、味道重、不说人话、散播PUA思想的人
        // 不管这是同事还是老板，请一顿996组合拳伺候

        // 如果这一单order总价很高（比如买了一搜航空母舰），这里*996后要注意下Long范围溢出的问题
        return orderTotalAmount * 996;
    }

    // 设置订单最小支付金额=996
    @Override
    protected long minCost() {
        return 996;
    }
}
