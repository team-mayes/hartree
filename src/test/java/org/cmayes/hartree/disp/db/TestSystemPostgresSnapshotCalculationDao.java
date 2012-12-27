package org.cmayes.hartree.disp.db;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cmayes.hartree.disp.db.impl.PostgresJdbiSnapshotCalculationDao;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CalculationCategory;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
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
import com.cmayes.common.util.CollectionUtils2;

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
    private static final String CAT_NAME2 = "__TEST_PROJ_CATEGORY2__";
    private static final String CAT_NAME3 = "__TEST_PROJ_CATEGORY3__";
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
        final List<CalculationCategory> cats = dao.findCategories(Arrays
                .asList(CAT_NAME, CAT_NAME2, CAT_NAME3));
        if (!cats.isEmpty()) {
            final PreparedBatch calcCatBatch = dbHandle
                    .prepareBatch("DELETE FROM calc_category WHERE cat_id = ?");
            for (CalculationCategory calculationCategory : cats) {
                calcCatBatch.add(calculationCategory.getId());
            }
            calcCatBatch.execute();
            
            final PreparedBatch calcBaseBatch = dbHandle
                    .prepareBatch("DELETE FROM category WHERE id = ?");
            for (CalculationCategory calculationCategory : cats) {
                calcBaseBatch.add(calculationCategory.getId());
            }
            calcBaseBatch.execute();

        }
        final Long projId = dao.findProjectId(PROJ_NAME);
        Long calcId = null;
        if (projId != null) {
            calcId = dao.findCalculationId(projId, CALC_FNAME);
        }
        if (calcId != null) {
            dbHandle.execute("DELETE FROM calc_summary WHERE calc_id = ?",
                    calcId);
            dbHandle.execute("DELETE FROM cremer_pople WHERE calc_id = ?",
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
        Long id = dao.findProjectId(PROJ_NAME);
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
        final Long projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        final Long catId = dao.insertCategoryName(CAT_NAME);
        assertNotNull(catId);
        assertNull(dao.findCalculationId(projectId, CALC_FNAME));
        final Long calcId = dao.insertCalculation(projectId, CALC_FNAME,
                Arrays.asList(catId));
        assertEquals(calcId, dao.findCalculationId(projectId, CALC_FNAME));
    }

    /**
     * Tests that we can create a project row.
     */
    @Test(expected = DatabaseException.class)
    public void testCalcDuplicateInsert() {
        final Long projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        final Long catId = dao.insertCategoryName(CAT_NAME);
        assertNotNull(catId);
        dao.insertCalculation(projectId, CALC_FNAME, Arrays.asList(catId));
        dao.insertCalculation(projectId, CALC_FNAME, Arrays.asList(catId));
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
        final Long catId = dao.findCategoryId(CAT_NAME);
        assertEquals(catId, dao.findCategoryId(CAT_NAME));
    }

    /**
     * Tests that we can create a category row.
     */
    @Test
    public void testCategoriesFetch() {
        dao.insertCategoryName(CAT_NAME);
        dao.insertCategoryName(CAT_NAME2);
        dao.insertCategoryName(CAT_NAME3);
        final List<String> cats = Arrays.asList(CAT_NAME, CAT_NAME2, CAT_NAME3);
        final List<CalculationCategory> findCategories = dao
                .findCategories(cats);
        assertEquals(cats.size(), findCategories.size());
        assertThat(CollectionUtils2.collectBeanValues(findCategories, "name",
                String.class), hasItems(CAT_NAME, CAT_NAME2, CAT_NAME3));
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
        final Long catId = dao.insertCategoryName(CAT_NAME);
        assertEquals(catId, dao.findCategory(CAT_NAME).getId());
    }

    /**
     * Tests that we can create a category name row.
     */
    @Test
    public void testCategoryNamesInsert() {
        assertNull(dao.findCategoryId(CAT_NAME));
        Map<String, Long> catIdMap = dao.insertCategoryNames(Arrays.asList(
                CAT_NAME, CAT_NAME2, CAT_NAME3));
        assertEquals(3, catIdMap.size());
        for (Entry<String, Long> idEntry : catIdMap.entrySet()) {
            assertEquals(idEntry.getValue(), dao.findCategory(idEntry.getKey())
                    .getId());
        }
    }

    // Calculation Summary //

    /**
     * Tests inserting a base result.
     */
    @Test
    public void testSummaryInsert() {
        final Long projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        final Long catId = dao.insertCategoryName(CAT_NAME2);
        assertNotNull(catId);
        final Long calcId = dao.insertCalculation(projectId, CALC_FNAME,
                Arrays.asList(catId));
        assertNotNull(calcId);
        final BaseResult result = createBaseResultInstance();
        dao.insertSummary(calcId, result);
        assertEquals(result, dao.findSummary(calcId));
    }

    /**
     * Tests inserting a base result.
     */
    @Test
    public void testCpCoordsInsert() {
        final Long projectId = dao.insertProjectName(PROJ_NAME);
        assertNotNull(projectId);
        final Long catId = dao.insertCategoryName(CAT_NAME);
        assertNotNull(catId);
        final Long calcId = dao.insertCalculation(projectId, CALC_FNAME,
                Arrays.asList(catId));
        assertNotNull(calcId);
        final CpCalculationSnapshot cpInst = createCpInstance();
        dao.insertSummary(calcId, cpInst);
        assertEquals(cpInst, dao.findSummary(calcId));
    }

    /**
     * Creates a test base result instance.
     * 
     * @return A test base result instance.
     */
    private BaseResult createBaseResultInstance() {
        final DefaultBaseResult result = new DefaultBaseResult();
        result.setSourceName(CALC_FNAME);
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
        result.setSolvent("test solvent");
        result.setStoichiometry("stoi!");
        result.setSymmetricTop(true);
        return result;
    }

    /**
     * Creates a test base result instance.
     * 
     * @return A test base result instance.
     */
    private CpCalculationSnapshot createCpInstance() {
        final CpCalculationSnapshot result = new CpCalculationSnapshot();
        result.setSourceName(CALC_FNAME);
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
        result.setSolvent("test solvent");
        result.setStoichiometry("stoi!");
        result.getCarbonDistances().add(99.0);
        result.getCarbonDistances().add(6633.22);
        result.getCarbonDistances().add(8888.);
        result.getCarbonDistances().add(9.);
        result.getOxygenDistances().add(9292.1);
        result.getOxygenDistances().add(77.0022);
        result.getOxygenDistances().add(13.3332);
        CremerPopleCoordinates cpCoords = new CremerPopleCoordinates(992.,
                44321.66, 1919192.2);
        cpCoords.setPucker("14b");
        result.setCpCoords(cpCoords);
        return result;
    }
}
