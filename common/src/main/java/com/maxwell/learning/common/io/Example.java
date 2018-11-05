package com.maxwell.learning.common.io;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Example {

    public static void main(String[] args) {


        Flushable flushable;

        InputStream issnputStream;

        Socket socket;

        PushbackInputStream inputStream;

        PushbackReader pushbackReader;

        OutputStreamWriter writer;

        BufferedReader bufferedReader;

        BufferedWriter bufferedWriter;

        FileOutputStream fileOutputStream;

        FileWriter fileWriter;

        BufferedOutputStream bufferedOutputStream;

        BufferedWriter bufferedWriter1;

        FilterOutputStream filterOutputStream;

        FileOutputStream fileOutputStream1;


        try {
            StringBuffer str = new StringBuffer();
            char[] buf = new char[1024];
            FileReader f = new FileReader("file");
            while(f.read(buf)>0){
                str.append(buf);
            }
            str.toString();
        } catch (IOException e) {

        }

        HashMap hashMap;


        RandomAccessFile file;

        Reader reader;

        OutputStream outputStream;

        Writer writer1;


    }
}
