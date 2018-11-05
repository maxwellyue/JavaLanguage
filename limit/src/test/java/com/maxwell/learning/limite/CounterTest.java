package com.maxwell.learning.limite;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
class CounterTest {

    @Test
    void grant() throws InterruptedException {
        Counter counter = new Counter(10);
        for (int i = 0; i < 30; i++) {
            if(counter.grant()){
                System.out.println("permit:::request:::" + i);
                Thread.sleep(100);
            }else {
                System.out.println("reject:::request:::" + i);
            }
        }
    }

    @Test
    void grant2() throws InterruptedException {
        Counter counter = new Counter(10);
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            service.submit(()->{
                if(counter.grant()){
                    System.out.println("permit:::request:::" + finalI);
                    try {
                        Thread.sleep(new Random().nextInt(500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("reject:::request:::" + finalI);
                }
            });
        }

        Thread.sleep(1000);
    }
}