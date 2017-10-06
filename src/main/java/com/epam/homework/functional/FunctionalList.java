package com.epam.homework.functional;

import java.util.ArrayList;
import java.util.Collection;

public class FunctionalList<E> extends ArrayList<E> {

    public FunctionalList(Collection<? extends E> c) {
        super(c);
    }

    public FunctionalList(int initialCapacity) {
        super(initialCapacity);
    }

    <T> FunctionalList<T> map(Function<E, T> function) {
        if (function == null) {
            throw new NullPointerException("Argument function should not be null.");
        }

        FunctionalList<T> mappedList = new FunctionalList<T>(this.size());
        for (E elem : this) {
            mappedList.add(function.apply(elem));
        }
        return mappedList;
    }

    E reduce(E initialValue, BinaryOperator<E> operator) {
        if (initialValue == null) {
            throw new NullPointerException("Argument initialValue should not be null.");
        }
        if (operator == null) {
            throw new NullPointerException("Argument operator should not be null.");
        }

        E result = initialValue;
        for (E elem : this) {
            result = operator.apply(result, elem);
        }
        return result;
    }
}
