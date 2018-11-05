package com.maxwell.learning.common;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ListExample {

    Iterable iterable;


    @Test
    public void initList() {
        List<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("white");
        colors.add("black");
    }

    @Test
    public void initList2() {
        List<String> list = new ArrayList<String>() {{
            add("red");
            add("white");
            add("black");
        }};
    }

    @Test
    public void initList3() {
        List<String> list = new ArrayList<String>(Arrays.asList("red", "white", "black"));
    }

    @Test
    public void initList4() {
        List<String> list = Arrays.asList("red", "white", "black");
    }

    @Test
    public void initList5() {
        List<String> list = ImmutableList.of("red", "white", "black");
    }

    @Test
    public void initList6() {
        List<String> list = Collections.nCopies(1000, "red");
    }

    public static <T> ArrayList<T> createArrayList(T... elements) {
        ArrayList<T> list = new ArrayList<T>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }

    public void single() {
        List<Integer> idList = Collections.singletonList(1001);
        ConcurrentLinkedQueue v;
        Stack stack;
    }

    @Test
    public void delete() {
        List<Integer> list = new ArrayList<>(Arrays.asList(6, 2, 2, 6, 8));

        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!newList.contains(list.get(i))) {
                newList.add(list.get(i));
            }
        }

        for (int i = 0; i < newList.size(); i++) {
            System.out.println(newList.get(i));
        }
    }

    @Test
    public void itee() {
        List<Integer> list = new ArrayList<>(Arrays.asList(6, 2, 2, 6, 8));
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
            if (next == 2) {
                iterator.remove();
            }
        }
        System.out.println(">>>>>>>>>after");
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    @Test
    public void foreach() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7));
        list.forEach(element -> {
            System.out.println(element);
        });
    }

    @Test
    public void exception1() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 1000; i++) {
            final int a = i;
            threadPool.execute(() -> {
                list.add(a);
            });
        }
        Collections.sort(list);
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void exception2() throws InterruptedException {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7));
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            System.out.println(next);
            if (next == 3) {
                //list.clear();
                list.add(10);
            }
        }
    }

    @Test
    public void comparator() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7));

        LinkedList linkedList;
    }
}
