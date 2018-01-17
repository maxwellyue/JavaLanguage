### 前言

nothing




---
### 目录结构
```
JavaLanguage
    |
    ├── concurrency 并发工具包中几个类
    |      ├── AtomicExample 原子操作类的演示
    |      |         ├── AtomicExample  原子更新基本类型
    |      |         ├── AtomicIntegerFieldUpdaterExample 原子更新字段
    |      |         └── AtomicReferenceExample  原子更新引用
    |      ├── ConcurrentHashMapExample ConcurrentHashMap的使用，TODO
    |      ├── ConditionExample 使用Condition实现一个有界阻塞队列来演示
    |      ├── CountDownLatchExample1 通过CountDownLatch实现任务开始之前、结束之后执行特定操作
    |      ├── CountDownLatchExample2 通过CountDownLatch实现任务开始之前、结束之后执行特定操作
    |      ├── CyclicBarriarExapmle CyclicBarrier使用演示
    |      ├── ExchangerExample Exchanger使用演示
    |      ├── LockExample Lock接口中定义的几个方法的解释
    |      ├── LockSupportExample TODO
    |      ├── ReentrantReadWriteLockExample 重入读写锁的使用示例
    |      └── SemaphoreExample 信号量Semaphore使用示例
    ├── connectionpool 连接池
    |      └── ConnectionPool  简化的数据库连接池实现，可以帮助理解连接池原理
    ├── consistanthash 一致性哈希算法
    |      ├── ConsistantHash 一致性哈希算法的实现：借助TreeMap实现哈希环
    |      └── RemainderHash 余数哈希算法的实现，以及服务器扩展后的问题演示
    ├── distributedlock 分布式锁
    |      └── SimpleRedisDistributedLock 简单使用Redis实现分布式锁（仅做演示，不可用于生产，生产可调研Redis作者的RedLock）
    ├── producerandconsumer  生产者消费者
    |      ├── ProducerConsumer1 使用synchronized、wait、notify实现
    |      ├── ProducerConsumer2 使用Lock和Condition实现
    |      ├── ProducerConsumer3 使用JDK自带的阻塞队列LinkedBlockingQueue实现
    |      ├── ProducerConsumer4 使用信号量Semaphore实现
    |      ├── ProducerConsumer5 TODO
    |      └── README.md 生产者消费者实现中的注意点说明
    |
    |--TODO


```
