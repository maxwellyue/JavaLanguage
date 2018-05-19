package com.maxwell.learning.javaagent.dogagentmain;

import com.maxwell.learning.javaagent.dog.Dog;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DogAgent {

    private DogAgent() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        ClassFileTransformer transformer = new DogTransformer();
        inst.addTransformer(transformer, true);
        inst.retransformClasses(Dog.class);
    }
}
