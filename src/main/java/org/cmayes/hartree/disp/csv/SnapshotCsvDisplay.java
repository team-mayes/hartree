package org.cmayes.hartree.disp.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.cmayes.hartree.disp.Display;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CremerPopleResult;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;

import au.com.bytecode.opencsv.CSVWriter;

import com.cmayes.common.MediaType;
import com.cmayes.common.exception.EnvironmentException;

/**
 * Formats {@link BaseResult} data as lines in a CSV file.
 * 
 * @author cmayes
 */
public class SnapshotCsvDisplay implements Display<BaseResult> {
    private static final String MISSING = "N/A";
    private String[] headerRow;
    private final String[] defaultHeaderRow = new String[] { "File Name",
            "Solvent type", "Stoichiometry", "Charge", "Mult", "Functional",
            "Basis Set", "Energy (A.U.)", "dipole", "ZPE (kcal/mol)",
            "G298 (Hartrees)", "Freq 1", "Freq 2" };
    private final String[] cpHeaderRow = new String[] { "File Name",
            "Solvent type", "Stoichiometry", "Charge", "Mult", "Functional",
            "Basis Set", "Energy (A.U.)", "dipole", "ZPE (kcal/mol)",
            "G298 (Hartrees)", "Freq 1", "Freq 2", "phi", "theta", "Q",
            "Pucker" };
    private boolean first = true;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.disp.Display#write(java.io.Writer,
     *      java.lang.Object)
     */
    @Override
    public void write(final Writer writer, final BaseResult valToDisp) {
        final CSVWriter csvWriter = new CSVWriter(writer);
        try {
            if (first) {
                csvWriter.writeNext(getHeaderRow(valToDisp));
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
            if (valToDisp instanceof CremerPopleResult) {
                final CremerPopleCoordinates cpCoords = ((CremerPopleResult) valToDisp)
                        .getCpCoords();
                if (cpCoords == null) {
                    csvWriter.writeNext(new String[] { fname, solv, stoi,
                            charge, mult, func, basisSet, energy, dip, zpe,
                            g298, firstFreq, secFreq, MISSING, MISSING,
                            MISSING, MISSING });

                } else {
                    final String phi = valOrMissing(cpCoords.getPhi());
                    final String theta = valOrMissing(cpCoords.getTheta());
                    final String q = valOrMissing(cpCoords.getQ());
                    final String pucker = valOrMissing(cpCoords.getPucker());
                    csvWriter.writeNext(new String[] { fname, solv, stoi,
                            charge, mult, func, basisSet, energy, dip, zpe,
                            g298, firstFreq, secFreq, phi, theta, q, pucker });
                }
            } else {
                csvWriter.writeNext(new String[] { fname, solv, stoi, charge,
                        mult, func, basisSet, energy, dip, zpe, g298,
                        firstFreq, secFreq });
            }
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
     * Returns the row that will be added on the first write to this display if
     * set. If this value is null, a header row appropriate for the write
     * candidate will be used.
     * 
     * @return the row that will be added on the first write to this display if
     *         set.
     */
    public String[] getHeaderRow() {
        return headerRow;
    }

    /**
     * Returns the row that will be added on the first write to this display.
     * Will return the header row appropriate to the given instance.
     * 
     * @param res
     *            The result to evaluate.
     * 
     * @return the row that will be added on the first write to this display.
     */
    public String[] getHeaderRow(final BaseResult res) {
        if (headerRow != null) {
            return headerRow;
        } else if (res instanceof CremerPopleResult) {
            return cpHeaderRow;
        }
        return defaultHeaderRow;
    }

    /**
     * Sets the row that will be added on the first write to this display.
     * 
     * @param row
     *            The row to add on the first write.
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
