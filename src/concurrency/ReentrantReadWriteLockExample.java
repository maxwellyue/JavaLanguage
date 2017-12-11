package concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月10日 --  下午2:17 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class ReentrantReadWriteLockExample {

}

class Cache{

    //非线程安全的HashMap
    private static Map<String, Object> map = new HashMap<>();
    //读写锁
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //读锁
    private static Lock readLock = reentrantReadWriteLock.readLock();
    //写锁
    private static Lock writeLock = reentrantReadWriteLock.writeLock();

    /**
     * 获取key对应的value
     *
     * 使用读锁，使得并发访问该方法时不会被阻塞
     */
    public static final Object get(String key){
        readLock.lock();
        try{
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 设置key对应的value
     *
     * 当有线程对map进行put操作时，使用写锁，阻塞其他线程的读、写操作，
     * 只有在写锁被释放后，其他读写操作才能继续
     */
    public static Object put(String key, Object value){
        writeLock.lock();
        try {
            return map.put(key, value);
        }finally {
            writeLock.unlock();
        }
    }

    /**
     * 清空map
     *
     * 当有线程对map进行清空操作时，使用写锁，阻塞其他线程的读、写操作，
     * 只有在写锁被释放后，其他读写操作才能继续
     */
    public static void clear(){
        writeLock.lock();
        try {
            map.clear();
        }finally {
            writeLock.unlock();
        }
    }
}