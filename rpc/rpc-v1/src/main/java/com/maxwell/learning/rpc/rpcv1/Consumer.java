package com.maxwell.learning.rpc.rpcv1;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Consumer {
    public static void main(String[] args) {
        //调用远程服务
        HelloService helloService = RpcManager.referenceService(HelloService.class, "127.0.0.1", 20880);
        System.out.println(helloService.sayHello("maxwell"));
    }
}
