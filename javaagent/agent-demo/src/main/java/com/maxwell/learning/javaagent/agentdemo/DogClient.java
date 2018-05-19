package com.maxwell.learning.javaagent.agentdemo;

import com.maxwell.learning.javaagent.dog.Dog;

/**
 * java – javaagent:/Users/yue/Desktop/dog-agent-main-1.0.0.jar com.maxwell.learning.javaagent.agentdemo.Client
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DogClient {

    public static void main(String[] args) {
        Dog dog = new Dog();
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(dog.toString());
        }
    }
}
