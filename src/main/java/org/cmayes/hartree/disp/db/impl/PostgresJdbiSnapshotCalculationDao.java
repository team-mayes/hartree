/**
 * 
 */
package org.cmayes.hartree.disp.db.impl;

import static com.cmayes.common.exception.ExceptionUtils.asNotBlank;
import static com.cmayes.common.exception.ExceptionUtils.asNotNull;
import static com.cmayes.common.exception.ExceptionUtils.asPositive;

import java.util.Properties;

import org.cmayes.hartree.disp.db.JdbiUtils;
import org.cmayes.hartree.disp.db.SnapshotCalculationDao;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;
import org.cmayes.hartree.model.def.DefaultCalculationCategory;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.exceptions.CallbackFailedException;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.util.IntegerMapper;
import org.skife.jdbi.v2.util.LongMapper;

import com.cmayes.common.exception.DatabaseException;
import com.cmayes.common.exception.EnvironmentException;

/**
 * @author cmayes
 * 
 */
public class PostgresJdbiSnapshotCalculationDao implements
        SnapshotCalculationDao {
    private final IDBI dbi;

    /**
     * Creates a DAO with a DBI handle initialized by the given properties file.
     * 
     * @param props
     *            The config file to use.
     */
    public PostgresJdbiSnapshotCalculationDao(final Properties props) {
        this.dbi = JdbiUtils.createDbHandle(props);
    }

    /**
     * Creates a DAO with the given DBI handle (mainly for testing).
     * 
     * @param dbiHandle
     *            The handle to use.
     */
    public PostgresJdbiSnapshotCalculationDao(final IDBI dbiHandle) {
        this.dbi = asNotNull(dbiHandle, "DBI handle is null");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findProjectId(java.lang.String)
     */
    @Override
    public Integer findProjectId(final String name) throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Integer>() {
                public Integer withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "select id from project where name = ?")
                            .bind(0, asNotBlank(name, "Project name is blank"))
                            .map(IntegerMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertProjectName(java.lang.String)
     */
    @Override
    public Integer insertProjectName(final String name)
            throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Integer>() {
                public Integer withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "INSERT INTO project (name) VALUES(?) RETURNING id")
                            .bind(0, asNotBlank(name, "Project name is blank"))
                            .map(IntegerMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertCalculation(int,
     *      java.lang.String)
     */
    @Override
    public Long insertCalculation(final int projectId, final String filename)
            throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "INSERT INTO calculation (project_id, filename) "
                                            + "VALUES(?, ?) RETURNING id")
                            .bind(0, asPositive(projectId))
                            .bind(1, asNotBlank(filename, "File name is blank"))
                            .map(LongMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findCalculationId(int,
     *      java.lang.String)
     */
    @Override
    public Long findCalculationId(final int projectId, final String filename)
            throws EnvironmentException {
        try {

            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "select id from calculation WHERE project_id = ? "
                                            + "AND filename = ?")
                            .bind(0, asPositive(projectId))
                            .bind(1, asNotBlank(filename, "File name is blank"))
                            .map(LongMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findCategoryId(java.lang.String)
     */
    @Override
    public Integer findCategoryId(final String name)
            throws EnvironmentException {
        try {

            return this.dbi.withHandle(new HandleCallback<Integer>() {
                public Integer withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "select id from category where name = ?")
                            .bind(0, asNotBlank(name, "Category name is null"))
                            .map(IntegerMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findCategory(java.lang.String)
     */
    @Override
    public CalculationCategory findCategory(final String name) {
        try {

            return this.dbi
                    .withHandle(new HandleCallback<CalculationCategory>() {
                        public CalculationCategory withHandle(final Handle conn) {
                            return conn
                                    .createQuery(
                                            "select id, name, description from category where name = ?")
                                    .bind(0,
                                            asNotBlank(name,
                                                    "Category name is null"))
                                    .map(DefaultCalculationCategory.class)
                                    .first();
                        }
                    });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertCategory(org.cmayes.hartree.model.CalculationCategory)
     */
    @Override
    public Integer insertCategory(final CalculationCategory category)
            throws EnvironmentException {
        try {

            return this.dbi.withHandle(new HandleCallback<Integer>() {
                public Integer withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "INSERT INTO category (name, description) "
                                            + "VALUES(:name, :description) RETURNING id")
                            .bindFromProperties(category)
                            .map(IntegerMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertCategoryName(java.lang.String)
     */
    @Override
    public Integer insertCategoryName(final String catName)
            throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Integer>() {
                public Integer withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "INSERT INTO category (name) VALUES(?) RETURNING id")
                            .bind(0, catName).map(IntegerMapper.FIRST).first();
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertSummary(long,
     *      org.cmayes.hartree.model.BaseResult)
     */
    @Override
    public void insertSummary(final long calcId, final BaseResult result) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findSummary(long)
     */
    @Override
    public BaseResult findSummary(final long calcId)
            throws EnvironmentException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Tries to dig a more meaningful nested exception out of the callback.
     * 
     * @param e
     *            The exception to evaluate.
     * @return An instance of {@link EnvironmentException} describing (and
     *         wrapping) the problem.
     */
    private EnvironmentException evalError(final CallbackFailedException e) {
        Throwable wrapMe;
        if (e.getCause() == null) {
            wrapMe = e;
        } else {
            wrapMe = e.getCause();
        }
        if (wrapMe instanceof DBIException) {
            return new DatabaseException("Problems with database call", wrapMe);
        }
        return new EnvironmentException("Problems with callback", wrapMe);
    }
}
