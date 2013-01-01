package org.cmayes.hartree.disp.db;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;
import static com.cmayes.common.exception.ExceptionUtils.asNotNullCollection;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.disp.db.impl.PostgresJdbiSnapshotCalculationDao;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.MediaType;
import com.cmayes.common.util.CollectionUtils2;

/**
 * @author cmayes
 * 
 */
public class SnapshotJdbcDisplay implements Display<BaseResult> {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Long projectId;
    private String projectName;
    private Collection<Long> catIds = new ArrayList<Long>(0);
    private final SnapshotCalculationDao dao;
    private boolean writeMulti;

    /**
     * Creates a display instance with the given DAO (mainly for testing).
     * 
     * @param calcDao
     *            The DAO instance to use.
     */
    public SnapshotJdbcDisplay(final SnapshotCalculationDao calcDao) {
        this.dao = asNotNull(calcDao, "Calc DAO is null");
    }

    /**
     * Creates a display instance with a DAO configured by the given config
     * properties.
     * 
     * @param cfgProps
     *            The properties to use.
     */
    public SnapshotJdbcDisplay(final Properties cfgProps) {
        this.dao = new PostgresJdbiSnapshotCalculationDao(cfgProps);
    }

    @Override
    public void finish(final Writer writer) {

    }

    @Override
    public void write(final Writer writer, final BaseResult valToDisp) {
        if (dao.findCalculationId(projectId, valToDisp.getSourceName()) == null) {
            final Long calcId = dao.insertCalculation(projectId,
                    valToDisp.getSourceName(), catIds);
            dao.insertSummary(calcId, valToDisp);
        } else {
            logger.warn(String.format(
                    "Source %s in project %s already exists; skipping.",
                    valToDisp.getSourceName(), projectName));
        }
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.RDBMS;
    }

    @Override
    public boolean isWriteMulti() {
        return writeMulti;
    }

    @Override
    public void setWriteMulti(final boolean multi) {
        this.writeMulti = multi;
    }

    /**
     * Finds or creates the project name and categories to use for this run.
     * 
     * @param projName
     *            The name of the project to use.
     * @param cats
     *            The categories for this run.
     */
    public synchronized void setProjectConfig(final String projName,
            final Collection<String> cats) {
        projectId = dao.findProjectId(asNotNull(projName,
                "Project name is null"));
        this.projectName = projName;
        if (projectId == null) {
            projectId = dao.insertProjectName(projName);
        }
        if (cats != null && cats.size() > 0) {
            final List<CalculationCategory> catEntries = dao
                    .findCategories(asNotNullCollection(cats,
                            "Category collection is null or has null values"));
            final Collection<Long> ids = CollectionUtils2.collectBeanValues(
                    catEntries, "id", Long.class);
            final ArrayList<String> newCats = new ArrayList<String>(cats);
            newCats.removeAll(CollectionUtils2.collectBeanValues(catEntries,
                    "name", String.class));
            ids.addAll(dao.insertCategoryNames(newCats).values());
            catIds = ids;
        }
    }

    /**
     * Test hook for setting a project ID.
     * 
     * @param id
     *            the projectId to set
     */
    void setProjectId(final Long id) {
        this.projectId = id;
    }

    /**
     * @return the dao
     */
    SnapshotCalculationDao getDao() {
        return dao;
    }
}
