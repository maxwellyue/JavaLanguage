package com.maxwell.learning.common.io;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * disk-nic零拷贝
 */
class ZeroCopyServer {

    ServerSocketChannel listener = null;

    public void start() throws IOException {
        InetSocketAddress listenAddress = new InetSocketAddress(8080);
        listener = ServerSocketChannel.open();
        ServerSocket ss = listener.socket();
        ss.setReuseAddress(true);
        ss.bind(listenAddress);
        System.out.println("监听端口:" + listenAddress.toString());
    }

    public static void main(String[] args) throws IOException {
        ZeroCopyServer dns = new ZeroCopyServer();
        dns.start();
        dns.receive();
    }

    private void receive() {
        ByteBuffer dst = ByteBuffer.allocate(1024 * 4);
        try {
            while (true) {
                SocketChannel conn = listener.accept();
                System.out.println("创建连接: " + conn);
                conn.configureBlocking(true);
                int nread = 0;
                while (nread != -1) {
                    try {
                        nread = conn.read(dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                        nread = -1;
                    }
                    dst.rewind();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ZeroCopyClient {

    public static void main(String[] args) throws IOException {
        SocketAddress server = new InetSocketAddress("localhost", 8080);

        SocketChannel client = SocketChannel.open();
        client.connect(server);
        client.configureBlocking(true);

        FileChannel fileChannel = new FileInputStream("/Users/yue/Desktop/test.ev4").getChannel();
        //零拷贝发送文件
        fileChannel.transferTo(0, fileChannel.size(), client);

        client.close();
        fileChannel.close();
    }
}
