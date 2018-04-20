package com.maxwell.learning.jmx;

/**
 *
 * 大体思路：
 *
 * 我自己有一个服务MyService，其中定义了启动、关闭的方法
 *
 * 现在我想通过JMX来管理我的服务的启动，并查看它的状态
 *
 * 那么，要写一个名字为XxxxMBean的接口，在其中定义你想实现的功能，
 *
 * 在这里就是启动、关闭我的服务，并可以获取我的服务的实时状态
 *
 * 然后，写一个类实现XxxxMBean这个接口，这个类的名字任意。
 *
 * 最后，将这个类交给MBeanServer，并在程序启动的时候，启动MBeanServer
 *
 *
 *
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Lanucher {


    public static void main(String[] args){
        //创建服务，并启动
        MyService service = new MyService();
        service.start();

        //启动jmx
        new MyServiceAgent(new ServiceMonitor(service)).start();
    }
}
