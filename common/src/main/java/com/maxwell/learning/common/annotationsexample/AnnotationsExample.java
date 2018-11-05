package com.maxwell.learning.common.annotationsexample;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */

public class AnnotationsExample {

    @Test
    @GetMapping(path = "")
    public void combineAnnotations(){



    }





    @Test
    public void logProcessor() throws InvocationTargetException, IllegalAccessException {
        Operation.Book book = new Operation.Book();
        book.setName("way-to-architect");

        Operation operation = new Operation();
        Class<? extends Operation> operationClass = operation.getClass();
        Method[] methods = operationClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                Log logAnnotation = method.getAnnotation(Log.class);
                String type = logAnnotation.type();
                System.out.println("操作的类型：" + type);
                method.invoke(operation, book);
            }
        }
    }

    @Test
    public void testFormatter() throws IllegalAccessException {
        User user = new User("xiaoming", 18);
        String format = Formatter.format(user);
        System.out.println(format);
    }

    static class User {

        @Label("名字")
        private String name;

        @Label("年龄")
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


}
