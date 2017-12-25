package main.java.producerandconsumer;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/************************************************************************************
 * 功能描述：
 *
 * 生产者消费者模型
 * 使用jdk自带的阻塞队列实现
 * 记住两个阻塞取放方法：
 * put():阻塞地放入元素
 * take():阻塞地取出元素
 *
 * 性能简单分析：
 * 如果使用LinkedBlockingQueue作为队列实现，则可以实现：
 * 在同一时刻，既可以放入又可以取出，因为LinkedBlockingQueue内部使用了两个重入锁，分别控制取出和放入。
 * 性能优于wait() / notify()方式实现。
 *
 * 如果使用ArrayBlockingQueue，则在同一时刻只能放入或取出，因为ArrayBlockingQueue内部只使用了一个重入锁来控制并发修改操作。
 *
 *
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月19日 --  下午8:02 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ProducerConsumer3 {

    class Producer extends Thread {
        private String threadName;
        private BlockingQueue<Goods> queue;

        public Producer(String threadName, BlockingQueue<Goods> queue) {
            this.threadName = threadName;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true){
                Goods goods = new Goods();
                try {
                    //模拟生产过程中的耗时操作
                    Thread.sleep(new Random().nextInt(100));
                    queue.put(goods);
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer extends Thread {
        private String threadName;
        private BlockingQueue<Goods> queue;

        public Consumer(String threadName, BlockingQueue<Goods> queue) {
            this.threadName = threadName;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Goods goods = queue.take();
                    System.out.println("【" + threadName + "】消费了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                    //模拟消费过程中的耗时操作
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
        BlockingQueue<Goods> queue = new LinkedBlockingQueue<>(maxSize);

        Thread producer1 = new ProducerConsumer3.Producer("生产者1", queue);
        Thread producer2 = new ProducerConsumer3.Producer("生产者2", queue);
        Thread producer3 = new ProducerConsumer3.Producer("生产者3", queue);

        Thread consumer1 = new ProducerConsumer3.Consumer("消费者1", queue);
        Thread consumer2 = new ProducerConsumer3.Consumer("消费者2", queue);


        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();

        while (true) {

        }
    }


}
