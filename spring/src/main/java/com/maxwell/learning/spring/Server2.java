package com.maxwell.learning.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Server2 {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:spring.xml");

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();

    }
}
