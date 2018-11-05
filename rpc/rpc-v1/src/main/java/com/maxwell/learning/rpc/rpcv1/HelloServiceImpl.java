package com.maxwell.learning.rpc.rpcv1;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class HelloServiceImpl  implements HelloService {

    @Override
    public String sayHello(String name) {
        return name + " say hello";
    }

}
