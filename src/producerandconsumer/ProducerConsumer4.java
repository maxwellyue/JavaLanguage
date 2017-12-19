package producerandconsumer;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

/************************************************************************************
 * 功能描述：
 *
 * 生产者与消费者模型的Java实现
 *
 * 使用信号量：Semaphore
 *
 * 理解代码中的三个信号量的含义：
 * queueSizeSemaphore：（其中的许可证数量，可以理解为队列中可以再放入多少个元素）
 *      该信号量的许可证初始数量为仓库大小，即maxSize，
 *      生产者每放置一个商品，则该信号量-1，即执行acquire()，表示队列中已经添加了一个元素，要减少一个许可证
 *      消费者每取出一个商品，该信号量+1，即执行release()，表示队列中已经少了一个元素，再给你一个许可证
 * notEmptySemaphore：（其中的许可证数量，可以理解为队列中可以取出多少个元素）
 *      该信号量的许可证初始数量为0
 *      生产者每放置一个商品，则该信号量+1，即执行release()，表示队列中添加了一个元素
 *      消费者每取出一个商品，该信号量-1，即执行acquire()，表示队列中已经少了一个元素，要减少一个许可证
 * concurrentWriteSemaphore
 *      相当于一个写锁，在放入或取出商品的时候，都需要先获取再释放许可证。
 *
 * 要特别留意Semaphore的release方法：
 *      一般在使用锁的时候，习惯于先lock，再unlock，或先。但是Semaphore在release之前不必一定要先acquire。
 *      源码中的注释说的很清楚：
 *      There is no requirement that a thread that releases a permit must
 *      have acquired that permit by calling acquire.
 *      Correct usage of a semaphore is established by programming convention
 *      in the application.
 *
 *
 *
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月19日 --  下午9:04 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ProducerConsumer4 {


    class Producer extends Thread {
        private String threadName;
        private Queue<Goods> queue;
        private Semaphore queueSizeSemaphore;
        private Semaphore concurrentWriteSemaphore;
        private Semaphore notEmptySemaphore;

        public Producer(String threadName, Queue<Goods> queue, Semaphore concurrentWriteSemaphore, Semaphore queueSizeSemaphore, Semaphore notEmptySemaphore) {
            this.threadName = threadName;
            this.queue = queue;
            this.concurrentWriteSemaphore = concurrentWriteSemaphore;
            this.queueSizeSemaphore = queueSizeSemaphore;
            this.notEmptySemaphore = notEmptySemaphore;
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

                try {
                    queueSizeSemaphore.acquire();//获取队列未满的信号量
                    concurrentWriteSemaphore.acquire();//获取读写的信号量
                    queue.add(goods);
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    concurrentWriteSemaphore.release();
                    notEmptySemaphore.release();
                }
            }
        }
    }

    class Consumer extends Thread {
        private String threadName;
        private Queue<Goods> queue;
        private Semaphore queueSizeSemaphore;
        private Semaphore concurrentWriteSemaphore;
        private Semaphore notEmptySemaphore;

        public Consumer(String threadName, Queue<Goods> queue, Semaphore concurrentWriteSemaphore, Semaphore queueSizeSemaphore, Semaphore notEmptySemaphore) {
            this.threadName = threadName;
            this.queue = queue;
            this.concurrentWriteSemaphore = concurrentWriteSemaphore;
            this.queueSizeSemaphore = queueSizeSemaphore;
            this.notEmptySemaphore = notEmptySemaphore;
        }

        @Override
        public void run() {
            while (true) {
                Goods goods;
                try {
                    notEmptySemaphore.acquire();
                    concurrentWriteSemaphore.acquire();
                    goods = queue.remove();
                    System.out.println("【" + threadName + "】生产了一个商品：【" + goods.toString() + "】，目前商品数量：" + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    concurrentWriteSemaphore.release();
                    queueSizeSemaphore.release();
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
        Semaphore concurrentWriteSemaphore = new Semaphore(1);
        Semaphore notEmptySemaphore = new Semaphore(0);
        Semaphore queueSizeSemaphore = new Semaphore(maxSize);


        Thread producer1 = new ProducerConsumer4.Producer("生产者1", queue, concurrentWriteSemaphore, queueSizeSemaphore, notEmptySemaphore);
        Thread producer2 = new ProducerConsumer4.Producer("生产者2", queue, concurrentWriteSemaphore, queueSizeSemaphore, notEmptySemaphore);
        Thread producer3 = new ProducerConsumer4.Producer("生产者3", queue, concurrentWriteSemaphore, queueSizeSemaphore, notEmptySemaphore);

        Thread consumer1 = new ProducerConsumer4.Consumer("消费者1", queue, concurrentWriteSemaphore, queueSizeSemaphore, notEmptySemaphore);
        Thread consumer2 = new ProducerConsumer4.Consumer("消费者2", queue, concurrentWriteSemaphore, queueSizeSemaphore, notEmptySemaphore);


        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();

        while (true) {

        }
    }




}
