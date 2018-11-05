package com.maxwell.learning.degradation.hystrix_circuitbreaker;

import com.netflix.hystrix.*;

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
     * 熔断配置
     *
     * @return
     */
    private static Setter setter() {
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("echo_service");
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("echo_time");

        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                // 是否开启熔断机制，默认为true
                .withCircuitBreakerEnabled(true)
                // 是否强制打开熔断器，默认false
                .withCircuitBreakerForceOpen(false)
                // 是否强制关闭熔断器，默认false
                .withCircuitBreakerForceClosed(false)
                // 至少有n个请求才计算错误比率，默认20
                .withCircuitBreakerRequestVolumeThreshold(3)
                // 对Command进行熔断的出错率阈值，超过这个阈值，则会将打开熔断器
                .withCircuitBreakerErrorThresholdPercentage(50)
                // 统计滚动的时间窗口
                .withMetricsRollingStatisticalWindowInMilliseconds(5000)
                // 开启熔断后的静默时间，超过这个时间，就会重新放一个请求进去，如果请求成功的话就关闭熔断，失败就再等一段时间，默认5000
                .withCircuitBreakerSleepWindowInMilliseconds(2000)
                // 为了便于观察，关闭超时时的线程中断
                .withExecutionIsolationThreadInterruptOnTimeout(false);

        return Setter
                .withGroupKey(groupKey)
                .andCommandKey(commandKey)
                .andCommandPropertiesDefaults(commandProperties);
    }
}
