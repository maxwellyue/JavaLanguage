package com.maxwell.learning.common.aqs;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Counter {

    private int num;

    private Mutex mutex = new Mutex();

    public Counter(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void increase(){
        mutex.lock();
        num++;
        mutex.unlock();
    }
}
