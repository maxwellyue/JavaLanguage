package com.maxwell.learning.limite;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
class PeriodCounterTest {

    @Test
    void permit() throws ExecutionException, InterruptedException {
        //每秒只允许访问两次
        PeriodCounter counter = new PeriodCounter(2);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(new Random().nextInt(1000));
            if(counter.permit()){
                System.out.println("permit:::request:::" + i);
            }else {
                System.out.println("reject:::request:::" + i);
            }
        }
    }

    @Test
    void permit2() throws InterruptedException {
        //每秒只允许访问两次
        PeriodCounter counter = new PeriodCounter(2);
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(new Random().nextInt(1000));
            int finalI = i;
            service.submit(()->{
                try {
                    if(counter.permit()){
                        System.out.println("permit:::request:::" + finalI);
                        try {
                            Thread.sleep(new Random().nextInt(500));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("reject:::request:::" + finalI);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(1000);
    }
}