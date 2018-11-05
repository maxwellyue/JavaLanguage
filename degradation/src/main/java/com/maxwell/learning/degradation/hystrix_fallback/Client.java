package com.maxwell.learning.degradation.hystrix_fallback;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EchoService service = new EchoService();
        for (;;){
            EchoTimeCommand command = new EchoTimeCommand(service);
            String time = command.execute();
            System.out.println(time);
        }
    }

    /*@Test
    public void testFailureConcurrent(){
        EchoService service = new EchoService();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (;;){
            executor.submit(()->{
                EchoTimeCommand command = new EchoTimeCommand(service);
                String time = command.execute();
                System.out.println(time);
            });
        }
    }*/


}
