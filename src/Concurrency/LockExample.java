package Concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月10日 --  上午11:46 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class LockExample {

    public static void main(String[] args){
        Lock lock = null;
        ReadWriteLock readWriteLock;
        AbstractOwnableSynchronizer synchronizer=  null;

        ReentrantLock reentrantLock;
        ReentrantReadWriteLock reentrantReadWriteLock;

    }



    public interface Locka {

        /**
         * 获取锁
         *
         * 如果当前线程无法获取到锁（可能其他线程正在持有锁），则当前线程就会休眠，直到获取到锁
         */
        void lock();

        /**
         *
         * 可中断地获取锁
         *
         * 如果如果当前线程无法获取到锁（可能其他线程正在持有锁），则当前线程就会休眠，
         * 直到发生下面两种情况的任意一种：
         * ①获取到了锁
         * ②被其他线程中断
         *
         *
         * @throws InterruptedException
         */
        void lockInterruptibly() throws InterruptedException;


        /**
         * 尝试非阻塞地获取锁
         *
         * lock()和lockInterruptibly()在获取不到锁的时候，都会阻塞当前线程，直到获取到锁
         * 而该方法则不会阻塞线程，能立即获取到锁则返回true，获取不到则立即返回false
         *
         * 该方法的常用方式如下：
         *
         * Lock lock = ...;
         * if (lock.tryLock()) {
         *   try {
         *     // manipulate protected state
         *   } finally {
         *     lock.unlock();
         *   }
         * } else {
         *   // perform alternative actions
         * }}
         *
         * 这种使用方式，可以保证只在获取到锁的时候才去释放锁
         *
         */
        boolean tryLock();

        /**
         *
         * 超时获取锁
         *
         * 当前线程在以下三种情况下会返回：
         * ①当前线程在超时时间内获取到了锁，返回true
         * ②当前线程在超时时间内被中断，返回false（即该方法可以响应其他线程对该线程的中断操作）
         * ③超时时间结束，没有获取到锁，返回false
         *
         * @param time
         * @param unit
         * @return
         * @throws InterruptedException
         */
        boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

        /**
         * 释放锁
         *
         */
        void unlock();


        /**
         * 获取与该锁绑定的Condition
         *
         * 当前线程只有在获得了锁，才能调用Condition的wait()方法（表示我已经到了某一条件），
         * 调用Condition的wait()方法之后，当前线程会释放锁
         *
         */
        Condition newCondition();
    }
}
