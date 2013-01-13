package org.cmayes.hartree.disp.db;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import com.cmayes.common.exception.ParamIllegalArgumentException;

/**
 * Handles creating SQL array entries for Postgres databases. Based on
 * {@link http://skife.org/jdbi/java/2011/12/21/jdbi_in_clauses.html}.
 * 
 * @author cmayes
 */
public class PostgresArrayArgumentFactory implements
        ArgumentFactory<SqlArray<?>> {
    private static final Map<Class<?>, String> TYPE_MAP = new HashMap<Class<?>, String>();

    /**
     * {@inheritDoc}
     * 
     * @see org.skife.jdbi.v2.tweak.ArgumentFactory#accepts(java.lang.Class,
     *      java.lang.Object, org.skife.jdbi.v2.StatementContext)
     */
    public boolean accepts(final Class<?> expectedType, final Object value,
            final StatementContext ctx) {
        return value instanceof SqlArray
                && isTypeHandled(((SqlArray<?>) value).getType());
    }

    /**
     * Returns whether we have a type name mapping for the given type.
     * 
     * @param type
     *            The type to check for.
     * @return Whether we have a mapping for the given type.
     */
    private static boolean isTypeHandled(final Class<?> type) {
        for (Entry<Class<?>, String> curEntry : TYPE_MAP.entrySet()) {
            if (curEntry.getKey().isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the Postgres type name for the given class.
     * 
     * @param type
     *            The type to look up.
     * @return The Postgres type name for the given class.
     * @throws IllegalArgumentException
     *             If we don't have a mapping for the given class.
     */
    private static String getTypeName(final Class<?> type) {
        for (Entry<Class<?>, String> curEntry : TYPE_MAP.entrySet()) {
            if (curEntry.getKey().isAssignableFrom(type)) {
                return curEntry.getValue();
            }
        }
        throw new ParamIllegalArgumentException("Unhandled type '%s'",
                type.getName());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.skife.jdbi.v2.tweak.ArgumentFactory#build(java.lang.Class,
     *      java.lang.Object, org.skife.jdbi.v2.StatementContext)
     */
    public Argument build(final Class<?> expectedType, final SqlArray<?> value,
            final StatementContext ctx) {
        return new Argument() {
            public void apply(final int position,
                    final PreparedStatement statement,
                    final StatementContext ctx) throws SQLException {
                // in postgres no need to (and in fact cannot) free arrays
                final Array ary = ctx.getConnection().createArrayOf(
                        getTypeName(value.getType()), value.getElements());
                statement.setArray(position, ary);
            }
        };
    }

    static {
        TYPE_MAP.put(Double.class, "float8");
        TYPE_MAP.put(Long.class, "int8");
        TYPE_MAP.put(Float.class, "float4");
        TYPE_MAP.put(Integer.class, "int4");
        TYPE_MAP.put(Boolean.class, "bool");
        TYPE_MAP.put(String.class, "text");
    }
}
