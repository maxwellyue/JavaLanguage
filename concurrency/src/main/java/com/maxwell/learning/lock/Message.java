package com.maxwell.learning.lock;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Message {

    private MessageQueue messageQueue;

    private String message;

    public Message(MessageQueue messageQueue, String message) {
        this.messageQueue = messageQueue;
        this.message = message;
    }

    public MessageQueue getMessageQueue() {
        return this.messageQueue;
    }

    public void setMessageQueue(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
