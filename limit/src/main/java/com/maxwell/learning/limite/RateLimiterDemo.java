package com.maxwell.learning.limite;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 平滑突发流量
 *
 * RateLimiter.create(5)：create(double permitsPerSecond)，表示桶的大小为5，每隔200毫秒新增一个令牌；
 * limiter.acquire()：表示消费1个令牌。
 *  如果当前桶中有足够令牌，则成功（返回值为0）；
 *  如果当前桶中没有令牌，则等待，直到产生足够令牌，才从该方法中返回（返回值为等待时间）；
 *      比如，当令牌发放间隔是200毫秒，则等待200毫秒才能拿到令牌，。
 */
public class RateLimiterDemo {

    @Test
    public void test1() {
        RateLimiter limiter = RateLimiter.create(5);
        for (int i = 0; i < 8; i++) {
            double acquire = limiter.acquire();
            System.out.println("wait-time-in-second:::" + acquire);
        }
    }

    @Test
    public void test2() {
        RateLimiter limiter = RateLimiter.create(5);
        System.out.println("wait-time-in-second:::" + limiter.acquire(5));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
    }

    @Test
    public void test3() {
        RateLimiter limiter = RateLimiter.create(5);
        System.out.println("wait-time-in-second:::" + limiter.acquire(10));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
        System.out.println("wait-time-in-second:::" + limiter.acquire(1));
    }


    @Test
    public void test4() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(2);
        System.out.println("wait-time-in-second:::" + limiter.acquire());//①此时消费1个令牌，无需等待，返回值为0
        Thread.sleep(2000);//暂停2s，
        // 因为maxBurstSeconds默认为1s，所以，②处获取令牌时，桶中实际有3个令牌，②③④处均无需等待
        System.out.println("wait-time-in-second:::" + limiter.acquire());//②
        System.out.println("wait-time-in-second:::" + limiter.acquire());//③
        System.out.println("wait-time-in-second:::" + limiter.acquire());//④
        System.out.println("wait-time-in-second:::" + limiter.acquire());//等待大约500毫秒
        System.out.println("wait-time-in-second:::" + limiter.acquire());//等待大约500毫秒
        System.out.println("wait-time-in-second:::" + limiter.acquire());//等待大约500毫秒
    }

    @Test
    public void test5() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(5,1, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            double acquire = limiter.acquire();
            System.out.println("wait-time-in-second:::" + acquire);
        }
    }
}
