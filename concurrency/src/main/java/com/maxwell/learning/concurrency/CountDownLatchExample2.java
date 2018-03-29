package com.maxwell.learning.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月09日 --  下午12:07 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class CountDownLatchExample2 {

    public void doSomething() {

        CountDownLatch doneSignal = new CountDownLatch(100);

        Executor e = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; ++i) {
            e.execute(new WorkerRunnable(doneSignal, i));
        }

        try {
            doneSignal.await();           // 等待所有任务结束
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void doSomeThingAfterAllThreadsProcess() {
        //所有任务开始前，做一些准备工作
    }

    private void doSomeThingBeforeAllThreadsProcess() {
        //所有任务开始后，做一些其他工作，如合并结果等等
    }

    class WorkerRunnable implements Runnable {

        private final CountDownLatch doneSignal;
        private final int i;

        WorkerRunnable(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        @Override
        public void run() {
            doWork(i);
            doneSignal.countDown();
        }

        void doWork(int i) {
            //这里是真正的有意义的任务
        }
    }
}

