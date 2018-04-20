package com.maxwell.learning.jmx;

/**
 * @author yuezengcun <yueyuemax@gamil.com>
 * @since
 */
public interface ServiceMonitorMBean {


    /**
     * 开启服务
     */
    void start();

    /**
     * 关闭服务
     */
    void stop();

    /**
     * 获取服务的状态
     *
     * @return
     */
    String getServiceStatus();

}
