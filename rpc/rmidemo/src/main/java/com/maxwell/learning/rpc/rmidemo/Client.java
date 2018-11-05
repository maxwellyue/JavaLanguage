package com.maxwell.learning.rpc.rmidemo;

import java.rmi.Naming;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {

    public static void main(String[] args) {
        try{
            //客户端到RMI registry中寻找
            UserService service = (UserService) Naming.lookup("rmi://127.0.0.1:1099/RemoteUserService");
            int userId = 10;
            String username = service.getUsername(userId);
            User user = service.getById(userId);

            System.out.println(username);
            System.out.println(user.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
