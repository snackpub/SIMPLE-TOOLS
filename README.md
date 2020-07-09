# snack-seata
spring-cloud-alibaba-seata 分布式事务 sample

-- 创建 order库、业务表、undo_log表
create database seata_order;
use seata_order;

DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `undo_log`
(
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch_id`     BIGINT(20)   NOT NULL,
  `xid`           VARCHAR(100) NOT NULL,
  `context`       VARCHAR(128) NOT NULL,
  `rollback_info` LONGBLOB     NOT NULL,
  `log_status`    INT(11)      NOT NULL,
  `log_created`   DATETIME     NOT NULL,
  `log_modified`  DATETIME     NOT NULL,
  `ext`           VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
  
-- 创建 storage库、业务表、undo_log表
create database seata_storage;
use seata_storage;

DROP TABLE IF EXISTS `tb_storage`;
CREATE TABLE `tb_storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `undo_log`
(
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch_id`     BIGINT(20)   NOT NULL,
  `xid`           VARCHAR(100) NOT NULL,
  `context`       VARCHAR(128) NOT NULL,
  `rollback_info` LONGBLOB     NOT NULL,
  `log_status`    INT(11)      NOT NULL,
  `log_created`   DATETIME     NOT NULL,
  `log_modified`  DATETIME     NOT NULL,
  `ext`           VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

-- 初始化库存模拟数据
INSERT INTO seata_storage.tb_storage (id, commodity_code, count) VALUES (1, 'product-1', 9999999);
INSERT INTO seata_storage.tb_storage (id, commodity_code, count) VALUES (2, 'product-2', 0);

## 如何定义分布式事务？

我们说，分布式事务是由一批分支事务组成的全局事务，通常分支事务只是本地事务。

seata 有三个组成部分：
* 事务协调器（TC）: 维护全局事务和分支事务的状态，驱动全局提交或回滚。
* 事务管理器（TM）: 定义全局事务的范围：开始全局事务，提交或回滚全局事务.
* 资源管理器（RM）: 管理分支事务正在处理的资源，与TC进行对话以注册分支事务并报告分支事务状态，并驱动分支事务的提交或回滚。

seata 典型的生命周期：
1. TM要求TC开始一项新的全球交易。TC生成代表全局交易的XID。
2. XID通过微服务的调用链传播。
3. RM将本地事务注册为XID到TC的相应全局事务的分支。
4. TM要求TC提交或回滚XID的相应全局事务。
5. TC驱动相应的XID全局事务下的所有分支事务以完成分支提交或回滚。