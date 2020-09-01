# snack-seata
spring-cloud-alibaba-seata 分布式事务 sample



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



Rabbit MQ

start: rabbitmq-plugins enable rabbitmq_management
url: http://localhost:15672/#/
