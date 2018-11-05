package com.maxwell.learning.common.skiplistexample;

import java.util.Random;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class SkipList<T extends Comparable<? super T>> {


    private static final int MIN_LEVEL = 2;

    private static final int MAX_LEVEL = 63;


    int level;

    SkipListNode<T> head;

    int size;

    Random rand = new Random();

    public SkipList(int level) {
        if (level < MIN_LEVEL){
            level = MIN_LEVEL;}
        else if (level > 63){
            level = 63; }
        this.level = level;
        this.head = new SkipListNode<>(null);
    }

    public void add(T value) {
        //Determine the level of the new node
        int nodeLevel = chooseLevel(this.level);
        //Create the new Node
        SkipListNode<T> node = new SkipListNode<T>(value);
        //Add to skipList
        SkipListNode<T> curNode = head;
        int curLevel = nodeLevel - 1;
        /*while (curLevel >= 0) {
            while (curNode.forward[curLevel] != null && curNode.forward[curLevel].val.compareTo(value) < 0) {
                curNode = curNode.forward[curLevel];
            }
            node.forward[curLevel] = curNode.forward[curLevel];
            curNode.forward[curLevel] = node;
            curLevel--;
        }*/
        this.size++;
    }

    //Get the level for the new node
    private int chooseLevel(int level) {
        long n = (long)1 << (level - 1);
        long ranNum;
        if (level < 32) {
            ranNum = rand.nextInt((int) n) + 1; //[1, n]
        } else {
            //int overflow, use long for calculation
            ranNum = rand.nextLong();
            if (ranNum < 0)
                ranNum = (ranNum + Long.MAX_VALUE) % n + 1; // [1, n]
            else if (ranNum > n)
                ranNum = ranNum % n + 1;  // [1, n]
        }
        int ranLevel = 1;
        while (true) {
            n >>= 1;
            if (ranNum > n)
                return ranLevel;
            ranLevel++;
        }
    }




    static final class SkipListNode<T>{

        T value;

        SkipListNode<T> right;

        SkipListNode<T> down;

        SkipListNode(T value, SkipListNode<T> right, SkipListNode<T> down){
            this.value = value;
            this.right = right;
            this.down = down;
        }

        SkipListNode(T value){
            this.value = value;
            this.right = null;
            this.down = null;
        }

        SkipListNode(SkipListNode<T> right, SkipListNode<T> down){
            this.value = null;
            this.right = right;
            this.down = down;
        }
    }
}
