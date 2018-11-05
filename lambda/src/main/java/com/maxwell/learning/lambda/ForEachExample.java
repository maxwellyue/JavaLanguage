package com.maxwell.learning.lambda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ForEachExample {

    @Test
    public void traditionalBreak() {
        List<String> colors = new ArrayList<>(Arrays.asList("white", "black", "red", "blue", "green"));
        for (String color : colors) {
            if (color.equals("red")) {
                break;
            }
            System.out.println(color);
        }
    }

    @Test
    public void traditionalContinue() {
        List<String> colors = new ArrayList<>(Arrays.asList("white", "black", "red", "blue", "green"));
        for (String color : colors) {
            if (color.equals("blue")) {
                continue;
            }
            System.out.println(color);
        }
    }

    @Test
    public void lambdaContinue() {
        List<String> colors = new ArrayList<>(Arrays.asList("white", "black", "red", "blue", "green"));
        colors.forEach(color -> {
            if (color.equals("blue")) {
                return;
            }
            System.out.println(color);
        });
    }

    @Test
    public void lambdaBreak() {
        List<String> colors = new ArrayList<>(Arrays.asList("white", "black", "red", "blue", "green"));
        try {
            colors.forEach(color -> {
                if (color.equals("blue")) {
                    throw new BreakException();
                }
                System.out.println(color);
            });
        } catch (BreakException e) {
            System.out.println("foreach while break");
        }

    }

    public static class BreakException extends RuntimeException {}


    @Test
    public void filter() {
        List<String> colors = new ArrayList<>(Arrays.asList("white", "black", "red", "blue", "green"));
        Optional<String> target = colors.stream().filter(color -> color.equals("blue")).findFirst();
        String targetColor = target.get();
        System.out.println(targetColor);
    }

    @Test
    public void anyMatch() {
        List<String> colors = new ArrayList<>(Arrays.asList("white", "black", "red", "blue", "green"));
        boolean blueExists = colors.stream().anyMatch(color -> color.equals("blue"));
        System.out.println(blueExists);
    }
}
