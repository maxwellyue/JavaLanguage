package com.maxwell.learning.producerconsumer;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/************************************************************************************
 * 功能描述：
 * 生产者与消费者模式的实现
 * 方式：组合Lock和Condition
 * 记住Condition的两个方法：
 * await():等待Condition的满足，会释放锁
 * signal():唤醒其他正在等待该Conditon的线程
 *
 * 性能简单分析：
 * 在本实现中，使用了两个Condition，来控制队列的不空和溢出问题，
 * 但仍是使用的同一个锁，性能与使用wait() / notify()类似。
 *
 * 如果要实现同一时刻，既可以放入又可以取出，则要使用两个重入锁，分别控制放入和取出的操作，
 * 具体实现可以参考LinkedBlockingQueue
 *
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月19日 --  下午7:31 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ProducerConsumer2 {

    class Producer extends Thread {

        private String threadName;
        private Queue<Goods> queue;
        private Lock lock;
        private Condition notFullCondition;
        private Condition notEmptyCondition;
        private int maxSize;

        public Producer(String threadName, Queue<Goods> queue, Lock lock, Condition notFullCondition, Condition notEmptyCondition, int maxSize) {
            this.threadName = threadName;
            this.queue = queue;
            this.lock = lock;
            this.notFullCondition = notFullCondition;
            this.notEmptyCondition = notEmptyCondition;
            this.maxSize = maxSize;

        }

        @Override
        public void run() {
            while (true) {
                //模拟生产过程中的耗时操作
                Goods goods = new Goods();
                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.lock();
                try {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("队列已满，【" + threadName + "】进入等待状态");
                            notFullCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.add(goods);
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                    notEmptyCondition.signalAll();

                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer extends Thread {
        private String threadName;
        private Queue<Goods> queue;
        private Lock lock;
        private Condition notFullCondition;
        private Condition notEmptyCondition;

        public Consumer(String threadName, Queue<Goods> queue, Lock lock, Condition notFullCondition, Condition notEmptyCondition) {
            this.threadName = threadName;
            this.queue = queue;
            this.lock = lock;
            this.notFullCondition = notFullCondition;
            this.notEmptyCondition = notEmptyCondition;
        }

        @Override
        public void run() {
            while (true) {
                Goods goods;
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("队列已空，【" + threadName + "】进入等待状态");
                            notEmptyCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    goods = queue.remove();
                    System.out.println("【" + threadName + "】消费了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                    notFullCondition.signalAll();

                } finally {
                    lock.unlock();
                }

                //模拟消费过程中的耗时操作
                try {
                    Thread.sleep(new Random().nextInt(100));
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
        Lock lock = new ReentrantLock();
        Condition notEmptyCondition = lock.newCondition();
        Condition notFullCondition = lock.newCondition();

        Thread producer1 = new ProducerConsumer2.Producer("生产者1", queue, lock, notFullCondition, notEmptyCondition, maxSize);
        Thread producer2 = new ProducerConsumer2.Producer("生产者2", queue, lock, notFullCondition, notEmptyCondition, maxSize);
        Thread producer3 = new ProducerConsumer2.Producer("生产者3", queue, lock, notFullCondition, notEmptyCondition, maxSize);

        Thread consumer1 = new ProducerConsumer2.Consumer("消费者1", queue, lock, notFullCondition, notEmptyCondition);
        Thread consumer2 = new ProducerConsumer2.Consumer("消费者2", queue, lock, notFullCondition, notEmptyCondition);
        Thread consumer3 = new ProducerConsumer2.Consumer("消费者3", queue, lock, notFullCondition, notEmptyCondition);


        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        while (true) {

        }
    }


}
