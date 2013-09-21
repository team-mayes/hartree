package org.cmayes.hartree.disp.db;

import static java.util.Arrays.asList;

import com.google.common.collect.Iterables;

/**
 * Wrapper for working with SQL array types.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type of the wrapped elements.
 * @see PostgresArrayArgumentFactory
 */
public class SqlArray<T> {
    private final Object[] elements;
    private final Class<T> type;

    /**
     * Creates an array wrapper for the given iterable of the given type.
     * 
     * @param iterType
     *            The type of the iterable's elements.
     * @param wrapMe
     *            The elements to wrap.
     */
    public SqlArray(final Class<T> iterType, final Iterable<T> wrapMe) {
        this.elements = Iterables.toArray(wrapMe, Object.class);
        this.type = iterType;
    }

    /**
     * Static factory to create a SQL array wrapper for the given varargs of the
     * given type.
     * 
     * @param iterType
     *            The type of the varargs's elements.
     * @param wrapMe
     *            The elements to wrap.
     * @param <T>
     *            The type of the elements.
     * @return The SqlArray-wrapped array.
     */
    public static <T> SqlArray<T> arrayOf(final Class<T> iterType,
            final T... wrapMe) {
        return new SqlArray<T>(iterType, asList(wrapMe));
    }

    /**
     * Static factory to create a SQL array wrapper for the given iterable of
     * the given type.
     * 
     * @param iterType
     *            The type of the iterable's elements.
     * @param wrapMe
     *            The elements to wrap.
     * @param <T>
     *            The type of the elements.
     * @return The SqlArray-wrapped array.
     */
    public static <T> SqlArray<T> arrayOf(final Class<T> iterType,
            final Iterable<T> wrapMe) {
        return new SqlArray<T>(iterType, wrapMe);
    }

    /**
     * Returns the wrapped elements.
     * 
     * @return The wrapped elements.
     */
    public Object[] getElements() {
        return elements;
    }

    /**
     * Returns the type of the wrapped elements.
     * 
     * @return The type of the wrapped elements.
     */
    public Class<T> getType() {
        return type;
    }
}
