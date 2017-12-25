package main.java.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月10日 --  下午3:07 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ConditionExample {

    Condition condition;
    ArrayBlockingQueue arrayBlockingQueue;
}

class BoundedBlockingQueue<T> {
    //使用数组维护队列
    private Object[] queue;
    //当前数组中的元素个数
    private int count = 0;
    //当前添加元素到数组的位置
    private int addIndex = 0;
    //当前移除元素在数组中的位置
    private int removeIndex = 0;

    private Lock lock = new ReentrantLock();
    private Condition notEmptyCondition = lock.newCondition();
    private Condition notFullCondition = lock.newCondition();


    private BoundedBlockingQueue() {
    }

    public BoundedBlockingQueue(int capacity) {
        queue = new Object[capacity];
    }

    public void put(T t) throws InterruptedException {
        lock.lock();//获得锁，保证内部数组修改的可见性和排他性
        try {
            //使用while，而非if：防止过早或意外的通知，
            //加入当前线程释放了锁进入等待状态，然后其他线程进行了signal，
            //则当前线程会从await()方法中返回，再次判断count == queue.length
            //todo：哪些情况下的过早或意外？？？
            while (count == queue.length) {
                notFullCondition.await();//释放锁，等待队列不满，即等待队列出现空位
            }
            queue[addIndex] = t;
            addIndex++;
            if (addIndex == queue.length) {
                addIndex = 0;
            }
            count++;
            notEmptyCondition.signal();
        } finally {
            //确保会释放锁
            lock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmptyCondition.await();//释放锁，等待队列不为空，即等待队列中至少有一个元素
            }
            Object x = queue[removeIndex];
            removeIndex++;
            if (removeIndex == queue.length) {
                removeIndex = 0;
            }
            count--;
            notFullCondition.signal();//通知那些等待队列非空的线程，可以向队列中插入元素了
            return (T) x;
        } finally {
            //确保会释放锁
            lock.unlock();
        }
    }
}

        /*interface Condition {

         *//**
 * 当前线程进入等待状态直到被通知（signalled）或中断(interrupted)
 * <p>
 * 如果当前线程从该方法返回，则表明当前线程已经获取了Condition对象所对应的锁
 *
 * @throws InterruptedException
 * <p>
 * 与await()不同是：该方法对中断操作不敏感
 * <p>
 * 如果当前线程在等待的过程中被中断，当前线程仍会继续等待，直到被通知(signalled)，
 * 但当前线程会保留线程的中断状态值
 * <p>
 * <p>
 * 当前线程进入等待状态，直到被通知或被中断或超时
 * <p>
 * 返回值表示剩余时间，
 * 如果当前线程在nanosTimeout纳秒之前被唤醒，那么返回值就是（nanosTimeout-实际耗时），
 * 如果返回值是0或者负数，则表示等待已超时
 * <p>
 * <p>
 * 该方法等价于awaitNanos(unit.toNanos(time)) > 0
 * <p>
 * 当前线程进入等待状态，直到被通知或被中断或到达时间点deadline
 * <p>
 * 如果在没有到达截止时间就被通知，返回true
 * 如果在到了截止时间仍未被通知，返回false
 * <p>
 * 唤醒一个等待在Condition上的线程
 * 该线程从等待方法返回前必须获得与Condition相关联的锁
 * <p>
 * 唤醒所有等待在Condition上的线程
 * 每个线程从等待方法返回前必须获取Condition相关联的锁
 *//*
    void await() throws InterruptedException;

    *//**
 * 与await()不同是：该方法对中断操作不敏感
 *
 * 如果当前线程在等待的过程中被中断，当前线程仍会继续等待，直到被通知(signalled)，
 * 但当前线程会保留线程的中断状态值
 *
 *//*
    void awaitUninterruptibly();

    *//**
 * 当前线程进入等待状态，直到被通知或被中断或超时
 *
 * 返回值表示剩余时间，
 * 如果当前线程在nanosTimeout纳秒之前被唤醒，那么返回值就是（nanosTimeout-实际耗时），
 * 如果返回值是0或者负数，则表示等待已超时
 *
 *//*
    long awaitNanos(long nanosTimeout) throws InterruptedException;

    *//**
 * 该方法等价于awaitNanos(unit.toNanos(time)) > 0
 *//*
    boolean await(long time, TimeUnit unit) throws InterruptedException;

    *//**
 * 当前线程进入等待状态，直到被通知或被中断或到达时间点deadline
 *
 * 如果在没有到达截止时间就被通知，返回true
 * 如果在到了截止时间仍未被通知，返回false
 *//*
    boolean awaitUntil(Date deadline) throws InterruptedException;

    *//**
 * 唤醒一个等待在Condition上的线程
 * 该线程从等待方法返回前必须获得与Condition相关联的锁
 *//*
    void signal();

    *//**
 * 唤醒所有等待在Condition上的线程
 * 每个线程从等待方法返回前必须获取Condition相关联的锁
 *//*
    void signalAll();
}*/
