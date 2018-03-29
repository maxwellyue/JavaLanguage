package com.maxwell.learning.producerconsumer;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/************************************************************************************
 * 功能描述：
 *
 * 生产者与消费者模式的实现
 * 方式：对象的wait() / notify()
 * 记住几个方法：
 * wait():当前线程释放锁，直到等到通知，再去获取锁
 * sleep():当前线程休眠，但不释放锁
 * notify():唤醒其他正在wait的线程
 *
 * 性能简单分析：
 * 由于是对队列加锁，同一时刻，要么在放入，要么在取出，两者不能同时进行，
 * 但实际上，我们期望在条件满足的时候，两者可以同时进行，
 * 即假如队列容量为10，在容量为4的时候，生产者可以放入，消费者也可以同时取出
 *
 *
 *
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月19日 --  下午6:20 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ProducerConsumer1 {

    class Producer extends Thread {
        private String threadName;
        private Queue<Goods> queue;
        private int maxSize;

        public Producer(String threadName, Queue<Goods> queue, int maxSize) {
            this.threadName = threadName;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                //模拟生产过程中的耗时操作
                Goods goods = new Goods();
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("队列已满，【" + threadName + "】进入等待状态");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(goods);
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                    queue.notifyAll();
                }
            }
        }
    }

    class Consumer extends Thread {
        private String threadName;
        private Queue<Goods> queue;

        public Consumer(String threadName, Queue<Goods> queue) {
            this.threadName = threadName;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                Goods goods;
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("队列已空，【" + threadName + "】进入等待状态");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    goods = queue.remove();
                    System.out.println("【" + threadName + "】消费了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                    queue.notifyAll();
                }
                //模拟消费过程中的耗时操作
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void test() {

        int maxSize = 5;
        Queue<Goods> queue = new LinkedList<>();

        Thread producer1 = new Producer("生产者1", queue, maxSize);
        Thread producer2 = new Producer("生产者2", queue, maxSize);
        Thread producer3 = new Producer("生产者3", queue, maxSize);

        Thread consumer1 = new Consumer("消费者1", queue);
        Thread consumer2 = new Consumer("消费者2", queue);


        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();

        while (true) {

        }
    }
}

