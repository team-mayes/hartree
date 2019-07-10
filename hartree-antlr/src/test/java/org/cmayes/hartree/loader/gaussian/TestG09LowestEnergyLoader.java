package org.cmayes.hartree.loader.gaussian;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.cmayes.hartree.model.LowestEnergyMapper;
import org.junit.Test;

import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for {@link LowestEnergyLoader}.
 * 
 * @author cmayes
 */
public class TestG09LowestEnergyLoader {
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/";
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Test.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testLoad() throws Exception {
        final LowestEnergyLoader loader = new LowestEnergyLoader();
        final LowestEnergyMapper load = loader.load("g09/aglc_b14_157.log",
                new FileReader(FILE_DIR_PFX + "g09/aglc_b14_157.log"));
        final List<Atom> results = mapper.readValue(new File(FILE_DIR_PFX,
                "json/aglc_b14_157.json"),
                new TypeReference<List<DefaultAtom>>() {
                });
        assertEquals(results, load.getLowestEnergy());
    }
}
