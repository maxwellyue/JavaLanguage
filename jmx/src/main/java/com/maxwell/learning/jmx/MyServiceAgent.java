package com.maxwell.learning.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MyServiceAgent {

    private ServiceMonitor serviceMonitor;

    public MyServiceAgent(ServiceMonitor serviceMonitor) {
        this.serviceMonitor = serviceMonitor;
    }

    public void start() {

        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName mbeanName = new ObjectName("jmxBean:name=" + "serviceMonitor");
            server.registerMBean(serviceMonitor, mbeanName);
            String ip = "192.168.56.14";
            int port = 1099;
            System.setProperty("java.rmi.server.hostname", ip);
            LocateRegistry.createRegistry(port);

            String url = "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/jmxrmi";
            JMXServiceURL jmxServiceURL = new JMXServiceURL(url);

            JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(
                    jmxServiceURL, null, server);
            jmxConnectorServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
