package com.maxwell.learning.degradation.hystrix_circuitbreaker;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EchoService service = new EchoService();
        int count = 0 ;
        for (;;){
            EchoTimeCommand command = new EchoTimeCommand(service);
            String time = command.execute();
            if(command.isCircuitBreakerOpen()){
                count++;
                if(count > 5){
                    Thread.sleep(2000);
                }
                System.out.println("[alert!!! alert!!! alert!!! circuit breaker is opened]");
            }
            System.out.println(time);
        }
    }
}
