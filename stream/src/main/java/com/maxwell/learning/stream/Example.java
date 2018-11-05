package com.maxwell.learning.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Example {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("dog", "cat", "pig", "rabbit");
        Stream<String> stream = list.stream();
        Stream<String> parallelStream = list.parallelStream();

        int[] array = {1, 2, 34, 5, 56, 4, 24};
        IntStream intStream = Arrays.stream(array);

        Stream<Integer> integerStream = Stream.of(1, 2, 34, 5, 56, 4, 24);


        int sum = widgets.stream()
                .filter(w -> w.getColor() == RED)
                .mapToInt(w -> w.getWeight())
                .sum();


        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();

        List<String> output = wordList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());


        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());

        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums)
                .filter(n -> n % 2 == 0)
                .toArray(Integer[]::new);

        List<String> output = reader.lines()
                .flatMap(line -> Stream.of(line.split(REGEXP)))
                .filter(word -> word.length() > 0)
                .collect(Collectors.toList());

        // Java 8
        roster.stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .forEach(p -> System.out.println(p.getName()));
        // Pre-Java 8
        for (Person p : roster) {
            if (p.getGender() == Person.Sex.MALE) {
                System.out.println(p.getName());
            }
        }

        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());


        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);
        getLength(strA);
        getLength("");
        getLength(strB);


        Integer sum = integers.reduce(0, (a, b) -> a + b);

        Integer sum = integers.reduce(0, Integer::sum);


        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F")
                .filter(x -> x.compareTo("Z") > 0)
                .reduce("", String::concat);


        BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
        int longest = br.lines()
                .mapToInt(String::length)
                .max()
                .getAsInt();
        br.close();
        System.out.println(longest);

        List<String> words = br.lines()
                .flatMap(line -> Stream.of(line.split(" ")))
                .filter(word -> word.length() > 0)
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        br.close();
        System.out.println(words);


        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));

        boolean isAllAdult = persons.stream()
                .allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);

        boolean isThereAnyChild = persons.stream()
                .anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);


        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random)
                .limit(10)
                .forEach(System.out::println);

        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100))
                .limit(10)
                .forEach(System.out::println);

        Stream.generate(new PersonSupplier())
                .limit(10)
                .forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));

        Stream.iterate(0, n -> n + 3)
                .limit(10)
                .forEach(x -> System.out.print(x + " "));

        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier())
                .limit(100)
                .collect(Collectors.groupingBy(Person::getAge));

        Iterator it = personGroups.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }


        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier())
                .limit(100)
                .collect(Collectors.partitioningBy(p -> p.getAge() < 18));

        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }

    public static void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
        if (text != null) {
            System.out.println(text);
        }
    }

    public static int getLength(String text) {
        // Java 8
        return Optional.ofNullable(text).map(String::length).orElse(-1);
        // Pre-Java 8
        //return if (text != null) ? text.length() : -1;
    }


    public void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream()
                .limit(2)
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());
        System.out.println(personList2);
    }

    private class Person {

        public int no;
        private String name;

        public Person(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public String getName() {
            System.out.println(name);
            return name;
        }
    }


    private class PersonSupplier implements Supplier<Person> {

        private int index = 0;
        private Random random = new Random();

        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }


}
