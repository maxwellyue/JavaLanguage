package com.maxwell.learning.common.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ZeroCopyExample {

    @Test
    public void testFileZeroCopy() throws IOException {
        FileChannel fromChannel = new RandomAccessFile("/Users/yue/Desktop/test.ev4", "rw").getChannel();
        FileChannel toChannel = new RandomAccessFile("/Users/yue/Desktop/copy.ev4", "rw").getChannel();

        fromChannel.transferTo(0, fromChannel.size(), toChannel);
        fromChannel.close();
        toChannel.close();
    }


}
