package com.epam.homework.functional;

import java.util.*;

public class Test {

    private static <T1, T2> void testMap(String desc, FunctionalList<T1> list, Function<T1, T2> function) {
        List<T2> resultList = list.map(function);
        System.out.println("--- " + desc + " ---"
                + "\nList:        " + Arrays.toString(list.toArray())
                + "\nResult List: " + resultList.toString() + "\n");
    }

    private static <T> void testReduce(String desc, FunctionalList<T> list, T initialValue, BinaryOperator<T> operator) {
        T result = list.reduce(initialValue, operator);
        System.out.println("--- " + desc + " ---"
                + "\nList:   " + Arrays.toString(list.toArray())
                + "\nResult: " + result.toString() + "\n");
    }

    public static void main(String[] args) {

        testMap("Test 1. Applying square to list of Doubles.",
                new FunctionalList<Double>(Arrays.asList(1., 2., 3., 4., 5., 6.)),
                new Function<Double, Double>() {
                    public Double apply(Double value) {
                        return value * value;
                    }
                });

        testMap("Test 2. Applying toString and adding \"String\" to list of Integers.",
                new FunctionalList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6)),
                new Function<Integer, String>() {
                    public String apply(Integer value) {
                        return "String" + value.toString();
                    }
                });

        testMap("Test 3. Applying division by 2 to list of Integers.",
                new FunctionalList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6)),
                new Function<Integer, Double>() {
                    public Double apply(Integer value) {
                        return value / 2.;
                    }
                });

        testReduce("Test 4. Reducing list of Integers to product.",
                new FunctionalList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6)),
                1,
                new BinaryOperator<Integer>() {
                    public Integer apply(Integer value1, Integer value2) {
                        return value1 * value2;
                    }
                });

        testReduce("Test 5. Reducing list of Strings to concatenation.",
                new FunctionalList<String>(Arrays.asList("String1", "String2", "String3")),
                "",
                new BinaryOperator<String>() {
                    public String apply(String value1, String value2) {
                        return value1 + value2;
                    }
                });

        testReduce("Test 6. Reducing list of Doubles to sum.",
                new FunctionalList<Double>(Arrays.asList(1., 2., 3., 4., 5., 6.)),
                0.,
                new BinaryOperator<Double>() {
                    public Double apply(Double value1, Double value2) {
                        return value1 + value2;
                    }
                });
    }
}
