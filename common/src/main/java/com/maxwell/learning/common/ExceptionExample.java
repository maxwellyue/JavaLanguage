package com.maxwell.learning.common;

import com.google.common.base.Preconditions;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ExceptionExample {

    public static void main(String[] args) {
        int[] array = {1, 2, 3};
        testPreconditions(false,array ,3 );


    }

    private void add(boolean bool, int[] array, int position) {
        if (!bool) {
            throw new IllegalArgumentException("preCondition not allow!!");
        }
        if (array == null) {
            throw new NullPointerException("array is null!!");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("array length is 0!!");
        }
        if (position > array.length || position < 0) {
            throw new ArrayIndexOutOfBoundsException("position error!!");
        }

        //ok, do something...
    }

    private static void testPreconditions(boolean bool, int[] array, int position) {
        Preconditions.checkArgument(bool, "preCondition not allow!!");
        Preconditions.checkNotNull(array, "array is null!!");
        Preconditions.checkElementIndex(position, array.length, "position error!!");
        //ok, do something...
    }
}
