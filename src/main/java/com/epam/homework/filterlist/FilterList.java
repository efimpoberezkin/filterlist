package com.epam.homework.filterlist;

import java.util.*;

public class FilterList<E> extends ArrayList<E> {

    private HashSet<E> predicate;

    public FilterList(Collection<? extends E> c, HashSet<? extends E> predicate) {
        super(c);
        this.predicate = new HashSet<E>(predicate);
    }

    public HashSet<E> getPredicate() {
        return predicate;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    public boolean elementAllowed(Object o) {
        return !predicate.contains(o);
    }

    public boolean elementAllowedAt(int index) {
        return elementAllowed(get(index));
    }

    public boolean remove(Object o) {
        if (elementAllowed(o)) {
            return super.remove(o);
        } else {
            return false;
        }
    }

    public E remove(int index) {
        rangeCheck(index);

        if (elementAllowedAt(index)) {
            return super.remove(index);
        } else {
            throw new ElementInPredicateException(inPredicateMsg(index));
        }
    }

    public boolean add(E element) {
        if (elementAllowed(element)) {
            return super.add(element);
        } else {
            return false;
        }
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (elementAllowed(element)) {
            super.add(index, element);
        } else {
            throw new ElementInPredicateException(inPredicateMsg());
        }
    }

    private void rangeCheck(int index) {
        if (index >= size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > this.size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size();
    }

    private String inPredicateMsg(int index) {
        return "Object is not allowed by the predicate. Index: " + index;
    }

    private String inPredicateMsg() {
        return "Object is not allowed by the predicate.";
    }

    //Similar to Iterator of AbstractList, except for the predicate check.
    private class Itr implements Iterator<E> {
        int cursor = 0;   // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            int i = cursor;
            while (i != size()) {
                if (elementAllowedAt(i)) {
                    cursor = i;
                    return true;
                } else {
                    i++;
                }
            }
            return false;
        }

        public E next() {
            checkForComodification();
            try {
                int i = cursor;
                E next = get(i);
                if (elementAllowedAt(i)) {
                    lastRet = i;
                    cursor = i + 1;
                    return next;
                } else {
                    throw new ElementInPredicateException(inPredicateMsg(i));
                }
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                FilterList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            } catch (ElementInPredicateException e) {
                throw new ElementInPredicateException(inPredicateMsg(lastRet));
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}