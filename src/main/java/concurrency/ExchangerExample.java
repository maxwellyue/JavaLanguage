package main.java.concurrency;

import java.util.concurrent.Exchanger;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月09日 --  下午4:03 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ExchangerExample {

    public static void main(String[] args){
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(()->{
            String resultOne = "A";
            try {
                String exchangeResult = exchanger.exchange(resultOne);
                System.out.println("我的计算结果是：" + resultOne + "，与我交换数据的那个线程计算的结果是：" + exchangeResult);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            String resultTwo = "B";
            try {
                String exchangeResult = exchanger.exchange(resultTwo);
                System.out.println("我的计算结果是：" + resultTwo + "，与我交换数据的那个线程计算的结果是：" + exchangeResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
