package com.maxwell.learning.bufferqueue;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 双缓冲队列
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DoubleBufferQueue<T> extends AbstractQueue<T> implements Queue<T> {

    private Lock readLock = new ReentrantLock();
    private Lock writeLock = new ReentrantLock();

    /**
     * 读队列，出队的时候，从该队列取出元素
     */
    private LinkedList<T> readQueue = new LinkedList<T>();
    /**
     * 写队列，入队的时候，从该队列放入元素
     */
    private LinkedList<T> writeQueue = new LinkedList<T>();

    public DoubleBufferQueue() {
        super();
    }

    /**
     * 添加元素到队尾
     */
    @Override
    public boolean offer(T e) {
        writeLock.lock();
        try {
            return writeQueue.offer(e);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 移除并返问队头元素
     *
     * 当读队列为空时，交换队列
     */
    @Override
    public T poll() {
        readLock.lock();
        try {
            if (readQueue.size() == 0) {
                swap();
            }

            return readQueue.poll();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 返回队头元素（不移除）
     */
    @Override
    public T peek() {
        readLock.lock();
        try {
            if (readQueue.size() == 0) {
                swap();
            }
            return readQueue.peek();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 增加元素到队尾
     */
    @Override
    public boolean add(T e) {
        writeLock.lock();
        try {
            return writeQueue.add(e);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 批量增加元素到队尾
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        writeLock.lock();
        try {
            return writeQueue.addAll(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Iterator<T> iterator() {
        throw new NotImplementedException();
    }

    @Override
    public int size() {
        readLock.lock();
        writeLock.lock();
        try {
            return readQueue.size() + writeQueue.size();
        } finally {
            try {
                writeLock.unlock();
            } finally {
                readLock.unlock();
            }
        }
    }

    /**
     * 读队列和写队列交换
     */
    private void swap() {
        writeLock.lock();
        try {
            if (writeQueue.size() > 0) {
                LinkedList<T> tmp = readQueue;
                readQueue = writeQueue;
                writeQueue = tmp;
                tmp = null;
            }
        } finally {
            writeLock.unlock();
        }
    }
}