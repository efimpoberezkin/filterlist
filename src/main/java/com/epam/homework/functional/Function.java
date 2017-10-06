package com.epam.homework.functional;

public interface Function<T1, T2> {
    T2 apply (T1 value);
}