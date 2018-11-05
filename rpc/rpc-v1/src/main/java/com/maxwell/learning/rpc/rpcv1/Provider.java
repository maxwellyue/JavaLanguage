package com.maxwell.learning.rpc.rpcv1;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        final HelloService helloService = new HelloServiceImpl();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcManager.exportService(helloService, 20880);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
