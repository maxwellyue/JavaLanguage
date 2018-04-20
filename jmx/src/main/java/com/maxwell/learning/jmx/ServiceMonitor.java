package com.maxwell.learning.jmx;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ServiceMonitor implements ServiceMonitorMBean{

    private MyService service;

    public ServiceMonitor(MyService service){
        this.service = service;
    }

    @Override
    public void start() {
        System.out.println("通过JMX调用启动方法");
        if(service != null && (service.getServiceStatus() == MyService.ServiceStatus.NEW || service.getServiceStatus() == MyService.ServiceStatus.TERMINATED )){
            service.start();
        }
    }

    @Override
    public void stop() {
        System.out.println("通过JMX调用关闭方法");
        if(service != null && service.getServiceStatus() == MyService.ServiceStatus.RUNNING){
            service.stop();
        }
    }

    @Override
    public String getServiceStatus() {
        System.out.println("通过JMX获取服务状态");
        return service.getServiceStatus().toString();
    }
}
