CREATE TABLE `merchants` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(64) NOT NULL COMMENT '商户名称',
    `logo_url` varchar(255) NOT NULL COMMENT '商户logo',
    `business_license_url` varchar(255) NOT NULL COMMENT '商户营业执照',
    `phone` varchar(20) NOT NULL COMMENT '商户联系电话',
    `address` varchar(64) NOT NULL COMMENT '商户地址',
    `is_audit` boolean NOT NULL COMMENT '是否通过审核',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
