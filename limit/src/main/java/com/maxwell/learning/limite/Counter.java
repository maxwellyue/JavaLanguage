package com.maxwell.learning.limite;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * 限制某个接口的总请求数
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Counter {

    /**
     * 请求数量计数器
     */
    private AtomicLong counter = new AtomicLong(0);
    /**
     * 请求数量限制量
     */
    private final long limit;

    public Counter(long limit){
        this.limit = limit;
    }

    public boolean grant(){
        return counter.incrementAndGet() <= limit;
    }
}
