package com.maxwell.learning.degradation.hystrix_circuitbreaker;

import java.util.Random;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class EchoService {

    public String echoTime(){
        //模拟抛出异常
        if(new Random().nextInt(10) > 5){
            System.out.println("exception:::failure processing echo time");
            throw new RuntimeException();
        }
        //模拟网络耗时，100或者1200
        try {
            long elapsed = new long[]{100, 1200, 1500}[new Random().nextInt(3)];
            System.out.println("elapsed:::" + elapsed + "ms");
            Thread.sleep(elapsed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "[normal]time:::" + System.currentTimeMillis();
    }
}
