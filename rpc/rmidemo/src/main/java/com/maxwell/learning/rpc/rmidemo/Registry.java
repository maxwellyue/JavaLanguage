package com.maxwell.learning.rpc.rmidemo;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Registry {

    public static void main(String[] args) {
        try {
            //创建Registry实例，并在端口1099监听远程请求(默认1099端口)
            LocateRegistry.createRegistry(1099);
            //把远程对象（service）注册到Registry
            UserService service = new UserServiceImpl();
            Naming.rebind("RemoteUserService", service);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
