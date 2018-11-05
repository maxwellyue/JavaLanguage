package com.maxwell.learning.common;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ThreadExample {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                double d = 0.0;
                while (!Thread.interrupted()) {
                    for (int i = 0; i < 900000; i++) {
                        d = d + (Math.PI + Math.E) / d;
                    }
                    System.out.println("i am running...");
                }
                System.out.println("interrupted...");
            }
        });

        thread.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断线程
        thread.interrupt();
    }


}
