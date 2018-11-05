package com.maxwell.learning.mq.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Example {


    @Test
    public void sendOrderedMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer();
        Message message = new Message();
        int orderId = 1000;

        SendResult sendResult = producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }
        }, orderId);

    }

    @Test
    public void send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        int queueSize = 6;

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < queueSize; i++) {
            map.put(i, new ArrayList<>());
        }

        for (int i = 1; i < 50; i++) {
            int index = i % queueSize;
            map.get(index).add(i);
        }

        map.forEach((queueIndex, msgs) -> {
            StringBuilder messages = new StringBuilder();
            msgs.forEach(msg -> {
                messages.append(msg).append(",");
            });
            System.out.println(String.format("[queue%d][%s]", queueIndex, messages.toString()));
        });

/*[queue0][3,6,9,12,15,18,21,24,27,30,33,36,39,42,45,48,]
[queue1][1,4,7,10,13,16,19,22,25,28,31,34,37,40,43,46,49,]
[queue2][2,5,8,11,14,17,20,23,26,29,32,35,38,41,44,47,]


[queue0][6,12,18,24,30,36,42,48,]
[queue1][1,7,13,19,25,31,37,43,49,]
[queue2][2,8,14,20,26,32,38,44,]
[queue3][3,9,15,21,27,33,39,45,]
[queue4][4,10,16,22,28,34,40,46,]
[queue5][5,11,17,23,29,35,41,47,]*/

    }

}
