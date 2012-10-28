package org.cmayes.hartree.disp.db;

import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

/**
 * Provides an interface for interacting with a Postgres database for saving
 * snapshot calculations.
 * 
 * TODO: calc_sum, cremer-pople, listings
 * 
 * @author cmayes
 */
public interface PostgresSnapshotCalculationDao extends
        Transactional<PostgresSnapshotCalculationDao> {
    // Project //

    /**
     * Returns the ID for the given name.
     * 
     * @param name
     *            The name to look for.
     * @return The ID for the new entry.
     * @throws UnableToExecuteStatementException
     *             When the query fails.
     */
    @SqlQuery("select id from project where name = :it")
    Integer findProjectId(@Bind String name);

    /**
     * Creates a project for the given name.
     * 
     * @param name
     *            The name to look for.
     * @return The ID or null if none is found.
     * @throws UnableToExecuteStatementException
     *             When the insert fails.
     */
    @SqlQuery("INSERT INTO project (name) VALUES(:it) RETURNING id")
    Integer insertProjectName(@Bind String name);

    // Calculation header //

    /**
     * Returns the ID for the given name.
     * 
     * @param projectId
     *            The project associated with this calculation.
     * @param filename
     *            The name to look for.
     * @return The ID for the new entry.
     * @throws UnableToExecuteStatementException
     *             When the insert fails.
     */
    @SqlQuery("INSERT INTO calculation (project_id, filename) "
            + "VALUES(:projectId, :filename) RETURNING id")
    Long insertCalculation(@Bind("projectId") int projectId,
            @Bind("filename") String filename);

    /**
     * Returns the ID for the given file and project.
     * 
     * @param projectId
     *            The ID of the project to look for.
     * @param filename
     *            The file name to look for.
     * @return The ID or null if none is found.
     * @throws UnableToExecuteStatementException
     *             When the query fails.
     */
    @SqlQuery("select id from calculation WHERE project_id = :projectId "
            + "AND filename = :filename")
    Long findCalculationId(@Bind("projectId") int projectId,
            @Bind("filename") String filename);

    // Category //

    /**
     * Returns the ID for the given category name.
     * 
     * @param name
     *            The name to look for.
     * @return The ID or null if none is found.
     * @throws UnableToExecuteStatementException
     *             When the query fails.
     */
    @SqlQuery("select id from category where name = :it")
    Integer findCategoryId(@Bind String name);

    /**
     * Returns the bean for the given category name.
     * 
     * @param name
     *            The name to look for.
     * @return The bean or null if none is found.
     * @throws UnableToExecuteStatementException
     *             When the query fails.
     */
    @Mapper(CalculationCategoryMapper.class)
    @SqlQuery("select id, name, description from category where name = :it")
    CalculationCategory findCategory(@Bind String name);

    /**
     * Creates the category.
     * 
     * @param category
     *            The bean to insert.
     * @return The ID for the new entry.
     * @throws UnableToExecuteStatementException
     *             When the insert fails.
     */
    @SqlQuery("INSERT INTO category (name, description) "
            + "VALUES(:name, :description) RETURNING id")
    Integer insertCategory(@BindBean CalculationCategory category);

    /**
     * Creates the category with the given name.
     * 
     * @param catName
     *            The name to insert.
     * @return The ID for the new entry.
     * @throws UnableToExecuteStatementException
     *             When the insert fails.
     */
    @SqlQuery("INSERT INTO category (name) VALUES(:it) RETURNING id")
    Integer insertCategoryName(@Bind String catName);

    // Summary //

    /**
     * Inserts a calculation summary entry for the given calc ID.
     * 
     * @param calcId
     *            The calculation's ID.
     * @param result
     *            The result to save.
     */
    @SqlQuery("INSERT INTO calc_summary (calc_id, solvent_type, "
            + "stoichiometry, charge, multiplicity, functional, basis_set, "
            + "energy, dipole, zpe, h298, g298, frequencies) VALUES (:calcId, "
            + ":solvent, :stoichiometry, :charge, :mult, :functional, "
            + ":basisSet, :elecEn, :dipoleMomentTotal, :zpeCorrection, "
            + ":gibbs298, :enthalpy298, :frequencyValues)")
    void insertSummary(@Bind("calcId") long calcId, @BindBean BaseResult result);

    /**
     * Returns the summary for the given calculation ID.
     * 
     * @param calcId
     * @return
     */
    @Mapper(BaseResultMapper.class)
    @SqlQuery("SELECT calc_id, solvent_type, stoichiometry, charge, "
            + "multiplicity, functional, basis_set, energy, dipole, zpe, h298, "
            + "g298, frequencies FROM  calc_summary WHERE calc_id = :it")
    BaseResult findSummary(@Bind("calcId") long calcId);
}