package com.maxwell.learning.degradation;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class UserService {

    public static void main(String[] args) {
        for(;;){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Configs.getBoolean("user.not.call.backend"));
            System.out.println(Configs.getInt("user.not.call.backend.limit"));
        }
    }
}
