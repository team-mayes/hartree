package org.cmayes.hartree.loader.gaussian;

import java.io.FileReader;

import org.cmayes.hartree.model.LowestEnergyMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for {@link LowestEnergyLoader}.
 * 
 * @author cmayes
 */
public class TestLowestEnergyLoader {
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/g09/";
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testLoad() throws Exception {
        final LowestEnergyLoader loader = new LowestEnergyLoader();
        final LowestEnergyMapper load = loader.load(new FileReader(FILE_DIR_PFX
                + "aglc_b14_157.log"));
        logger.debug("Lowest: " + load);
    }
}
