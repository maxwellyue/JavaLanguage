package com.maxwell.learning.common.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MutexExample {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);
        CountDownLatch latch = new CountDownLatch(1000);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(() -> {
                counter.increase();
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(counter.getNum());
    }
}
