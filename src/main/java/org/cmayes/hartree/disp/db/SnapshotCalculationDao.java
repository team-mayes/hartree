package org.cmayes.hartree.disp.db;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;

import com.cmayes.common.exception.EnvironmentException;

/**
 * Provides an interface for interacting with a Postgres database for saving
 * snapshot calculations.
 * 
 * @author cmayes
 */
public interface SnapshotCalculationDao {
    // Project //

    /**
     * Returns the ID for the given name.
     * 
     * @param name
     *            The name to look for.
     * @return The ID for the new entry.
     * @throws EnvironmentException
     *             When the query fails.
     */
    Long findProjectId(String name) throws EnvironmentException;

    /**
     * Creates a project for the given name.
     * 
     * @param name
     *            The name to look for.
     * @return The ID or null if none is found.
     * @throws EnvironmentException
     *             When the insert fails.
     */
    Long insertProjectName(String name) throws EnvironmentException;

    // Calculation header //

    /**
     * Returns the ID for the given name.
     * 
     * @param projectId
     *            The project associated with this calculation.
     * @param filename
     *            The name to look for.
     * @param catIds
     *            The category IDs for this calculation.
     * @return The ID for the new entry.
     * @throws EnvironmentException
     *             When the insert fails.
     */
    Long insertCalculation(long projectId, String filename,
            Collection<Long> catIds) throws EnvironmentException;

    /**
     * Returns the ID for the given file and project.
     * 
     * @param projectId
     *            The ID of the project to look for.
     * @param filename
     *            The file name to look for.
     * @return The ID or null if none is found.
     * @throws EnvironmentException
     *             When the query fails.
     */
    Long findCalculationId(long projectId, String filename)
            throws EnvironmentException;

    // Category //

    /**
     * Returns the ID for the given category name.
     * 
     * @param name
     *            The name to look for.
     * @return The ID or null if none is found.
     * @throws EnvironmentException
     *             When the query fails.
     */
    Long findCategoryId(String name) throws EnvironmentException;

    /**
     * Returns the bean for the given category name.
     * 
     * @param name
     *            The name to look for.
     * @return The bean or null if none is found.
     * @throws EnvironmentException
     *             When the query fails.
     */
    CalculationCategory findCategory(String name) throws EnvironmentException;

    /**
     * Returns a list of categories with the given names (may not include all
     * names if some are not found).
     * 
     * @param name
     *            The names to fetch.
     * @return Any categories found.
     */
    List<CalculationCategory> findCategories(final Collection<String> name);

    /**
     * Creates the category.
     * 
     * @param category
     *            The bean to insert.
     * @return The ID for the new entry.
     * @throws EnvironmentException
     *             When the insert fails.
     */
    Long insertCategory(CalculationCategory category)
            throws EnvironmentException;

    /**
     * Creates the category with the given name.
     * 
     * @param catName
     *            The name to insert.
     * @return The ID for the new entry.
     * @throws EnvironmentException
     *             When the insert fails.
     */
    Long insertCategoryName(String catName) throws EnvironmentException;

    /**
     * Creates categories for each name.
     * 
     * @param catNames
     *            The names to insert.
     * @return Names mapped to their IDs.
     * @throws EnvironmentException
     *             When any insert fails.
     */
    Map<String, Long> insertCategoryNames(final Collection<String> catNames)
            throws EnvironmentException;

    // Summary //

    /**
     * Inserts a calculation summary entry for the given calc ID.
     * 
     * @param calcId
     *            The calculation's ID.
     * @param result
     *            The result to save.
     * @throws EnvironmentException
     *             When the insert fails.
     */
    void insertSummary(long calcId, BaseResult result)
            throws EnvironmentException;

    /**
     * Returns the summary for the given calculation ID.
     * 
     * @param calcId
     *            The calculation's ID.
     * @return The summary or null.
     * @throws EnvironmentException
     *             When the query fails.
     */
    BaseResult findSummary(long calcId) throws EnvironmentException;
}