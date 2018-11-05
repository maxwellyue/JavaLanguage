package com.maxwell.learning.common;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DaemonThreadExample {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("daemon finally run ");
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("main thread end, jvm will close...");
    }
}
