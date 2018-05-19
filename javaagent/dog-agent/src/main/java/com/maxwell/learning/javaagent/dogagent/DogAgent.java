package com.maxwell.learning.javaagent.dogagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class DogAgent {

    private static final Logger logger = Logger.getLogger(DogAgent.class.getName());

    private DogAgent() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassFileTransformer transformer = new DogTransformer();
        inst.addTransformer(transformer, true);
    }
}
