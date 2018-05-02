package com.maxwell.learning.versioncompatible;

/**
 *
 *
 * 演示某些情况下的版本兼容问题的解决方案
 *
 * 思路：
 * 将你想要借助某框架实现的功能抽象出来（如例子中的doSomething()），
 * 然后根据运行时的环境，判断框架版本，根据框架版本，写出具体的功能逻辑。
 *
 * 思路来源：https://github.com/openzipkin/brave/blob/master/instrumentation/spring-webmvc/src/main/java/brave/spring/webmvc/WebMvcRuntime.java
 *
 *
 * @author yuezengcun <yueyuemax@gmail.com>
 * @since
 */
public abstract class SpringDataRedisRunTime {

    abstract void doSomething();

    private static final SpringDataRedisRunTime SPRING_DATA_REDIS_RUNTIME = findSpringDataRedisRunTime();

    static SpringDataRedisRunTime get(){
        return SPRING_DATA_REDIS_RUNTIME;
    }

    static SpringDataRedisRunTime findSpringDataRedisRunTime(){
        try {
            Class.forName("org.springframework.data.redis.connection.jedis.JedisClientConfiguration");
            return new SpringDataRedisRunTime2x();
        } catch (ClassNotFoundException e) {
            // pre spring-data-redis v2.0
            return new SpringDataRedisRunTime1x();
        }
    }


    static final class SpringDataRedisRunTime1x extends SpringDataRedisRunTime{

        @Override
        void doSomething() {
            //how to iml doSomething() with  Spring-Data-Redis 1.xx version
        }
    }

    static final class SpringDataRedisRunTime2x extends SpringDataRedisRunTime{

        @Override
        void doSomething() {
            //how to iml doSomething() with  Spring-Data-Redis 2.xx version
        }
    }
}
