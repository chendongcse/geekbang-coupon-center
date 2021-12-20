CREATE DATABASE IF NOT EXISTS geekbang_coupon_db;

-- 创建 coupon_template 数据表
DROP TABLE IF EXISTS `geekbang_coupon_db`.`coupon_template`;

CREATE TABLE IF NOT EXISTS `geekbang_coupon_db`.`coupon_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `available` boolean NOT NULL DEFAULT false COMMENT '优惠券可用状态',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '优惠券名称',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '优惠券详细信息',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '优惠券类型，比如满减、随机立减、晚间双倍等等',
  `shop_id` bigint(20) COMMENT '优惠券适用的门店，如果是空则代表全场适用',
  `created_time` datetime NOT NULL DEFAULT '2021-12-13 00:00:00' COMMENT '创建时间',
  `rule` varchar(2000) NOT NULL DEFAULT '' COMMENT '详细的使用规则',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优惠券模板';

DROP TABLE if exists  `geekbang_coupon_db`.`coupon` ;
CREATE TABLE IF NOT EXISTS `geekbang_coupon_db`.`coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `template_id` int(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '拥有这张券的用户ID',
  `created_time` datetime NOT NULL DEFAULT '2021-12-13 00:00:00' COMMENT '领券时间',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '优惠券的状态，比如未用，已用',
  `shop_id` bigint(20) COMMENT '冗余字段，方便查找',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_template_id` (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='领到手的优惠券';