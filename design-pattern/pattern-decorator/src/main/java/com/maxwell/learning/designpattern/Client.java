package com.maxwell.learning.designpattern;

import java.io.*;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Client {


    public static void main(String[] args) {


        try (DataInputStream inputStream = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream("test.txt")))){
            //do something with inputStream

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
