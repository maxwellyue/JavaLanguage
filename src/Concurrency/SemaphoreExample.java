package Concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月09日 --  下午3:42 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class SemaphoreExample {

    private Semaphore semaphore = new Semaphore(10);

    private Executor executor = Executors.newFixedThreadPool(30);

    public void calculate(){

        for(int i = 0; i < 30; i++){
            executor.execute(()->{
                try {
                    //获取许可证
                    semaphore.acquire();
                    //执行计算
                    System.out.println("使用资源，执行任务");
                    //释放许可证
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
