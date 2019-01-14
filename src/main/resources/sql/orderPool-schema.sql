SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `orderPool`;
CREATE TABLE `orderPool` (
  `number` varchar(255) NOT NULL COMMENT '单号',
  `isUsed` SMALLINT(5) NOT NULL DEFAULT '0' COMMENT '是否已经使用',
  UNIQUE KEY `uni_num` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
