package org.cmayes.hartree.calc.impl;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.exception.InvalidDataException;
import com.cmayes.common.exception.NotFoundException;
import com.cmayes.common.util.EnvUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Calculates the puckering for the given Cremer-Pople result.
 * 
 * @author cmayes
 */
public class CremerPoplePuckeringCalculation implements Calculation {
    private static final String PHI_GLOB = "*";
    private static final String CP_CODES_CSV_FNAME = "CPcodes.csv";
    /** Indicates that phi is globbed s.t. any value matches. */
    private static final double PHI_GLOB_VAL = -1.;
    /** The tolerance value for number matching. */
    private static final double TOLERANCE = 8.;
    /** If the number is within tolerance of this value, it is set to zero. */
    private static final double TO_ZERO_VAL = 360.;
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /** Table of theta, phi, and pucker code. */
    private final Table<Double, Double, String> cpTable;

    /**
     * Zero-arg constructor.
     */
    public CremerPoplePuckeringCalculation() {
        cpTable = getCpTable();
    }

    /**
     * Finds the Cremer-Pople puckering code for the CP coordinates in the given
     * object. {@inheritDoc}
     * 
     * @see org.cmayes.hartree.calc.Calculation#calculate(java.lang.Object)
     */
    @Override
    public Object calculate(final Object rawInput) {
        if (rawInput instanceof CpCalculationSnapshot) {
            final CpCalculationSnapshot cpSnap = new CpCalculationSnapshot(
                    (CpCalculationSnapshot) rawInput);
            final CremerPopleCoordinates cpCoords = cpSnap.getCpCoords();
            if (cpCoords == null) {
                logger.warn(String.format(
                        "No CP coordinates for CP Pucker calc: '%s'", rawInput));
                return cpSnap;
            }
            cpCoords.setPucker(findPuckerCode(cpCoords));
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * Reads in the Cremer-Pople puckering characterization table from a file in
     * the project.
     * 
     * @return A table describing the parameters for different Cremer-Pople
     *         puckering states.
     */
    private Table<Double, Double, String> getCpTable() {
        final Table<Double, Double, String> table = HashBasedTable.create();
        CSVReader reader = null;
        try {
            reader = new CSVReader(asNotNull(
                    EnvUtils.getResourceReader(CP_CODES_CSV_FNAME),
                    String.format("Couldn't read CP codes file '%s'",
                            CP_CODES_CSV_FNAME)));
            final String[] headerRow = reader.readNext();
            if (headerRow == null) {
                throw new InvalidDataException("No header row in file "
                        + CP_CODES_CSV_FNAME);
            }
            final int codeIdx = findIdx(headerRow, "code");
            final int phiIdx = findIdx(headerRow, "phi");
            final int thetaIdx = findIdx(headerRow, "theta");
            String[] line;
            while (null != (line = reader.readNext())) {
                String curCode;
                String curPhi;
                String curTheta;
                try {
                    curCode = asNotNull(line[codeIdx],
                            "Null value for code in " + CP_CODES_CSV_FNAME);
                    curPhi = asNotNull(line[phiIdx], "Null value for phi in "
                            + CP_CODES_CSV_FNAME);
                    curTheta = asNotNull(line[thetaIdx],
                            "Null value for theta in " + CP_CODES_CSV_FNAME);
                } catch (final IndexOutOfBoundsException e) {
                    throw new InvalidDataException(
                            String.format(
                                    "The report line for %s in %s is too short (%d elements)",
                                    Arrays.toString(line), CP_CODES_CSV_FNAME,
                                    line.length), e);
                }
                addRow(table, curCode, curPhi, curTheta);
            }
        } catch (final IOException e) {
            throw new EnvironmentException("Problems reading "
                    + CP_CODES_CSV_FNAME, e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (final IOException e) {
                throw new EnvironmentException("Problems reading "
                        + CP_CODES_CSV_FNAME, e);
            }
        }

        return table;
    }

    /**
     * Adds a row to the table for the given theta, phi, and code.
     * 
     * @param table
     *            The table to add to.
     * @param curCode
     *            The code to add.
     * @param curPhi
     *            The phi value to add. Note that if this value is PHI_GLOB,
     *            then PHI_GLOB_VALUE is used.
     * @param curTheta
     *            The theta value to add.
     * @throws InvalidDataException
     *             If phi or theta do not parse to a double.
     */
    private void addRow(final Table<Double, Double, String> table,
            final String curCode, final String curPhi, final String curTheta) {
        double phi;
        if (curPhi.equals(PHI_GLOB)) {
            phi = PHI_GLOB_VAL;
        } else {
            try {
                phi = Double.valueOf(curPhi);
            } catch (final NumberFormatException e) {
                throw new InvalidDataException(String.format(
                        "Phi value '%s' does not parse to a double", curPhi), e);
            }
        }
        try {
            final double theta = Double.valueOf(curTheta);
            table.put(theta, phi, curCode);
        } catch (final NumberFormatException e) {
            throw new InvalidDataException(String.format(
                    "Theta value '%s' does not parse to a double", curTheta), e);
        }
    }

    /**
     * Returns the pucker code for the coordinates, or null if nothing matches.
     * 
     * @param cpCoords
     *            The coordinates to match.
     * @return The pucker code or null if nothing matches.
     */
    private String findPuckerCode(final CremerPopleCoordinates cpCoords) {
        double cpTheta = cpCoords.getTheta();
        if (diffMatches(TO_ZERO_VAL, cpTheta)) {
            cpTheta = 0;
        }
        for (Double tableTheta : cpTable.rowKeySet()) {
            if (diffMatches(tableTheta, cpTheta)) {
                for (Entry<Double, String> tablePhiEntry : cpTable.row(
                        tableTheta).entrySet()) {
                    if (tablePhiEntry.getKey().equals(PHI_GLOB_VAL)
                            || diffMatches(tablePhiEntry.getKey(),
                                    cpCoords.getPhi())) {
                        return tablePhiEntry.getValue();
                    }
                }
            }
        }
        logger.warn(String.format("Couldn't find CP code for coords '%s'",
                cpCoords));
        return null;
    }

    /**
     * Sees whether the difference between the two numbers is within the
     * matching tolerance.
     * 
     * @param val1
     *            The first number to check.
     * @param val2
     *            The second number to check.
     * @return Whether the two numbers are close enough to match.
     */
    private boolean diffMatches(final double val1, final double val2) {
        final double abs = Math.abs(val1 - val2);
        return abs <= TOLERANCE;
    }

    /**
     * Finds the header with the given column name.
     * 
     * @param headerRow
     *            The header row to search.
     * @param colName
     *            The column name to search for.
     * @return The index of the given column name.
     * @throws NotFoundException
     *             If the column name is not found in the header.
     */
    private int findIdx(final String[] headerRow, final String colName) {
        for (int i = 0; i < headerRow.length; i++) {
            if (headerRow[i].equalsIgnoreCase(colName)) {
                return i;
            }
        }
        throw new NotFoundException("No header entry for " + colName);
    }
}
