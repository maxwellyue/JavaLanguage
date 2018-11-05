package com.maxwell.learning.common;

import org.junit.jupiter.api.Test;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class EnumExample {

    @Test
    public void enumTest(){
        Color color1 = Color.WHITE;
        Color color2 = Color.WHITE;

        color1.setValue("val1");
        color2.setValue("val2");

        System.out.println(color1.value);
        System.out.println(color2.value);
    }

    public static enum Color{
        WHITE("white"),
        BLACK("black");

        public String value;

        Color(String value) {
            this.value = value;
        }

        public void setValue(String value){
            this.value = value;
        }
    }

    public static void main(String[] args) {

    }
}
