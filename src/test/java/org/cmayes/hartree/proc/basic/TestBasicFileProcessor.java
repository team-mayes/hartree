package org.cmayes.hartree.proc.basic;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;

import org.cmayes.hartree.HandlingType;
import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.loader.Loader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.CommonConstants;
import com.cmayes.common.MediaType;
import com.cmayes.common.util.EnvUtils;

public class TestBasicFileProcessor {
    private static final File INDIR = new File(CommonConstants.TMPDIR,
            "basicfpin");
    private static final File OUTDIR = new File(CommonConstants.TMPDIR,
            "basicfpout");
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp() {
        if (INDIR.exists()) {
            EnvUtils.recursiveDelete(INDIR);
        }
        if (OUTDIR.exists()) {
            EnvUtils.recursiveDelete(OUTDIR);
        }
        if (!INDIR.mkdirs()) {
            logger.warn("Couldn't create " + INDIR);
        }
        if (!OUTDIR.mkdirs()) {
            logger.warn("Couldn't create " + OUTDIR);
        }
    }

    @After
    public void tearDown() {
        if (INDIR.exists()) {
            EnvUtils.recursiveDelete(INDIR);
        }
        if (OUTDIR.exists()) {
            EnvUtils.recursiveDelete(INDIR);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGood() throws Exception {
        File inFile = new File(INDIR, "someInFile.txt");
        if (!inFile.createNewFile()) {
            logger.warn("Couldn't create " + inFile);
        }
        Loader<Object> loader = mock(Loader.class);
        Display<Object> disp = mock(Display.class);
        when(disp.getMediaType()).thenReturn(MediaType.TEXT);
        BasicFileProcessor<Object> proc = new BasicFileProcessor<Object>(
                HandlingType.TEST, loader, disp, new ArrayList<Calculation>(),
                OUTDIR);
        proc.display(inFile);
        File outFile = new File(OUTDIR, "someInFile.txt-test.txt");
        assertTrue(outFile.exists());
    }
}
