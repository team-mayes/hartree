package org.cmayes.hartree.disp.db;

import static java.util.Arrays.asList;

import com.google.common.collect.Iterables;

public class SqlArray<T> {
    private final Object[] elements;
    private final Class<T> type;

    public SqlArray(Class<T> type, Iterable<T> elements) {
        this.elements = Iterables.toArray(elements, Object.class);
        this.type = type;
    }

    public static <T> SqlArray<T> arrayOf(Class<T> type, T... elements) {
        return new SqlArray<T>(type, asList(elements));
    }

    public static <T> SqlArray<T> arrayOf(Class<T> type, Iterable<T> elements) {
        return new SqlArray<T>(type, elements);
    }

    public Object[] getElements() {
        return elements;
    }

    public Class<T> getType() {
        return type;
    }
}
