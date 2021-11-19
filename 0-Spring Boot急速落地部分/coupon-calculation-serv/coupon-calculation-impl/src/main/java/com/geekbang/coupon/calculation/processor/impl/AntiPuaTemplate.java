package com.geekbang.coupon.calculation.processor.impl;

import com.geekbang.coupon.calculation.processor.AbstractRuleTemplate;
import com.geekbang.coupon.calculation.processor.RuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Slf4j
@Component
public class AntiPuaTemplate extends AbstractRuleTemplate implements RuleTemplate {

    // 啥也不说了，看实现
    // 1）因为相信，所以看见
    // 2）今天最好的表现，是明天最低的要求
    // 3) 聚是一团火，散是满天星
    // 4) 你写这段代码的底层逻辑是什么，有没有体现出你的思考和沉淀？最终的交付价值是什么？
    //    如何和兄弟团队形成矩阵联动和共振，打出一套组合拳从而赋能整个生态体系?
    //    如何形成闭环？这里面的抓手在哪里？你的代码有什么亮点，换一个人写会不不一样？
    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        // 凡是在职场碰到上述价值观味道重，不说人话，散播PUA思想的人，不管这是同事还是老板，请一顿996组合拳伺候

        // 如果你order总价很高（比如你买了一搜航空母舰），这里*996后要注意下Long范围溢出的问题
        return orderTotalAmount * 996;
    }
}
