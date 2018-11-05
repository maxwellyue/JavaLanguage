package com.maxwell.learning.limite;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 对时间窗口内的请求限制
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MultiPeriodCounter {

    private List<PeriodLimiter> periodLimiters;

    private Map<PeriodLimiter, LoadingCache<Long, AtomicLong>> counters;


    public MultiPeriodCounter(List<PeriodLimiter> periodLimiters) {
        if (periodLimiters == null || periodLimiters.size() == 0) {
            throw new RuntimeException("限制器不能为空");
        }
        this.periodLimiters = periodLimiters;
        for (PeriodLimiter limiter : periodLimiters) {
            LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
                    .expireAfterWrite(2, limiter.getTimeUnit())
                    .build(new CacheLoader<Long, AtomicLong>() {
                        @Override
                        public AtomicLong load(Long aLong) throws Exception {
                            //重新获取初始值为0
                            return new AtomicLong(0);
                        }
                    });
            counters.put(limiter, counter);
        }
    }

    public boolean permit() throws ExecutionException {
        while (true) {
            boolean res = true;
            //获取当前秒
            long currentSecond = System.currentTimeMillis() / 1000;
            System.out.println(currentSecond);

            for (PeriodLimiter limiter : periodLimiters) {
                TimeUnit timeUnit = limiter.getTimeUnit();
                switch (timeUnit) {
                    case SECONDS:
                        if(counters.get(timeUnit).get(currentSecond).incrementAndGet() > limiter.getLimit()){
                            return false;
                        }
                        break;
                    case MINUTES:
                        if(counters.get(timeUnit).get(TimeUnit.SECONDS.toMinutes(currentSecond)).incrementAndGet() > limiter.getLimit()){
                            return false;
                        }
                        break;
                    case HOURS:
                        if(counters.get(timeUnit).get(TimeUnit.SECONDS.toHours(currentSecond)).incrementAndGet() > limiter.getLimit()){
                            return false;
                        }
                        break;
                    case DAYS:
                        if(counters.get(timeUnit).get(TimeUnit.SECONDS.toDays(currentSecond)).incrementAndGet() > limiter.getLimit()){
                            return false;
                        }
                        break;
                    default:
                        break;
                }

            }


            return res;
        }
    }





    public class PeriodLimiter {

        private TimeUnit timeUnit;
        private long limit;

        public PeriodLimiter(TimeUnit timeUnit, long limit) {
            this.timeUnit = timeUnit;
            this.limit = limit;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }

        public long getLimit() {
            return limit;
        }

        public void setLimit(long limit) {
            this.limit = limit;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
}
