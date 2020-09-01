# Rabbit MQ doc

### windows 操作系统配置方式
配置环境变量   
系统环境变量 path  
```
E:\JavaTools\rabbit MQ\mqserver\rabbitmq_server-3.8.7\sbin  
```

启动命令：
```
C:\Users\root>rabbitmq-plugins enable rabbitmq_management  

Enabling plugins on node rabbit@DESKTOP-VA7852T:
rabbitmq_management
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@DESKTOP-VA7852T...
The following plugins have been enabled:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
```
访问地址:
```
http://localhost:15672
```
login  
username: guest  
password: guest


##rabbit mq 发布订阅持久化以及持久化方式

### exchange、queue、message 之间的持久化的顺序

#### Queue 队列持久化
队列持久化是rabbit mq进行数据传输的最多使用的方式，是进行点对点消息传递使用最多的方式
queue params：
  1. durable: true 持久化设置参数
  2. autodelete: true 是否自动删除  
  3. 队列排他性（exclusive）：如果一个队列被声明为排他队列，该队列仅对【首次申明它的连接可见，并在连接断开时自动删除】
需要注意：1. 排他队列是基于连接可见的，同一连接的不同信道Channel是可以同时访问同一连接创建的排他队列；
	2.“首次”，如果一个连接已经声明了一个排他队列，其他连接是不允许建立同名的排他队列的，这个与普通队列不同；
	3. 即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除的，这种队列适用于一个客户端发送读取消息的应用场景。

#### Message 消息持久化
一个队列设置为持久化，会创建一个持久化队列，但并不意味着队列的消息也能持久化。如果
要保证Rabbit mq出现异常时数据不丢失，需要设定消息的持久化。

简要说明一下消息持久化和队列持久化的联系：

1. 队列设置为持久化，那么在RabbitMQ重启之后，持久化的队列也会存在，并会保持和重启前一致的队列参数。
2. 消息设置为持久化，在RabbitMQ重启之后，持久化的消息也会存在。

message params：
  1. durable: true 持久化设置
  2. autodelete: true 是否自动删除

#### Exchange 交换器持久化
	
    exchange type：
	1. fanout
	2. topic
	3. direct
	4. header
    exchange params:
	1. durable: true 持久化设置
	2. autodelete: 是否自动删除

exchange在和queue进行binding时会设置routingkey
```
bindings = @QueueBinding(
         value = @Queue(value = "queue-1", durable="true"),
         exchange = @Exchange(value = "exchange-1", durable="true", type= "topic", ignoreDeclarationExceptions = "true"),
         key = "springboot.*"
         )
```
我们在将消息发送到exchange时会设置对应的routingkey
```
convertAndSend(String exchange, String routingKey, final Object object,
			CorrelationData correlationData)  

rabbitTemplate.convertAndSend("exchange-1", "springboot.abc", msg, correlationData);
```
在direct类型的exchange中，只有这两个routingkey完全相同，exchange才会选择对应的binding
进行消息路由。

topic 与 redis中的消息订阅/发布大致上是一致的
topic类型的exchange，和direct类型差不多，但direct类型要求routingkey完全相等，这里的routingkey可有通配符：‘*’，‘#’
* '*'表示匹配一个单词；'#'则表示匹配没有或者多个单词。

fanout exchange  
此exchange的路由规则很简单直接将消息路由到所有绑定的队列中，无须对消息的routingkey进行匹配操作。

header exchange  
此类型的exchange和以上三个都不一样，其路由的规则是根据header来判断，其中的header就是以下方法的arguments参数：



1. 设置了队列和消息持久化后，当服务重启之后，消息仍然存在。
2. 只设置队列持久化，不设置消息持久化，重启之后消息会丢失；
3. 只设置消息持久化，不设置队列持久化，在服务重启后，队列会消失
，从而依附于队列的消息也会丢失。只设置消息持久化而不设置队列的持久化，毫无意义。