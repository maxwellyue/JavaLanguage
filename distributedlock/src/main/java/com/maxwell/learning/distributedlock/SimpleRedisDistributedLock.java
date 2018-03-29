package com.maxwell.learning.distributedlock;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月25日 --  下午7:12 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class SimpleRedisDistributedLock implements Lock {

    private Jedis jedis;
    private String lockKey;
    private int expireTime;//锁的过期时间，防止客户端挂掉造成死锁，单位：毫秒
    private String value = UUID.randomUUID().toString();
    private static final String OPT_SUCCESS = "OK";

    public SimpleRedisDistributedLock(String localhost, Integer port, String lockKey, int expireTime) {
        //建立redis连接
        this.jedis = new Jedis(localhost, port);
        this.lockKey = lockKey;
        this.expireTime = expireTime;
    }


    @Override
    public void lock() {
        boolean success = false;
        while (!success) {
            String result = jedis.set(lockKey, value, "NX", "PX", expireTime);
            if (OPT_SUCCESS.equals(result)) {
                success = true;
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        String result = jedis.set(lockKey, value, "NX", "PX", expireTime);
        return OPT_SUCCESS.equals(result);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long curTime = System.currentTimeMillis();
        long waitTime = TimeUnit.MILLISECONDS.convert(time, unit);
        while (System.currentTimeMillis() - curTime <= waitTime) {
            String result = jedis.set(lockKey, value, "NX", "PX", expireTime);
            if (OPT_SUCCESS.equals(result)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unlock() {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(value));
        jedis.close();
    }

    @Override
    public Condition newCondition() {
        return null;
    }


}
