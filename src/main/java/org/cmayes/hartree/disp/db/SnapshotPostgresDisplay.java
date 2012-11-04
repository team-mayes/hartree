package org.cmayes.hartree.disp.db;

import static com.cmayes.common.exception.ExceptionUtils.asNotBlank;
import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.Writer;
import java.util.Collection;
import java.util.Properties;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.disp.db.impl.PostgresJdbiSnapshotCalculationDao;
import org.cmayes.hartree.model.BaseResult;
import org.postgresql.ds.PGPoolingDataSource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.util.IntegerMapper;

import com.cmayes.common.MediaType;

/**
 * @author cmayes
 * 
 */
public class SnapshotPostgresDisplay implements Display<BaseResult> {

    private Integer projId;
    private Collection<Integer> catIds;
    private final SnapshotCalculationDao dao;

    /**
     * Creates a display instance with the given DAO (mainly for testing).
     * 
     * @param calcDao
     *            The DAO instance to use.
     */
    public SnapshotPostgresDisplay(final SnapshotCalculationDao calcDao) {
        this.dao = asNotNull(calcDao, "Calc DAO is null");
    }

    /**
     * Creates a display instance with a DAO configured by the given config
     * properties.
     * 
     * @param cfgProps
     *            The properties to use.
     */
    public SnapshotPostgresDisplay(final Properties cfgProps) {
        this.dao = new PostgresJdbiSnapshotCalculationDao(cfgProps);
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

    /**
     * Finds or creates the project name in the database.
     * 
     * @param projectName
     *            THe name of the project to create.
     */
    public void setProjectName(final String projectName) {
 
    }
}
