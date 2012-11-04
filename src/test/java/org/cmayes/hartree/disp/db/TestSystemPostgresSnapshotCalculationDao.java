package org.cmayes.hartree.disp.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.cmayes.hartree.disp.db.impl.PostgresJdbiSnapshotCalculationDao;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;
import org.cmayes.hartree.model.def.DefaultBaseResult;
import org.cmayes.hartree.model.def.DefaultCalculationCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.PreparedBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.exception.DatabaseException;

/**
 * Tests for {@link SnapshotCalculationDao}.
 * 
 * @author cmayes
 */
public class TestSystemPostgresSnapshotCalculationDao {
    private static final String CAT_DESC = "Description of the category";
    private static final String PROJ_NAME = "__TEST_PROJ_DAO__";
    private static final String CALC_FNAME = "__TEST_PROJ_CALC_FILE__";
    private static final String CAT_NAME = "__TEST_PROJ_CATEGORY__";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SnapshotCalculationDao dao;
    private DBI dbi;

    /**
     * Creates the JDBI connection and the DAO proxy.
     */
    @Before
    public void setUp() {
        dbi = new DBI("jdbc:postgresql://localhost/hartree_dev", "dev", "g00d@");
        this.dao = new PostgresJdbiSnapshotCalculationDao(dbi);
    }

    /**
     * Makes sure the data under test is removed.
     */
    @After
    public void tearDown() {
        // TODO: Look into using transactions to roll back changes instead
        // of manual deletes.
        final Handle dbHandle = dbi.open();
        final Integer catId = dao.findCategoryId(CAT_NAME);
        if (catId != null) {
            final PreparedBatch calcBaseBatch = dbHandle
                    .prepareBatch("DELETE FROM category WHERE id = ?");
            calcBaseBatch.add(catId);
            calcBaseBatch.execute();
        }
        final Integer projId = dao.findProjectId(PROJ_NAME);
        Long calcId = null;
        if (projId != null) {
            calcId = dao.findCalculationId(projId, CALC_FNAME);
        }
        if (calcId != null) {
            dbHandle.execute("DELETE FROM calc_summary WHERE calc_id = ?",
                    calcId);
            final PreparedBatch calcBaseBatch = dbHandle
                    .prepareBatch("DELETE FROM calculation WHERE id = ?");
            calcBaseBatch.add(calcId);
            calcBaseBatch.execute();
        }
        final PreparedBatch projBatch = dbHandle
                .prepareBatch("DELETE FROM project WHERE id = ?");
        projBatch.add(projId);
        projBatch.execute();
    }

    // Project //

    /**
     * Tests that we can create a project row.
     */
    @Test
    public void testProjectInsert() {
        Integer id = dao.findProjectId(PROJ_NAME);
        assertNull(id);
        id = dao.insertProjectName(PROJ_NAME);
        assertNotNull(id);
        assertEquals(id, dao.findProjectId(PROJ_NAME));
    }

    /**
     * Makes sure that duplicate entries give us an error.
     */
    @Test(expected = DatabaseException.class)
    public void testProjectDuplicateInsert() {
        dao.insertProjectName(PROJ_NAME);
        dao.insertProjectName(PROJ_NAME);
    }

    // Calculation header //

    /**
     * Tests that we can create a project row.
     */
    @Test
    public void testCalcInsert() {
        final Integer projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        assertNull(dao.findCalculationId(projectId, CALC_FNAME));
        final Long calcId = dao.insertCalculation(projectId, CALC_FNAME);
        assertEquals(calcId, dao.findCalculationId(projectId, CALC_FNAME));
    }

    /**
     * Tests that we can create a project row.
     */
    @Test(expected = DatabaseException.class)
    public void testCalcDuplicateInsert() {
        final Integer projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        dao.insertCalculation(projectId, CALC_FNAME);
        dao.insertCalculation(projectId, CALC_FNAME);
    }

    // Category //

    /**
     * Tests that we can create a category row.
     */
    @Test
    public void testCategoryInsert() {
        assertNull(dao.findCategoryId(CAT_NAME));
        final DefaultCalculationCategory catBean = new DefaultCalculationCategory(
                CAT_NAME, CAT_DESC);
        dao.insertCategory(catBean);
        final Integer catId = dao.findCategoryId(CAT_NAME);
        assertEquals(catId, dao.findCategoryId(CAT_NAME));
    }

    /**
     * Tests that we can create a category row and fetch the filled bean.
     */
    @Test
    public void testCategoryBeanFetch() {
        assertNull(dao.findCategoryId(CAT_NAME));
        final DefaultCalculationCategory catBean = new DefaultCalculationCategory(
                CAT_NAME, CAT_DESC);
        dao.insertCategory(catBean);
        final CalculationCategory resCat = dao.findCategory(CAT_NAME);
        catBean.setId(resCat.getId());
        assertEquals(catBean, resCat);
    }

    /**
     * Tests that duplicate entries fail.
     */
    @Test(expected = DatabaseException.class)
    public void testCategoryDuplicateInsert() {
        assertNull(dao.findCategoryId(CAT_NAME));
        final DefaultCalculationCategory catBean = new DefaultCalculationCategory(
                CAT_NAME, CAT_DESC);
        dao.insertCategory(catBean);
        dao.insertCategory(catBean);
    }

    /**
     * Tests that we can create a category name row.
     */
    @Test
    public void testCategoryNameInsert() {
        assertNull(dao.findCategoryId(CAT_NAME));
        dao.insertCategoryName(CAT_NAME);
        final Integer catId = dao.findCategoryId(CAT_NAME);
        assertEquals(catId, dao.findCategoryId(CAT_NAME));
    }

    // Calculation Summary //

    /**
     * Tests inserting a base result.
     */
    @Test
    public void testSummaryInsert() {
        final Integer projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        final Long calcId = dao.insertCalculation(projectId, CALC_FNAME);
        assertNotNull(calcId);
        final BaseResult result = createBaseResultInstance();
        dao.insertSummary(calcId, result);
        final BaseResult saved = dao.findSummary(calcId);
    }

    /**
     * Creates a test base result instance.
     * 
     * @return A test base result instance.
     */
    private BaseResult createBaseResultInstance() {
        final DefaultBaseResult result = new DefaultBaseResult(CALC_FNAME);
        result.setAtomCount(10);
        result.setBasisSet("test basis set");
        result.setCharge(3);
        result.setDipoleMomentTotal(99.44);
        result.setElecEn(78.33);
        result.setEnthalpy298(1.9);
        result.getFrequencyValues().add(11.32);
        result.getFrequencyValues().add(8888.3);
        result.getFrequencyValues().add(99223.22);
        result.setFunctional("aaw2");
        result.setGibbs298(44322.22);
        result.setMult(2);
        result.setRotPart(323.8756);
        result.setSolvent("test solvent");
        result.setStoichiometry("stoi!");
        result.setSymmetricTop(true);
        result.setTransPart(7777.01);
        return result;
    }
}
