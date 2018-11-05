package com.maxwell.learning.common.reflectexample;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.HashMap;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ReflectExample {


    @Test
    public void testGetClass() throws ClassNotFoundException {
        Class<?> userClass = Class.forName("com.maxwell.learning.common.reflectexample.User");

        Class<User> userClass1 = User.class;

        Class<String> stringClass = String.class;

        Class<HashMap> hashMapClass = HashMap.class;

        User user = new User();
        Class<? extends User> userClass2 = user.getClass();

        String s = new String("abc");
        Class<? extends String> aClass = s.getClass();

    }

    static class Animal { }
    static class Cat extends Animal { }

    @Test
    public void testInstanceOf() {
        Cat cat = new Cat();
        boolean isInstanceOf = cat instanceof Animal;
        boolean isInstance = Animal.class.isInstance(cat);
    }


    @Test
    public void testNewInstance() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<User> constructor = User.class.getConstructor(String.class, int.class);
        User user = constructor.newInstance("xiaohong", 18);
    }

    @Test
    public void testGetConstructor() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<User> userClass = User.class;
        Constructor<User> constructor = userClass.getConstructor(String.class, int.class);
        userClass.getDeclaredConstructors();
        userClass.getConstructors();
        userClass.getDeclaredConstructor();


    }

    @Test
    public void testGetFiled() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        User user = new User("xiaoming", 18);

        Class<? extends User> userClass = user.getClass();
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            System.out.println(String.format("属性[%s], 值[%s]", field.getName(), field.get(user)));
        }
    }

    @Test
    public void testInvokeMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Calculator calculator = new Calculator();
        Class<? extends Calculator> calculatorClass = calculator.getClass();
        Method method = calculatorClass.getDeclaredMethod("add", int.class, int.class);
        method.setAccessible(true);
        int res = (int)method.invoke(calculator, 1, 2);
        System.out.println(res);
    }

    @Test
    public void testArray() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.lang.String");
        Object array = Array.newInstance(clazz,25);
        Array.set(array,0,"aaa");
        Array.set(array,1,"bbb");
        Array.set(array,2,"ccc");
        Array.set(array,3,"ddd");
        Array.set(array,4,"eee");
        String  res = (String)Array.get(array, 3);
        System.out.println(res);
    }

}
