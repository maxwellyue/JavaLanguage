package com.maxwell.learning.common;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ThreadGroupExample {

    public static void main(String[] args) {
        Thread curThread = Thread.currentThread();
        ThreadGroup curGroup = curThread.getThreadGroup();
        System.out.println(String.format("thread name is [%s], threadGroup name is [%s] ", curThread.getName(), curGroup.getName()));
    }
}
