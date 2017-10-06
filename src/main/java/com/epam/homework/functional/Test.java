package com.epam.homework.functional;

import com.epam.homework.filterlist.FilterList;

import java.util.*;

public class Test {

    public static <T1, T2> List<T2> map(List<T1> list, Function<T1, T2> function) {
        if (list == null) {
            throw new NullPointerException("Argument list should not be null.");
        }
        if (function == null) {
            throw new NullPointerException("Argument function should not be null.");
        }

        List<T2> mappedList = new ArrayList<T2>(list.size());
        for (T1 elem : list) {
            mappedList.add(function.apply(elem));
        }
        return mappedList;
    }

    public static <T> T reduce(List<T> list, T initialValue, BinaryOperator<T> operator) {
        if (list == null) {
            throw new NullPointerException("Argument list should not be null.");
        }
        if (initialValue == null) {
            throw new NullPointerException("Argument initialValue should not be null.");
        }
        if (operator == null) {
            throw new NullPointerException("Argument operator should not be null.");
        }

        T result = initialValue;
        for (T elem : list) {
            result = operator.apply(result, elem);
        }
        return result;
    }

    private static <T1, T2> void testMap(String desc, List<T1> list, Function<T1, T2> function) {
        List resultList = map(list, function);
        System.out.println("--- " + desc + " ---"
                + "\nList:        " + Arrays.toString(list.toArray())
                + "\nResult List: " + resultList.toString() + "\n");
    }

    private static <T> void testReduce(String desc, List<T> list, T initialValue, BinaryOperator<T> operator) {
        T result = reduce(list, initialValue, operator);
        System.out.println("--- " + desc + " ---"
                + "\nList:   " + Arrays.toString(list.toArray())
                + "\nResult: " + result.toString() + "\n");
    }

    public static void main(String[] args) {

        testMap("Test 1. Applying square to list of Doubles.",
                Arrays.asList(1., 2., 3., 4., 5., 6.),
                new Function<Double, Double>() {
                    public Double apply(Double value) {
                        return value * value;
                    }
                });

        testMap("Test 2. Applying toString and adding \"String\" to list of Integers.",
                Arrays.asList(1, 2, 3, 4, 5, 6),
                new Function<Integer, String>() {
                    public String apply(Integer value) {
                        return "String" + value.toString();
                    }
                });

        testMap("Test 3. Applying division by 2 to filter list of Integers. Predicate is [3, 4, 6, 9].",
                new FilterList<Integer>(
                        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                        new HashSet<Integer>(Arrays.asList(3, 4, 6, 9))),
                new Function<Integer, Double>() {
                    public Double apply(Integer value) {
                        return value / 2.;
                    }
                });

        testReduce("Test 4. Reducing list of Integers to product.",
                Arrays.asList(1, 2, 3, 4, 5, 6),
                1,
                new BinaryOperator<Integer>() {
                    public Integer apply(Integer value1, Integer value2) {
                        return value1 * value2;
                    }
                });

        testReduce("Test 5. Reducing list of Strings to concatenation.",
                Arrays.asList("String1", "String2", "String3"),
                "",
                new BinaryOperator<String>() {
                    public String apply(String value1, String value2) {
                        return value1 + value2;
                    }
                });

        testReduce("Test 6. Reducing filter list of Doubles to sum. Predicate is [3., 4., 6.].",
                new FilterList<Double>(
                        Arrays.asList(1., 2., 3., 4., 5., 6., 7.),
                        new HashSet<Double>(Arrays.asList(3., 4., 6.))),
                0.,
                new BinaryOperator<Double>() {
                    public Double apply(Double value1, Double value2) {
                        return value1 + value2;
                    }
                });
    }
}
