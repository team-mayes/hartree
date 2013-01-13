package org.cmayes.hartree.disp.txt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.cmayes.hartree.model.LowestEnergyMapper;
import org.junit.Test;

import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;
import com.cmayes.common.util.EnvUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for {@link LowestEnergyTemplateDisplay}.
 * 
 * @author cmayes
 */
public class TestLowestEnergyTemplateDisplay {
    /** The prefix for file locations. */
    private static final String FILE_DIR_PFX = "src/test/resources/files/";
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Checks that the display matches a previously-generated example.
     * 
     * @throws Exception
     *             When there's a problem.
     */
    @Test
    public void testSimple() throws Exception {
        final List<Atom> results = mapper.readValue(new File(FILE_DIR_PFX,
                "json/aglc_b14_157.json"),
                new TypeReference<List<DefaultAtom>>() {
                });
        final LowestEnergyMapper lowMap = new LowestEnergyMapper();
        lowMap.add(1, results);
        final Writer stringWriter = new StringWriter();
        new LowestEnergyTemplateDisplay().write(stringWriter, lowMap);
        assertEquals(EnvUtils.getStringFromReader(new FileReader(new File(
                FILE_DIR_PFX, "txt/aglc_b14_157.txt"))),
                stringWriter.toString());
    }
}
