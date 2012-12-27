package org.cmayes.hartree.disp.db;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.cmayes.hartree.disp.db.impl.PostgresJdbiSnapshotCalculationDao;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.DefaultCalculationCategory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cmayes.common.MediaType;

/**
 * Tests for {@link SnapshotJdbcDisplay}.
 * 
 * @author cmayes
 */
public class TestSnapshotJdbcDisplay {
    private static final long CAT_ID2 = 19L;

    private static final long CAT_ID1 = 17L;

    private static final String CAT2 = "cat2";

    private static final String CAT1 = "cat1";

    private static final String TEST_PROJ_NAME = "testProjectName";

    private static final long ID2 = 270L;

    private static final long ID = 99L;

    private static final String TEST_SOURCE_NAME = "testSourceName";

    @Mock
    private SnapshotCalculationDao calcDao;

    private SnapshotJdbcDisplay display;

    /**
     * Set up display to test.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        display = new SnapshotJdbcDisplay(calcDao);
    }

    /**
     * Tests the basic insert step.
     */
    @Test
    public void testHandle() {
        display.setProjectId(ID2);
        final BaseResult result = mock(BaseResult.class);
        when(result.getSourceName()).thenReturn(TEST_SOURCE_NAME);
        when(
                calcDao.insertCalculation(eq(ID2), eq(TEST_SOURCE_NAME),
                        anyListOf(Long.class))).thenReturn(ID);
        display.write(null, result);
        verify(calcDao).insertSummary(ID, result);
    }

    /**
     * Tests init plus insert.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testSetupHandle() {
        final List<String> catNames = Arrays.asList(CAT1, CAT2);
        when(calcDao.findCategories(catNames)).thenReturn(
                (List) Arrays
                        .asList(new DefaultCalculationCategory(CAT1, CAT_ID1),
                                new DefaultCalculationCategory(CAT2, CAT_ID2)));
        when(calcDao.findProjectId(TEST_PROJ_NAME)).thenReturn(ID2);
        display.setProjectConfig(TEST_PROJ_NAME, catNames);
        final BaseResult result = mock(BaseResult.class);
        when(result.getSourceName()).thenReturn(TEST_SOURCE_NAME);
        when(
                calcDao.insertCalculation(eq(ID2), eq(TEST_SOURCE_NAME),
                        (Collection<Long>) argThat(hasItems(CAT_ID1, CAT_ID2))))
                .thenReturn(ID);
        display.write(null, result);
        display.finish(null);
        verify(calcDao).insertSummary(ID, result);
    }

    /**
     * Tests init plus insert.
     */
    @Test
    public void testSetupNewProject() {
        when(calcDao.findProjectId(TEST_PROJ_NAME)).thenReturn(null);
        display.setProjectConfig(TEST_PROJ_NAME, new ArrayList<String>());
        verify(calcDao).insertProjectName(TEST_PROJ_NAME);
    }

    /**
     * Tests init plus insert.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testSomeNewCats() {
        when(calcDao.findProjectId(TEST_PROJ_NAME)).thenReturn(ID2);
        when(calcDao.findCategories(Arrays.asList(CAT1, CAT2))).thenReturn(
                (List) Arrays.asList(new DefaultCalculationCategory(CAT2,
                        CAT_ID2)));
        display.setProjectConfig(TEST_PROJ_NAME, Arrays.asList(CAT1, CAT2));
        verify(calcDao).insertCategoryNames(Arrays.asList(CAT1));
    }

    /**
     * Tests media type.
     */
    @Test
    public void testMediaType() {
        assertEquals(MediaType.RDBMS, display.getMediaType());
    }

    /**
     * Tests media type.
     */
    @Test
    public void testMulti() {
        assertFalse(display.isWriteMulti());
        display.setWriteMulti(true);
        assertTrue(display.isWriteMulti());
    }

    /**
     * Tests that we get a {@link PostgresJdbiSnapshotCalculationDao} when we
     * use the properties ctor.
     */
    @Test
    public void testPropsCtor() {
        final Properties dbProps = new Properties();
        dbProps.setProperty(JdbiUtils.PASSWORD_KEY, "pass");
        dbProps.setProperty(JdbiUtils.USER_KEY, "user");
        final SnapshotJdbcDisplay propsDisp = new SnapshotJdbcDisplay(dbProps);
        assertEquals(propsDisp.getDao().getClass(),
                PostgresJdbiSnapshotCalculationDao.class);
    }
}
