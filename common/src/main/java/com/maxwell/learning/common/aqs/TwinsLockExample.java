package com.maxwell.learning.common.aqs;

import java.util.concurrent.locks.Lock;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class TwinsLockExample {

    public static void main(String[] args) {

        final Lock lock = new TwinsLock();

        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        System.out.println("interruptException!");
                    } finally {
                        lock.unlock();
                        break;
                    }
                }
            }
        }

        //启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        //每间隔一秒钟打印一个空行.
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
