package org.cmayes.hartree.disp.db;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.util.Properties;

import org.postgresql.ds.PGPoolingDataSource;
import org.skife.jdbi.v2.DBI;

/**
 * Common JDBI methods.
 * 
 * @author cmayes
 */
public final class JdbiUtils {
    public static final String DEFAULT_DB = "hartree";
    public static final String DEFAULT_SERVER = "localhost";
    public static final String PASSWORD_KEY = "db.password";
    public static final String USER_KEY = "db.user";
    public static final String SERVER_KEY = "db.host";
    public static final String DATABASE_KEY = "db.name";

    /**
     * Private constructor for util class.
     */
    private JdbiUtils() {
    }

    /**
     * Creates a DBI instance using the given properties.
     * 
     * @param cfgProps
     *            The properties to use.
     * @return A DBI instance configured by the given properties.
     */
    public static DBI createDbHandle(final Properties cfgProps) {
        final PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setPassword(asNotNull(asNotNull(cfgProps, "DB properties are null")
                .getProperty(PASSWORD_KEY), String.format(
                "Value for key %s is null", PASSWORD_KEY)));
        ds.setUser(asNotNull(cfgProps.getProperty(USER_KEY),
                String.format("Value for key %s is null", USER_KEY)));
        ds.setServerName(cfgProps.getProperty(SERVER_KEY, DEFAULT_SERVER));
        ds.setDatabaseName(cfgProps.getProperty(DATABASE_KEY, DEFAULT_DB));
        return new DBI(ds);
    }
}
