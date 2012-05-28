package org.cmayes.hartree.disp.csv;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.cmayes.hartree.model.CalculationSnapshot;
import org.cmayes.hartree.model.def.DefaultCalculationSnapshot;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.cmayes.common.MediaType;

public class TestCalculationSnapshotCsvDisplay {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String[] HEAD_LINE = { "File Name", "Solvent type",
            "Stoichiometry", "Charge", "Mult", "Functional", "Basis Set",
            "Energy (A.U.)", "dipole", "ZPE (kcal/mol)", "G298 (Hartrees)",
            "Freq 1", "Freq 2" };
    private static final String[] ALT_HEAD_LINE = { "Alt File Name",
            "Solvent type", "Stoichiometry", "Charge", "Alt Mult",
            "Functional", "Basis Set", "Energy (A.U.)", "dipole",
            "ZPE (kcal/mol)", "G298 (Hartrees)", "Freq 1", "Freq 2" };
    private static final String[] DATA_LINE = { "someFileName.txt", "water",
            "C6H12NaO6(1+)", "1", "1", "m062x", "6-31+g(2df,p)",
            "-849.236562347", "19.6701", "0.200499", "-1260.568296", "60.7784",
            "90.3398" };
    private static final String[] EMPTY_LINE = { "N/A", "N/A", "N/A", "N/A",
            "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A" };
    private CalculationSnapshotCsvDisplay disp;

    @Before
    public void setUp() {
        disp = new CalculationSnapshotCsvDisplay();
    }

    @Test
    public void testFullData() throws Exception {
        StringWriter stringWriter = new StringWriter();
        assertTrue(disp.isFirst());
        disp.write(stringWriter, getTestInst());
        assertFalse(disp.isFirst());
        CSVReader csvReader = new CSVReader(new StringReader(
                stringWriter.toString()));
        assertThat(csvReader.readNext(), equalTo(HEAD_LINE));
        assertThat(csvReader.readNext(), equalTo(DATA_LINE));
        assertNull(csvReader.readNext());
    }

    @Test
    public void testEmptyData() throws Exception {
        StringWriter stringWriter = new StringWriter();
        assertTrue(disp.isFirst());
        disp.write(stringWriter, new DefaultCalculationSnapshot());
        assertFalse(disp.isFirst());
        CSVReader csvReader = new CSVReader(new StringReader(
                stringWriter.toString()));
        assertThat(csvReader.readNext(), equalTo(HEAD_LINE));
        assertThat(csvReader.readNext(), equalTo(EMPTY_LINE));
        assertNull(csvReader.readNext());
    }

    @Test
    public void testAltHead() throws Exception {
        assertThat(disp.getMediaType(), equalTo(MediaType.CSV));

        assertThat(disp.getHeaderRow(), equalTo(HEAD_LINE));
        disp.setHeaderRow(ALT_HEAD_LINE);
        assertThat(disp.getHeaderRow(), equalTo(ALT_HEAD_LINE));
        StringWriter stringWriter = new StringWriter();
        disp.write(stringWriter, getTestInst());
        CSVReader csvReader = new CSVReader(new StringReader(
                stringWriter.toString()));
        assertThat(csvReader.readNext(), equalTo(ALT_HEAD_LINE));
        assertThat(csvReader.readNext(), equalTo(DATA_LINE));
        assertNull(csvReader.readNext());
    }

    private CalculationSnapshot getTestInst() {
        DefaultCalculationSnapshot snap = new DefaultCalculationSnapshot();
        snap.setFileName("someFileName.txt");
        snap.setSolvent("water");
        snap.setStoichiometry("C6H12NaO6(1+)");
        snap.setElecEn(-849.236562347);
        snap.setFunctional("m062x");
        snap.setCharge(1);
        snap.setMult(1);
        snap.setFrequencyValues(Arrays.asList(60.7784, 90.3398));
        snap.setZpeCorrection(0.200499);
        snap.setGibbs298(-1260.568296);
        snap.setBasisSet("6-31+g(2df,p)");
        snap.setDipoleMomentTotal(19.6701);
        return snap;
    }
}
