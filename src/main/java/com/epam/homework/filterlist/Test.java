package com.epam.homework.filterlist;

import java.util.*;

public class Test {

    private static <E> void testIterating(String desc, Collection<? extends E> collection,
                                          Collection<? extends E> predicate) {

        List<E> list = new FilterList<E>(collection, predicate);

        System.out.print("--- " + desc + " ---"
                + "\nList:      " + Arrays.toString(list.toArray())
                + "\nPredicate: " + ((FilterList<E>) list).getPredicate().toString()
                + "\nIterating: ");
        for (Object obj : list) {
            System.out.print(obj.toString() + " ");
        }
        System.out.print("\n\n");
    }

    private static <E> void testRemovingObject(String desc, Collection<? extends E> collection,
                                               Collection<? extends E> predicate, Object obj) {

        List<E> list = new FilterList<E>(collection, predicate);

        System.out.println("--- " + desc + " ---"
                + "\nList:        " + Arrays.toString(list.toArray())
                + "\nPredicate:   " + ((FilterList<E>) list).getPredicate().toString()
                + "\nTrying to remove object: " + obj.toString()
                + (((FilterList<E>) list).elementAllowed(obj) ? ", not in the predicate." : ", in the predicate."));
        boolean check = list.remove(obj);
        System.out.println((check ? "Object is removed." : "Object is not removed.")
                + "\nResult list: " + Arrays.toString(list.toArray()) + "\n");
    }

    private static <E> void testRemovingAtIndex(String desc, Collection<? extends E> collection,
                                                Collection<? extends E> predicate, int index) {

        List<E> list = new FilterList<E>(collection, predicate);

        System.out.println("--- " + desc + " ---"
                + "\nList:        " + Arrays.toString(list.toArray())
                + "\nPredicate:   " + ((FilterList<E>) list).getPredicate().toString()
                + "\nTrying to remove at index: " + index + " - Element: " + list.get(index).toString()
                + (((FilterList<E>) list).elementAllowedAt(index) ? ", not in the predicate." : ", in the predicate."));
        try {
            E elem = list.remove(index);
            System.out.println("Removed element: " + elem.toString());
        } catch (ElementInPredicateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Result list: " + Arrays.toString(list.toArray()) + "\n");
    }

    private static <E> void testAddingElement(String desc, Collection<? extends E> collection,
                                              Collection<? extends E> predicate, E element) {

        List<E> list = new FilterList<E>(collection, predicate);

        System.out.println("--- " + desc + " ---"
                + "\nList:        " + Arrays.toString(list.toArray())
                + "\nPredicate:   " + ((FilterList<E>) list).getPredicate().toString()
                + "\nTrying to add element: " + element.toString()
                + (((FilterList<E>) list).elementAllowed(element) ? ", not in the predicate." : ", in the predicate."));
        boolean check = false;
        try {
            check = list.add(element);
        } catch (ElementInPredicateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println((check ? "Element is added." : "Element is not added.")
                + "\nResult list: " + Arrays.toString(list.toArray()) + "\n");
    }

    private static <E> void testAddingAtIndex(String desc, Collection<? extends E> collection,
                                              Collection<? extends E> predicate, int index, E element) {

        List<E> list = new FilterList<E>(collection, predicate);

        System.out.println("--- " + desc + " ---"
                + "\nList:        " + Arrays.toString(list.toArray())
                + "\nPredicate:   " + ((FilterList<E>) list).getPredicate().toString()
                + "\nTrying to add at index: " + index + " - Element: " + element.toString()
                + (((FilterList<E>) list).elementAllowed(element) ? ", not in the predicate." : ", in the predicate."));
        try {
            list.add(index, element);
            System.out.println("Element is added.");
        } catch (ElementInPredicateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Result list: " + Arrays.toString(list.toArray()) + "\n");
    }

    public static void main(String[] args) {

        testIterating("Test 1. List and predicate are both Integer.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 5, 7, 9, 6})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 2. List and predicate are both Integer, several items from a predicate in a row.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 5, 4, 7, 9, 5, 4, 2, 6, 5, 8})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 3. List and predicate are both Integer, item from a predicate in the beginning.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 1, 2, 7, 9, 6})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 4. List and predicate are both Integer, several items from a predicate in the beginning.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{9, 9, 4, 1, 2, 7, 9, 6})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 5. List and predicate are both Integer, item from a predicate in the end.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 5, 7, 9})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 6. List and predicate are both Integer, several items from a predicate in the end.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 5, 7, 9, 4})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 7. List and predicate are both String.",
                new ArrayList<String>(Arrays.asList(new String[]{"c", "b", "e", "d", "a", "e", "ab", "bc", "d"})),
                new ArrayList<String>(Arrays.asList(new String[]{"e", "ab"})));

        testIterating("Test 8. List is Number, predicate is Integer.",
                new ArrayList<Number>(Arrays.asList(new Number[]{1, 5., 2, 5, 7, 9, 6, 4.})),
                new ArrayList<Integer>(Arrays.asList(new Integer[]{5, 9, 4})));

        testIterating("Test 9. List is Integer, predicate is Number.",
                new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 5, 7, 9, 6, 4})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})));

        testIterating("Test 10. List and predicate are both Number.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})));

        testRemovingObject("Test 11. Testing removal of object, object is in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                9.);

        testRemovingObject("Test 12. Testing removal of object, object is not in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                "a");

        testRemovingAtIndex("Test 13. Testing removal at index, element is in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                2);

        testRemovingAtIndex("Test 14. Testing removal at index, element is not in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                3);

        testAddingElement("Test 15. Testing addition of element, element is in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                9.);

        testAddingElement("Test 16. Testing addition of element, object is not in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                8);

        testAddingAtIndex("Test 17. Testing addition at index, element is in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                0,
                4);

        testAddingAtIndex("Test 18. Testing addition at index, element is not in a predicate.",
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 1, 9., 2, 5, 7, 4., 9, 6, 4, 3})),
                new ArrayList<Number>(Arrays.asList(new Number[]{5., 9., 4})),
                0,
                7);
    }
}