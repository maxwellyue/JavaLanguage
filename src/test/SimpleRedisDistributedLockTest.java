import org.junit.jupiter.api.Test;

public class SimpleRedisDistributedLockTest {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                //创建zookeeper的客户端
                String localhost = "127.0.0.1";
                int port = 6379;
                String lockKey = "lock_test";
                int expire = 10000;

                main.java.distributedlock.SimpleRedisDistributedLock lock = new main.java.distributedlock.SimpleRedisDistributedLock(localhost, port, lockKey, expire);
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
