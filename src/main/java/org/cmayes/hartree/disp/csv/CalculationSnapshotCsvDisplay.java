package org.cmayes.hartree.disp.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.model.CalculationSnapshot;

import au.com.bytecode.opencsv.CSVWriter;

import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;

/**
 * Formats {@link CalculationSnapshot} data as lines in a CSV file.
 * 
 * @author cmayes
 */
public class CalculationSnapshotCsvDisplay implements
        Display<CalculationSnapshot> {
    private static final String MISSING = "N/A";
    private String[] headerRow = new String[] { "File Name", "Solvent type",
            "Stoichiometry", "Charge", "Mult", "Functional", "Basis Set",
            "Energy (A.U.)", "dipole", "ZPE (kcal/mol)", "G298 (Hartrees)",
            "Freq 1", "Freq 2" };
    private boolean first = true;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(final Writer writer, final CalculationSnapshot valToDisp) {
        final CSVWriter csvWriter = new CSVWriter(writer);
        try {
            if (first && headerRow != null) {
                csvWriter.writeNext(headerRow);
                first = false;
            }
            final String charge = valOrMissing(valToDisp.getCharge());
            final String mult = valOrMissing(valToDisp.getMult());
            final String energy = valOrMissing(valToDisp.getElecEn());
            final List<Double> freq = valToDisp.getFrequencyValues();
            String firstFreq;
            String secFreq;
            if (freq == null || freq.isEmpty()) {
                firstFreq = MISSING;
            } else {
                firstFreq = valOrMissing(freq.get(0));
            }
            if (freq == null || freq.size() < 2) {
                secFreq = MISSING;
            } else {
                secFreq = valOrMissing(freq.get(1));
            }
            final String solv = valOrMissing(valToDisp.getSolvent());
            final String zpe = valOrMissing(valToDisp.getZpeCorrection());
            final String stoi = valOrMissing(valToDisp.getStoichiometry());
            final String dip = valOrMissing(valToDisp.getDipoleMomentTotal());
            final String fname = valOrMissing(valToDisp.getFileName());
            final String func = valOrMissing(valToDisp.getFunctional());
            final String basisSet = valOrMissing(valToDisp.getBasisSet());
            final String g298 = valOrMissing(valToDisp.getGibbs298());
            csvWriter
                    .writeNext(new String[] { fname, solv, stoi, charge, mult,
                            func, basisSet, energy, dip, zpe, g298, firstFreq,
                            secFreq });
        } finally {
            try {
                csvWriter.flush();
            } catch (final IOException e) {
                throw new EnvironmentException(
                        "Problems writing CSV to writer", e);
            }
        }
    }

    /**
     * Returns the string value of the object or the missing string value of the
     * value is null.
     * 
     * @param val
     *            The value.
     * @return The value's string value or the missing value of the value is
     *         null.
     */
    private String valOrMissing(final Object val) {
        if (val == null || val.toString().isEmpty()) {
            return MISSING;
        }
        return val.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#getMediaType()
     */
    @Override
    public MediaType getMediaType() {
        return MediaType.CSV;
    }

    /**
     * Returns the row that will be added on the first write to this display.
     * 
     * @return the row that will be added on the first write to this display.
     */
    public String[] getHeaderRow() {
        return headerRow;
    }

    /**
     * Sets the row that will be added on the first write to this display. You
     * may set this to null if you do not wish to have a header row.
     * 
     * @param row
     *            The row to add on the first write or null if no header is
     *            desired.
     */
    public void setHeaderRow(final String[] row) {
        this.headerRow = row;
    }

    /**
     * Returns whether this writer is waiting for its first write.
     * 
     * @return Whether the first row has been written.
     */
    boolean isFirst() {
        return first;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#finish(Writer)
     */
    @Override
    public void finish(final Writer writer) {
        this.first = true;
    }
}
