/**
 * 
 */
package org.cmayes.hartree.disp.db.impl;

import static com.cmayes.common.exception.ExceptionUtils.asNotBlank;
import static com.cmayes.common.exception.ExceptionUtils.asNotNull;
import static com.cmayes.common.exception.ExceptionUtils.asNotNullCollection;
import static com.cmayes.common.exception.ExceptionUtils.asPositive;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.cmayes.hartree.disp.db.HartreeBeanMapper;
import org.cmayes.hartree.disp.db.JdbiUtils;
import org.cmayes.hartree.disp.db.PostgresArrayArgumentFactory;
import org.cmayes.hartree.disp.db.SnapshotCalculationDao;
import org.cmayes.hartree.disp.db.SqlArray;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
import org.cmayes.hartree.model.def.CremerPopleResult;
import org.cmayes.hartree.model.def.DefaultBaseResult;
import org.cmayes.hartree.model.def.DefaultCalculationCategory;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.PreparedBatch;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.exceptions.CallbackFailedException;
import org.skife.jdbi.v2.exceptions.DBIException;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.util.LongMapper;

import com.cmayes.common.exception.DatabaseException;
import com.cmayes.common.exception.EnvironmentException;

/**
 * @author cmayes
 * 
 */
public class PostgresJdbiSnapshotCalculationDao implements
        SnapshotCalculationDao {
    private static final String CAT_INS = "INSERT INTO category (name) VALUES(?) RETURNING id";
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
    public Long findProjectId(final String name) throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "select id from project where name = ?")
                            .bind(0, asNotBlank(name, "Project name is blank"))
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
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertProjectName(java.lang.String)
     */
    @Override
    public Long insertProjectName(final String name)
            throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "INSERT INTO project (name) VALUES(?) RETURNING id")
                            .bind(0, asNotBlank(name, "Project name is blank"))
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
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertCalculation(long,
     *      java.lang.String)
     */
    @Override
    public Long insertCalculation(final long projectId, final String filename,
            final Collection<Long> catIds) throws EnvironmentException {
        try {
            return this.dbi.inTransaction(new TransactionCallback<Long>() {
                @Override
                public Long inTransaction(final Handle conn,
                        final TransactionStatus status) throws Exception {
                    final Long calcId = conn
                            .createQuery(
                                    "INSERT INTO calculation (project_id, filename) "
                                            + "VALUES(?, ?) RETURNING id")
                            .bind(0, asPositive(projectId))
                            .bind(1, asNotBlank(filename, "File name is blank"))
                            .map(LongMapper.FIRST).first();
                    final PreparedBatch batchHandle = conn
                            .prepareBatch("INSERT INTO calc_category (calc_id, cat_id) VALUES(?, ?)");
                    for (Long catId : asNotNullCollection(catIds,
                            "Categories is null")) {
                        batchHandle.add().bind(0, calcId).bind(1, catId);
                    }
                    final int[] execResult = batchHandle.execute();
                    int totalRows = 0;
                    for (int i = 0; i < execResult.length; i++) {
                        totalRows += execResult[i];
                    }
                    if (totalRows != catIds.size()) {
                        throw new EnvironmentException(
                                "Tried to add %d categories to calc %s but result was %d",
                                catIds.size(), filename, totalRows);
                    }
                    return calcId;
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findCalculationId(long,
     *      java.lang.String)
     */
    @Override
    public Long findCalculationId(final long projectId, final String filename)
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
    public Long findCategoryId(final String name) throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "select id from category where name = ?")
                            .bind(0, asNotBlank(name, "Category name is null"))
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
     * Returns a list of categories with the given names (may not include all
     * names if some are not found).
     * 
     * @param name
     *            The names to fetch.
     * @return Any categories found.
     */
    @Override
    public List<CalculationCategory> findCategories(
            final Collection<String> name) {
        if (asNotNullCollection(name, "Category name list or elements is null")
                .isEmpty()) {
            throw new IllegalArgumentException("Empty name list");
        }
        try {
            return this.dbi
                    .withHandle(new HandleCallback<List<CalculationCategory>>() {
                        @SuppressWarnings({ "unchecked", "rawtypes" })
                        public List<CalculationCategory> withHandle(
                                final Handle conn) {
                            conn.registerArgumentFactory(new PostgresArrayArgumentFactory());
                            return (List) conn
                                    .createQuery(
                                            "select id, name, description from category where name = any(?)")
                                    .bind(0,
                                            new SqlArray<String>(
                                                    String.class,
                                                    asNotNullCollection(name,
                                                            "Category name list or elements is null")))
                                    .map(DefaultCalculationCategory.class)
                                    .list();
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
    public Long insertCategory(final CalculationCategory category)
            throws EnvironmentException {
        try {

            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn
                            .createQuery(
                                    "INSERT INTO category (name, description) "
                                            + "VALUES(:name, :description) RETURNING id")
                            .bindFromProperties(category).map(LongMapper.FIRST)
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
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertCategoryName(java.lang.String)
     */
    @Override
    public Long insertCategoryName(final String catName)
            throws EnvironmentException {
        try {
            return this.dbi.withHandle(new HandleCallback<Long>() {
                public Long withHandle(final Handle conn) {
                    return conn.createQuery(CAT_INS).bind(0, catName)
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
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#insertCategoryNames(Collection)
     */
    @Override
    public Map<String, Long> insertCategoryNames(
            final Collection<String> catNames) throws EnvironmentException {
        if (catNames == null || catNames.isEmpty()) {
            return new HashMap<String, Long>(0);
        }

        try {
            return this.dbi
                    .inTransaction(new TransactionCallback<Map<String, Long>>() {
                        @Override
                        public Map<String, Long> inTransaction(final Handle conn,
                                final TransactionStatus status) throws Exception {

                            final HashMap<String, Long> resultMap = new HashMap<String, Long>(
                                    catNames.size());
                            for (String curName : catNames) {
                                resultMap.put(curName, conn
                                        .createQuery(CAT_INS).bind(0, curName)
                                        .map(LongMapper.FIRST).first());
                            }
                            return resultMap;
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
        try {
            this.dbi.inTransaction(new TransactionCallback<Object>() {
                @Override
                public Object inTransaction(final Handle conn,
                        final TransactionStatus status) throws Exception {
                    conn.registerArgumentFactory(new PostgresArrayArgumentFactory());
                    final int baseCount = conn
                            .createStatement(
                                    "INSERT INTO calc_summary (calc_id, solvent_type, "
                                            + "stoichiometry, charge, multiplicity, functional, basis_set, "
                                            + "energy, dipole, zpe, h298, g298, frequencies) VALUES (:calcId, "
                                            + ":solvent, :stoichiometry, :charge, :mult, :functional, "
                                            + ":basisSet, :elecEn, :dipoleMomentTotal, :zpeCorrection, "
                                            + ":enthalpy298, :gibbs298, :freqs)")
                            .bind("calcId", calcId)
                            .bind("freqs",
                                    new SqlArray<Double>(Double.class, result
                                            .getFrequencyValues()))
                            .bindFromProperties(result).execute();
                    if (baseCount != 1) {
                        throw new DatabaseException(
                                "'%d' rows returned from base insert rather than 1",
                                baseCount);
                    }
                    if (result instanceof CremerPopleResult) {
                        final CremerPopleResult cpResult = (CremerPopleResult) result;
                        final CremerPopleCoordinates cpCoords = cpResult
                                .getCpCoords();
                        if (cpCoords == null) {
                            final int cpCount = conn
                                    .createStatement(
                                            "INSERT INTO cremer_pople (calc_id, r, o) "
                                                    + "VALUES (:calcId, :carbs, :oxys)")
                                    .bind("calcId", calcId)
                                    .bind("carbs",
                                            new SqlArray<Double>(
                                                    Double.class,
                                                    cpResult.getCarbonDistances()))
                                    .bind("oxys",
                                            new SqlArray<Double>(
                                                    Double.class,
                                                    cpResult.getOxygenDistances()))
                                    .execute();
                            if (cpCount != 1) {
                                throw new DatabaseException(
                                        "'%d' rows returned from cp insert rather than 1",
                                        cpCount);
                            }
                        } else {
                            final int cpCount = conn
                                    .createStatement(
                                            "INSERT INTO cremer_pople (calc_id, phi, theta, q, pucker, r, o) "
                                                    + "VALUES (:calcId, :phi, :theta, :q, :pucker, :carbs, "
                                                    + ":oxys)")
                                    .bind("calcId", calcId)
                                    .bind("carbs",
                                            new SqlArray<Double>(
                                                    Double.class,
                                                    cpResult.getCarbonDistances()))
                                    .bind("oxys",
                                            new SqlArray<Double>(
                                                    Double.class,
                                                    cpResult.getOxygenDistances()))
                                    .bindFromProperties(cpCoords).execute();
                            if (cpCount != 1) {
                                throw new DatabaseException(
                                        "'%d' rows returned from cp insert rather than 1",
                                        cpCount);
                            }
                        }

                    }
                    return null;
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.db.SnapshotCalculationDao#findSummary(long)
     */
    @Override
    public BaseResult findSummary(final long calcId)
            throws EnvironmentException {
        try {

            return this.dbi.withHandle(new HandleCallback<BaseResult>() {
                public BaseResult withHandle(final Handle conn) {
                    final CremerPopleCoordinates cpCoords = conn
                            .createQuery(
                                    "SELECT calc_id AS calcId, phi, theta, q, pucker FROM cremer_pople WHERE calc_id = ?")
                            .bind(0, asPositive(calcId))
                            .map(new HartreeBeanMapper<CremerPopleCoordinates>(
                                    CremerPopleCoordinates.class)).first();
                    if (cpCoords == null) {
                        return conn
                                .createQuery(
                                        "SELECT c.filename AS sourceName, cs.calc_id AS calcId, cs.solvent_type AS solvent, cs.stoichiometry, cs.charge, "
                                                + "cs.multiplicity AS mult, cs.functional, cs.basis_set AS basisSet, cs.energy "
                                                + "AS elecEn, cs.dipole AS dipoleMomentTotal, cs.zpe AS zpeCorrection, cs.h298 AS enthalpy298, "
                                                + "cs.g298 AS gibbs298, cs.frequencies AS frequencyValues FROM calc_summary cs, calculation c "
                                                + "WHERE c.id = ? AND calc_id = c.id")
                                .bind(0, asPositive(calcId))
                                .map(new HartreeBeanMapper<DefaultBaseResult>(
                                        DefaultBaseResult.class)).first();
                    }
                    final CpCalculationSnapshot cpSnap = conn
                            .createQuery(
                                    "SELECT c.filename AS sourceName, cs.calc_id AS calcId, cs.solvent_type AS solvent, cs.stoichiometry, cs.charge, "
                                            + "cs.multiplicity AS mult, cs.functional, cs.basis_set AS basisSet, cs.energy "
                                            + "AS elecEn, cs.dipole AS dipoleMomentTotal, cs.zpe AS zpeCorrection, cs.h298 AS enthalpy298, "
                                            + "cs.g298 AS gibbs298, cs.frequencies AS frequencyValues, cp.r AS carbonDistances, cp.o "
                                            + "AS oxygenDistances FROM calc_summary cs, cremer_pople cp, calculation c "
                                            + "WHERE c.id = ? AND cp.calc_id = c.id AND cs.calc_id = cp.calc_id")
                            .bind(0, asPositive(calcId))
                            .map(new HartreeBeanMapper<CpCalculationSnapshot>(
                                    CpCalculationSnapshot.class)).first();
                    cpSnap.setCpCoords(cpCoords);
                    return cpSnap;
                }
            });
        } catch (final CallbackFailedException e) {
            throw evalError(e);
        }
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
            Throwable inner = e.getCause();
            while (inner != null) {
                wrapMe = inner;
                inner = inner.getCause();
            }
            return new DatabaseException("Problems with database call", wrapMe);
        }
        return new EnvironmentException("Problems with callback", wrapMe);
    }
}
