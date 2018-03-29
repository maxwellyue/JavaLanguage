package com.maxwell.learning.distributedlock;

import org.junit.jupiter.api.Test;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yueyuemax@gmail.com
 * 创建时间： 2018年03月29日 --  下午6:27 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
class SimpleRedisDistributedLockTest {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                //创建zookeeper的客户端
                String localhost = "127.0.0.1";
                int port = 6379;
                String lockKey = "lock_test";
                int expire = 10000;

                SimpleRedisDistributedLock lock = new SimpleRedisDistributedLock(localhost, port, lockKey, expire);
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获取到了锁，开始干活");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "干活完毕");
                try {
                    System.out.println(Thread.currentThread().getName() + "释放锁");
                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.setName("【线程 " + i + "】");
            thread.start();
        }

        while (true) {
        }

    }
}