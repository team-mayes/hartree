package org.cmayes.hartree.disp.csv;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.DefaultBaseResult;
import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

import com.cmayes.common.MediaType;

/**
 * Tests for {@link SnapshotCsvDisplay}.
 * 
 * @author cmayes
 */
public class TestCalculationSnapshotCsvDisplay {
    /** Logger. */
    private static final String[] HEAD_LINE = { "File Name", "Solvent type",
            "Stoichiometry", "Charge", "Mult", "Functional", "Basis Set",
            "Energy (A.U.)", "dipole", "ZPE (kcal/mol)", "G298 (Hartrees)",
            "H298 (Hartrees)", "Freq 1", "Freq 2" };
    private static final String[] ALT_HEAD_LINE = { "Alt File Name",
            "Solvent type", "Stoichiometry", "Charge", "Alt Mult",
            "Functional", "Basis Set", "Energy (A.U.)", "dipole",
            "ZPE (kcal/mol)", "G298 (Hartrees)", "H298 (Hartrees)", "Freq 1",
            "Freq 2" };
    private static final String[] DATA_LINE = { "someFileName.txt", "water",
            "C6H12NaO6(1+)", "1", "1", "m062x", "6-31+g(2df,p)",
            "-849.236562347", "19.6701", "0.200499", "-1260.493395",
            "-1260.568296", "60.7784", "90.3398" };
    private static final String[] EMPTY_LINE = { "N/A", "N/A", "N/A", "N/A",
            "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A",
            "N/A" };
    private SnapshotCsvDisplay disp;

    /**
     * Create an instance of the display class before each test.
     */
    @Before
    public void setUp() {
        disp = new SnapshotCsvDisplay();
    }

    /**
     * Tests running against a fully-formed test instance.
     * 
     * @throws Exception
     *             When there is a problem.
     */
    @Test
    public void testFullData() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        assertTrue(disp.isFirst());
        disp.write(stringWriter, getTestInst());
        assertFalse(disp.isFirst());
        final CSVReader csvReader = new CSVReader(new StringReader(
                stringWriter.toString()));
        try {
            assertThat(csvReader.readNext(), equalTo(HEAD_LINE));
            assertThat(csvReader.readNext(), equalTo(DATA_LINE));
            assertNull(csvReader.readNext());
        } finally {
            csvReader.close();
        }

    }

    /**
     * Tests running against an empty test instance.
     * 
     * @throws Exception
     *             When there is a problem.
     */
    @Test
    public void testEmptyData() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        assertTrue(disp.isFirst());
        disp.write(stringWriter, new DefaultBaseResult());
        assertFalse(disp.isFirst());
        final CSVReader csvReader = new CSVReader(new StringReader(
                stringWriter.toString()));
        try {
            assertThat(csvReader.readNext(), equalTo(HEAD_LINE));
            assertThat(csvReader.readNext(), equalTo(EMPTY_LINE));
            assertNull(csvReader.readNext());
        } finally {
            csvReader.close();
        }
    }

    /**
     * Tests running against a full instance with an alternate header line.
     * 
     * @throws Exception
     *             When there is a problem.
     */
    @Test
    public void testAltHead() throws Exception {
        assertThat(disp.getMediaType(), equalTo(MediaType.CSV));

        assertNull(disp.getHeaderRow());
        disp.setHeaderRow(ALT_HEAD_LINE);
        assertThat(disp.getHeaderRow(), equalTo(ALT_HEAD_LINE));
        final StringWriter stringWriter = new StringWriter();
        disp.write(stringWriter, getTestInst());
        final CSVReader csvReader = new CSVReader(new StringReader(
                stringWriter.toString()));
        try {
            assertThat(csvReader.readNext(), equalTo(ALT_HEAD_LINE));
            assertThat(csvReader.readNext(), equalTo(DATA_LINE));
            assertNull(csvReader.readNext());
        } finally {
            csvReader.close();
        }
    }

    /**
     * Creates a test instance to display.
     * 
     * @return A test instance.
     */
    private BaseResult getTestInst() {
        final DefaultBaseResult snap = new DefaultBaseResult();
        snap.setSourceName("someFileName.txt");
        snap.setSolvent("water");
        snap.setStoichiometry("C6H12NaO6(1+)");
        snap.setElecEn(-849.236562347);
        snap.setFunctional("m062x");
        snap.setCharge(1);
        snap.setMult(1);
        snap.setFrequencyValues(Arrays.asList(60.7784, 90.3398));
        snap.setZpeCorrection(0.200499);
        snap.setGibbs298(-1260.568296);
        snap.setEnthalpy298(-1260.493395);
        snap.setBasisSet("6-31+g(2df,p)");
        snap.setDipoleMomentTotal(19.6701);
        return snap;
    }
}
