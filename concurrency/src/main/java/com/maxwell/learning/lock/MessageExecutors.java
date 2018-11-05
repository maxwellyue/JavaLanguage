package com.maxwell.learning.lock;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MessageExecutors {


    @Test
    public void test() throws InterruptedException {

        ExecutorService executors = Executors.newFixedThreadPool(5);

        MessageQueueLock queueLock = new MessageQueueLock();

        int queueSize = 3;
        int messageSize = 20;

        List<Message> messageList = new ArrayList<Message>();
        Map<Integer, MessageQueue> map = new HashMap<>();
        for (int i = 0; i < queueSize; i++) {
            map.put(i, new MessageQueue(i));
        }

        for (int i = 0; i < messageSize; i++) {
            int messageQueueId = i % queueSize;
            MessageQueue queue = map.get(messageQueueId);
            messageList.add(new Message(queue, i + ""));
        }

        List<String> results = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(messageSize);

        for(Message message : messageList){
            executors.execute(() -> {
                try {
                    final Object lock = queueLock.fetchLockObject(message.getMessageQueue());
                    synchronized (lock) {
                        String threadName = Thread.currentThread().getName();
                        results.add(String.format("线程[%s]:消息:[queue id is %d, message is %s]", threadName, message.getMessageQueue().getId(), message.getMessage()));
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Collections.sort(results);
        results.forEach(result -> {
            System.out.println(result);
        });

    }

}
