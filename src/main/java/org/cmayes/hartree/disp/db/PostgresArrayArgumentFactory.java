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

public class PostgresArrayArgumentFactory implements
        ArgumentFactory<SqlArray<?>> {
    private static final Map<Class<?>, String> TYPE_MAP = new HashMap<Class<?>, String>();

    public boolean accepts(Class<?> expectedType, Object value,
            StatementContext ctx) {
        return value instanceof SqlArray
                && isTypeHandled(((SqlArray<?>) value).getType());
    }

    private static boolean isTypeHandled(Class<?> type) {
        for (Entry<Class<?>, String> curEntry : TYPE_MAP.entrySet()) {
            if (curEntry.getKey().isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    private static String getTypeName(Class<?> type) {
        for (Entry<Class<?>, String> curEntry : TYPE_MAP.entrySet()) {
            if (curEntry.getKey().isAssignableFrom(type)) {
                return curEntry.getValue();
            }
        }
        throw new ParamIllegalArgumentException("Unhandled type '%s'",
                type.getName());
    }

    public Argument build(Class<?> expectedType, final SqlArray<?> value,
            StatementContext ctx) {
        return new Argument() {
            public void apply(int position, PreparedStatement statement,
                    StatementContext ctx) throws SQLException {
                // in postgres no need to (and in fact cannot) free arrays
                Array ary = ctx.getConnection().createArrayOf(
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
    }
}
