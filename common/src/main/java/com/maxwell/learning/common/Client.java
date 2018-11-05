package com.maxwell.learning.common;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {

        ForAndWhile s = new ForAndWhile();

        java.sql.Connection connection;



        /*List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.clear();
        System.out.println(list.size());*/
    }

    /*public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(3);

        TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<String>();
        parent.set("value-set-in-parent");

        // 应用代码
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                parent.set("value-set-in-runnable");
                System.out.println("runnable:::value:::" + parent.get());
                latch.countDown();
            }
        };

        Runnable task_after = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable-after:::value:::" + parent.get());
                latch.countDown();
            }
        };

        Callable call = new Callable() {
            @Override
            public Object call() throws Exception {
                parent.set("value-set-in-callable");
                System.out.println("callable:::value:::" + parent.get());
                latch.countDown();
                return null;
            }
        };
        executorService.submit(task);
        executorService.submit(call);
        executorService.submit(task_after);

        // =====================================================

        //
        String value = parent.get();
        System.out.println("main:::value:::" + value);

        try {
            latch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/
}
