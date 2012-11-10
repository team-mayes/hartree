package org.cmayes.hartree.disp.db;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.util.BeanMap;

/**
 * RS mapper using {@link BeanMap} to map data to bean instances.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type to map.
 */
public class HartreeBeanMapper<T> implements ResultSetMapper<T> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Class<T> type;
    private final Map<String, PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();

    /**
     * Creates a mapper for the given type.
     * 
     * @param tgtType
     *            The type to fill.
     */
    public HartreeBeanMapper(final Class<T> tgtType) {
        this.type = tgtType;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.skife.jdbi.v2.tweak.ResultSetMapper#map(int, java.sql.ResultSet,
     *      org.skife.jdbi.v2.StatementContext)
     */
    public T map(final int row, final ResultSet rs, final StatementContext ctx)
            throws SQLException {
        final BeanMap<T> beanMap;
        try {
            beanMap = new BeanMap<T>(type.newInstance(), false);
        } catch (final Exception e) {
            throw new IllegalArgumentException(String.format(
                    "A bean, %s, was mapped " + "which was not instantiable",
                    type.getName()), e);
        }

        final ResultSetMetaData metadata = rs.getMetaData();

        for (int i = 1; i <= metadata.getColumnCount(); ++i) {
            final String name = metadata.getColumnLabel(i);

            if (beanMap.containsKey(name)) {
                final Object rawObj = rs.getObject(i);
                if (rawObj instanceof Array) {
                    beanMap.put(name, ((Array) rawObj).getArray());
                } else {
                    beanMap.put(name, rawObj);
                }
            } else {
                logger.debug("Skipping column " + name);
            }
        }
        return beanMap.getBean();
    }
}
