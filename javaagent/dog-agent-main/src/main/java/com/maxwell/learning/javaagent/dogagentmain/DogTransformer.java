package com.maxwell.learning.javaagent.dogagentmain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DogTransformer implements ClassFileTransformer {

    private static final String DOG_CLASS_NAME="com.maxwell.learning.javaagent.dog.Dog";

    @Override
    public byte[] transform(ClassLoader loader, String classFile, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        try {
            final String className = toClassName(classFile);

            if (DOG_CLASS_NAME.equals(className)) {
                System.out.println("Transforming class " + className);
                CtClass clazz = getCtClass(classFileBuffer, loader);
                //modify toString() methdo
                CtMethod method = clazz.getDeclaredMethod("toString");
                method.setBody("return \"i am a gentle dog\";");
                return clazz.toBytecode();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }


    private static String toClassName(String classFile) {
        return classFile.replace('/', '.');
    }

    private static CtClass getCtClass(byte[] classFileBuffer, ClassLoader classLoader) throws IOException {

        ClassPool classPool = new ClassPool(true);
        if (classLoader == null) {
            classPool.appendClassPath(new LoaderClassPath(ClassLoader.getSystemClassLoader()));
        } else {
            classPool.appendClassPath(new LoaderClassPath(classLoader));
        }

        CtClass clazz = classPool.makeClass(new ByteArrayInputStream(classFileBuffer), false);
        clazz.defrost();
        return clazz;
    }
}
