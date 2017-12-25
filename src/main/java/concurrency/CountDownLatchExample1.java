package main.java.concurrency;

import java.util.concurrent.CountDownLatch;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月09日 --  下午12:07 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class CountDownLatchExample1 {

    public void doSomething() {

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(10);

        //创建并启动线程
        for (int i = 0; i < 10; ++i) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        doSomeThingBeforeAllThreadsProcess();
        startSignal.countDown();      //让之前for循环创建的线程开始真正工作
        try {
            doneSignal.await();       // 等待之前for循环创建的线程执行结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        doSomeThingAfterAllThreadsProcess();
    }

    private void doSomeThingAfterAllThreadsProcess() {
        //所有任务开始前，做一些准备工作
    }

    private void doSomeThingBeforeAllThreadsProcess() {
        //所有任务开始后，做一些其他工作，如合并结果等等
    }

    class Worker implements Runnable {

        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();//等待，开始信号为0再继续向下进行
                doWork();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }finally {
                doneSignal.countDown();//完成后，将结束信号减1
            }
        }

        void doWork() {
            //这里是真正有意义的任务
        }
    }
}

