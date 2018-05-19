package com.maxwell.learning.javaagent.agentdemo;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class AttachToJVM {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //获取本机所有JVM实例
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果是我们刚才运行的DogClient所在的虚拟机
            if(vmd.displayName().endsWith("DogClient")){
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/yue/Desktop/dog-agent-main-1.0.0.jar");
                System.out.println("load dog agent main...");
                virtualMachine.detach();
            }
        }
    }
}
