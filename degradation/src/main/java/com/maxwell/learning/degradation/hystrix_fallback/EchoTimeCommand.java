package com.maxwell.learning.degradation.hystrix_fallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class EchoTimeCommand extends HystrixCommand<String> {

    private EchoService echoService;

    public EchoTimeCommand(EchoService echoService) {
        super(setter());
        this.echoService = echoService;
    }

    @Override
    protected String run() {
        return echoService.echoTime();
    }

    /**
     * 降级方法
     */
    @Override
    protected String getFallback() {
        //此处不应该再进行网络请求
        long defaultTime = 0;
        return "[fallback]time:::" + defaultTime;
    }

    /**
     *
     * 降级策略配置
     *
     * @return
     */
    private static Setter setter() {
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("echo_service");
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("echo_time");

        HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter()
                //隔离策略，默认THREAD
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                //是否允许降级，默认允许，如果允许，则在超时或异常时会调用getFallback()进行降级处理
                .withFallbackEnabled(true)
                //getFallback()的并发数，如果超出该值，就不再调用getFallback()方法，而是快速失败，默认为10
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(10)
                //当执行线程执行run()超时时，是否进行中断处理，默认false
                .withExecutionIsolationThreadInterruptOnFutureCancel(true)
                //是否开启/run()方法超时设置，默认true
                .withExecutionTimeoutEnabled(true)
                //run()方法的超时时间（单位毫秒），默认1000
                .withExecutionTimeoutInMilliseconds(1000)
                //run()方法超时的时候，是否中断run()执行，默认true
                .withExecutionIsolationThreadInterruptOnTimeout(false);

        return HystrixCommand.Setter
                .withGroupKey(groupKey)
                .andCommandKey(commandKey)
                .andCommandPropertiesDefaults(setter);
    }
}
