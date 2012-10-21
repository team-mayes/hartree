package org.cmayes.hartree.calc.impl;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;
import static com.cmayes.common.util.FormatUtils.findIdx;
import static com.cmayes.common.util.FormatUtils.toDouble;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.cmayes.common.exception.EnvironmentException;
import com.cmayes.common.exception.InvalidDataException;
import com.cmayes.common.util.ChemUtils;
import com.cmayes.common.util.EnvUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

/**
 * Calculates the puckering for the given Cremer-Pople result using Cartesian
 * coordinates.
 * 
 * @author cmayes
 */
public class CartesianCremerPoplePuckeringCalculation implements Calculation {
    private static final String CP_CODES_CSV_FNAME = "CartCPcodes.csv";
    private static final String NULL_ERR_FMT = "Null value for %s in "
            + CP_CODES_CSV_FNAME;
    private static final String CODE_FIELD = "code";
    private static final String Z_FIELD = "z";
    private static final String Y_FIELD = "y";
    private static final String X_FIELD = "x";
    private static final String CODE_NULL_ERR = String.format(NULL_ERR_FMT,
            CODE_FIELD);
    private static final String X_NULL_ERR = String.format(NULL_ERR_FMT,
            X_FIELD);
    private static final String Y_NULL_ERR = String.format(NULL_ERR_FMT,
            Y_FIELD);
    private static final String Z_NULL_ERR = String.format(NULL_ERR_FMT,
            Z_FIELD);
    /** If the number is within tolerance of this value, it is set to zero. */
    private static final double TO_ZERO_VAL = 360.;
    /** The tolerance value for number matching. */
    private static final double TOLERANCE = 8.;
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<Vector3D, String> cartConfs;

    public static void main(String[] args) {
        new CartesianCremerPoplePuckeringCalculation().writeCartesianTable();
    }

    /**
     * Zero-arg constructor.
     */
    public CartesianCremerPoplePuckeringCalculation() {
        this.cartConfs = createCartesianTable();
    }

    private void writeCartesianTable() {
        FileWriter writer = null;
        try {
            writer = new FileWriter("CartCPCodes.csv");
            final CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(new String[] { X_FIELD, Y_FIELD, Z_FIELD,
                    CODE_FIELD });

            for (Entry<Vector3D, String> cartConf : cartConfs.entrySet()) {
                Vector3D coords = cartConf.getKey();
                csvWriter.writeNext(new String[] {
                        Double.toString(coords.getX()),
                        Double.toString(coords.getY()),
                        Double.toString(coords.getZ()), cartConf.getValue() });
            }
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (final IOException e) {
                    logger.warn("Couldn't close CSV file", e);
                }
            }
        }
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

    // /**
    // * Returns a Cartesian mapping of the table.
    // *
    // * @return A Cartesian mapping of the table.
    // */
    // public Map<Vector3D, String> createCartesianTable() {
    // final Table<Double, Double, String> cpTable = new
    // CremerPoplePuckeringCalculation()
    // .getCpTable();
    // final Map<Vector3D, String> vectorTable = new HashMap<Vector3D,
    // String>();
    // for (Double tableTheta : cpTable.rowKeySet()) {
    // for (Entry<Double, String> tablePhiEntry : cpTable.row(tableTheta)
    // .entrySet()) {
    // final Vector3D phiThetaVector = ChemUtils.phiThetaToVector(
    // tablePhiEntry.getKey(), tableTheta);
    // vectorTable.put(phiThetaVector, tablePhiEntry.getValue());
    // }
    // }
    // return vectorTable;
    // }

    /**
     * Returns a Cartesian mapping of the table.
     * 
     * @return A Cartesian mapping of the table.
     */
    public Map<Vector3D, String> createCartesianTable() {
        final Map<Vector3D, String> vectorTable = Maps.newHashMap();
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
            final int xIdx = findIdx(headerRow, X_FIELD);
            final int yIdx = findIdx(headerRow, Y_FIELD);
            final int zIdx = findIdx(headerRow, Z_FIELD);
            final int codeIdx = findIdx(headerRow, CODE_FIELD);
            String[] line;
            while (null != (line = reader.readNext())) {
                String curCode;
                String curX;
                String curY;
                String curZ;
                try {
                    curCode = asNotNull(line[codeIdx], CODE_NULL_ERR);
                    curX = asNotNull(line[xIdx], X_NULL_ERR);
                    curY = asNotNull(line[yIdx], Y_NULL_ERR);
                    curZ = asNotNull(line[zIdx], Z_NULL_ERR);
                } catch (final IndexOutOfBoundsException e) {
                    throw new InvalidDataException(
                            String.format(
                                    "The report line for %s in %s is too short (%d elements)",
                                    Arrays.toString(line), CP_CODES_CSV_FNAME,
                                    line.length), e);
                }
                final String fmtString = "%s on confirmation " + curCode;
                vectorTable.put(
                        new Vector3D(toDouble(curX,
                                String.format(fmtString, X_FIELD)), toDouble(
                                curY, String.format(fmtString, Y_FIELD)),
                                toDouble(curZ,
                                        String.format(fmtString, Z_FIELD))),
                        curCode);
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

        return vectorTable;

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

        final Vector3D tgtCoords = ChemUtils.phiThetaToVector(
                cpCoords.getPhi(), cpTheta);

        double minAngle = -1;
        Vector3D minVec = null;

        for (Vector3D curConf : cartConfs.keySet()) {
            double dotProduct = tgtCoords.dotProduct(curConf);
            // We allow the dot product to be slightly greater than PI, but
            // the arccos is undefined for values greater than PI, so we just
            // set the product to PI when the calculated value is slightly
            // greater than PI.
            if ((dotProduct > Math.PI) && ((dotProduct - Math.PI) < .001)) {
                dotProduct = Math.PI;
            }

            final double calcAngle = Math.acos(dotProduct);
            if (minVec == null || calcAngle < minAngle) {
                minAngle = calcAngle;
                minVec = curConf;
            }
        }
        return asNotNull(
                cartConfs.get(asNotNull(minVec, "No vector found for "
                        + cpCoords)), "No confirmation maps to " + minVec);
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

}
