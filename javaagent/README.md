## 测试premain的功能
1.dog-agent是premain的示例，将其打包，生成dog-agent-1.0.0.jar；
2.运行agentdemo中的Client，运行时添加VM参数 -javaagnt:/path/to/dog-agent-1.0.0.jar
3.比较添加参数前后，Client运行输出结果

## 测试agentmain的功能
1.dog-agent-main是agentmain的示例，将其打包，生成dog-agent-main-1.0.0.jar；
2.运行agentdemo中的DogClient
3.运行agentdemo中的AttachToJVM（注意修改代码中dog-agent-main-1.0.0.jar路径）
4.观察DogClient在AttachToJVM启动前后的输出变化
