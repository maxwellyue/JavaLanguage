package com.maxwell.learning.jmx;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MyService {

    private volatile boolean shouldStop = false;
    private volatile ServiceStatus serviceStatus;

    public MyService() {
        serviceStatus = ServiceStatus.NEW;
    }

    public void start() {
        serviceStatus = ServiceStatus.STARTING;
        new Thread(new MyTask()).start();
    }

    public void stop() {
        serviceStatus = ServiceStatus.STOPING;
        shouldStop = true;
    }

    public boolean isShouldStop() {
        return shouldStop;
    }

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    private class MyTask implements Runnable {
        @Override
        public void run() {
            serviceStatus = ServiceStatus.RUNNING;
            while (!shouldStop) {
                // do some thing , ie. just print service current status
                System.out.println(serviceStatus);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            serviceStatus = ServiceStatus.TERMINATED;
            shouldStop = false;
        }
    }

    public enum ServiceStatus {
        NEW, STARTING, RUNNING, STOPING, TERMINATED
    }
}
