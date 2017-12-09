package Concurrency;

import java.util.Map;
import java.util.concurrent.*;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月09日 --  下午1:01 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class CyclicBarriarExapmle {

    private Map<String, Integer> map = new ConcurrentHashMap<>();

    CyclicBarrier barrier = new CyclicBarrier(10, ()->{
        //线程全部到达屏障后，执行的任务
        System.out.println("我是线程全部到达屏障后，执行的任务");
        int result = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            result += entry.getValue();
        }
        System.out.println("最终计算结果：" + result);
    });

    private void calculate(){
        for(int i = 0; i < 10; i++){
            final int j = i;
            new Thread(()->{
                //执行计算，假如计算结果是，计算完成后，放入map中
                System.out.println("当前计算结果：" + j);
                map.put(Thread.currentThread().getName(), j);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void main(String[] args){
        CyclicBarriarExapmle exapmle = new CyclicBarriarExapmle();
        exapmle.calculate();
    }
}
