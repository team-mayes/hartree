package org.cmayes.hartree.disp.db;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.Writer;
import java.util.Collection;
import java.util.Properties;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.model.BaseResult;
import org.postgresql.ds.PGPoolingDataSource;
import org.skife.jdbi.v2.DBI;

import com.cmayes.common.MediaType;

/**
 * @author cmayes
 * 
 */
public class SnapshotPostgresDisplay implements Display<BaseResult> {
    public static final String DEFAULT_DB = "hartree";
    public static final String DEFAULT_SERVER = "localhost";
    public static final String PASSWORD_KEY = "db.password";
    public static final String USER_KEY = "db.user";
    public static final String SERVER_KEY = "db.host";
    public static final String DATABASE_KEY = "db.name";
    private final PostgresSnapshotCalculationDao dao;
    private int projId;
    private Collection<Integer> catIds;

    /**
     * Creates a display instance with the given DAO.
     * 
     * @param tgtDao
     *            The DAO to wrap.
     */
    public SnapshotPostgresDisplay(final PostgresSnapshotCalculationDao tgtDao) {
        dao = asNotNull(tgtDao, "DAO is null");
    }

    /**
     * Creates a display insance with a DAO configured by the given config
     * properties.
     * 
     * @param cfgProps
     *            The properties to use.
     */
    public SnapshotPostgresDisplay(final Properties cfgProps) {
        final PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setPassword(asNotNull(asNotNull(cfgProps, "DB properties are null")
                .getProperty(PASSWORD_KEY), String.format(
                "Value for key %s is null", PASSWORD_KEY)));
        ds.setUser(asNotNull(cfgProps.getProperty(USER_KEY),
                String.format("Value for key %s is null", USER_KEY)));
        ds.setServerName(cfgProps.getProperty(SERVER_KEY, DEFAULT_SERVER));
        ds.setDatabaseName(cfgProps.getProperty(DATABASE_KEY, DEFAULT_DB));
        dao = new DBI(ds).onDemand(PostgresSnapshotCalculationDao.class);
    }

    @Override
    public void finish(final Writer writer) {

    }

    @Override
    public void write(final Writer writer, final BaseResult valToDisp) {

    }

    @Override
    public MediaType getMediaType() {
        return MediaType.RDBMS;
    }

    @Override
    public boolean isWriteMulti() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWriteMulti(final boolean writeMulti) {
        // TODO Auto-generated method stub

    }

    public void setProjectName(String projectName) {
        
    }
}
