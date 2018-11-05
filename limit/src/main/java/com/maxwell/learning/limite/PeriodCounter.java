package com.maxwell.learning.limite;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 对时间窗口内的请求限制
 *
 * 这里仅仅是每秒请求数限制
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class PeriodCounter {

    /**
     * 每秒限制的请求数
     */
    private final long limit;

    /**
     * 键：当前时间，秒
     * 值：该秒内的累计请求量
     */
    LoadingCache<Long, AtomicLong> secondCounter = CacheBuilder.newBuilder()
            //写入2秒后删除
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long aLong) throws Exception {
                    //重新获取初始值为0
                    return new AtomicLong(0);
                }
            });

    public PeriodCounter(long limit){
        this.limit = limit;
    }

    public boolean permit() throws ExecutionException {
        while (true){
            //获取当前秒
            long currentSecond = System.currentTimeMillis() / 1000;
            System.out.println(currentSecond);
            if(secondCounter.get(currentSecond).incrementAndGet() > limit){
                return false;
            }else {
                return true;
            }
        }
    }
}
