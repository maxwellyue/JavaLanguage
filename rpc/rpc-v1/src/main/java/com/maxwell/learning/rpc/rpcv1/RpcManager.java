package com.maxwell.learning.rpc.rpcv1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class RpcManager {

    /**
     *
     * 提供服务
     *
     *
     * @param service 向外暴露的服务
     * @param port 端口
     * @throws Exception
     */
    public static void exportService(final Object service, int port) throws Exception {
        //创建服务端socket
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            //监听端口
            final Socket socket = serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream reader = null;
                    ObjectOutputStream writer = null;
                    try {
                        reader = new ObjectInputStream(socket.getInputStream());
                        String methodName = reader.readUTF();
                        Class[] argumentsType = (Class[]) reader.readObject();
                        Object[] arguments = (Object[]) reader.readObject();
                        Method method = service.getClass().getMethod(methodName, argumentsType);
                        //调用方法
                        Object result = method.invoke(service, arguments);
                        writer = new ObjectOutputStream(socket.getOutputStream());
                        //返回结果
                        writer.writeObject(result);
                    } catch (Exception e) {
                        if (null != writer) {
                            try {
                                writer.writeObject(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } finally {
                        if (null != writer) {
                            try {
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (null != reader) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }

    }


    /**
     * 调用远程服务
     *
     * @param interfaceClass 远程服务的接口
     * @param host 远程服务的host
     * @param port 远程服务的port
     * @param <T>
     * @return
     */
    public static <T> T referenceService(Class<T> interfaceClass, final String host, final int port) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host, port);
                ObjectOutputStream writer = null;
                ObjectInputStream reader = null;
                try {
                    writer = new ObjectOutputStream(socket.getOutputStream());
                    writer.writeUTF(method.getName());
                    writer.writeObject(method.getParameterTypes());
                    writer.writeObject(args);
                    reader = new ObjectInputStream(socket.getInputStream());
                    return reader.readObject();
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != reader) {
                        reader.close();
                    }
                }
            }
        });
    }
}
