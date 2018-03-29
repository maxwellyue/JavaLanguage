package com.maxwell.learning.concurrency;


import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/************************************************************************************
 * 功能描述：
 * 创建人：岳增存  yuezc@seentao.com
 * 创建时间： 2017年12月10日 --  下午8:43 
 * 其他说明：
 * 修改时间：
 * 修改人：
 *************************************************************************************/
public class AtomicExample {


    @Test
    public void testAtomicInteger() {
        Executor executor = Executors.newFixedThreadPool(3);
        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println("atomicInteger的当前值：" + atomicInteger.addAndGet(1));
            });
        }
    }

    @Test
    public void testAtomicIntegerArray() {

        int[] originArray = new int[]{1, 2, 3};

        AtomicIntegerArray array = new AtomicIntegerArray(originArray);

        array.getAndSet(0, 8);
        System.out.println(array.get(0));
        System.out.println(originArray[0]);
    }

}

class AtomicReferenceExample{

    private  AtomicReference<User> userAtomicReference = new AtomicReference<>();

    @Test
    public void test(){
        User originUser = new User(18, "小岳");
        userAtomicReference.set(originUser);
        User updateUser = new User(28, "老岳");
        userAtomicReference.compareAndSet(originUser, updateUser);
        System.out.println(userAtomicReference.get().getName() + ":" + userAtomicReference.get().getAge());
    }

    class User{
        private String name;
        private int age;

        public User(int age, String name){
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }
}

class AtomicIntegerFieldUpdaterExample{

    private AtomicIntegerFieldUpdater<User> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    @Test
    public void test(){
        User user = new User(18, "小岳");
        fieldUpdater.addAndGet(user, 10);
        System.out.println("user现在的年龄:" + fieldUpdater.get(user));
    }

    class User{
        private String name;
        public volatile int age;

        public User(int age, String name){
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }
}




