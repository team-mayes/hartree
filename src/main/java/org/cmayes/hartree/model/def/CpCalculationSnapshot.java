package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CremerPopleResult;
import org.cmayes.hartree.model.GlucoseRingResult;

import com.cmayes.common.model.Atom;
import com.cmayes.common.util.ChemUtils;

/**
 * Extends a calculation snapshot to have a glucose ring and a Cremer-Pople
 * result.
 * 
 * @author cmayes
 */
public class CpCalculationSnapshot extends DefaultBaseResult implements
        CremerPopleResult, GlucoseRingResult {
    private static final int LAST_CARBON_IDX = 5;
    private static final int FULL_RING_SIZE = 6;
    private List<Atom> glucoseRing = new ArrayList<Atom>();
    private List<Double> carbonDistances = new ArrayList<Double>();
    private List<Double> oxygenDistances = new ArrayList<Double>();
    private CremerPopleCoordinates cpCoords;

    /**
     * Zero-arg constructor.
     */
    public CpCalculationSnapshot() {
    }

    /**
     * Copy constructor.
     * 
     * @param snap
     *            The snapshot to copy.
     */
    public CpCalculationSnapshot(final CpCalculationSnapshot snap) {
        super(snap);
        this.glucoseRing = snap.getGlucoseRing();
        this.cpCoords = snap.getCpCoords();
    }

    /**
     * Creates a snapshot filled by the given raw input.
     * 
     * @param baseResult
     *            The instance containing the data to copy.
     */
    public CpCalculationSnapshot(final BaseResult baseResult) {
        super(baseResult);
    }

    /**
     * @return the glucoseRing
     */
    public List<Atom> getGlucoseRing() {
        return glucoseRing;
    }

    /**
     * @param ring
     *            the glucoseRing to set
     */
    public void setGlucoseRing(final List<Atom> ring) {
        this.glucoseRing = ring;
    }

    /**
     * @return the cpCoords
     */
    public CremerPopleCoordinates getCpCoords() {
        return cpCoords;
    }

    /**
     * @param coords
     *            the cpCoords to set
     */
    public void setCpCoords(final CremerPopleCoordinates coords) {
        this.cpCoords = coords;
    }

    /**
     * @return The distance between the oxygen and the first carbon.
     */
    @Override
    public double getFirstCarbonDistance() {
        if (glucoseRing == null) {
            throw new IllegalStateException("Glucose ring is null");
        } else if (glucoseRing.size() < 2) {
            throw new IllegalStateException(String.format(
                    "Glucose ring has %d atoms, which is less than two",
                    glucoseRing.size()));
        }
        return ChemUtils.vectorForAtom(glucoseRing.get(0)).distance(
                ChemUtils.vectorForAtom(glucoseRing.get(1)));
    }

    /**
     * @return The distance between the oxygen and the last carbon.
     */
    @Override
    public double getLastCarbonDistance() {
        if (glucoseRing == null) {
            throw new IllegalStateException("Glucose ring is null");
        } else if (glucoseRing.size() < FULL_RING_SIZE) {
            throw new IllegalStateException(String.format(
                    "Glucose ring has %d atoms, which is less than six",
                    glucoseRing.size()));
        }
        return ChemUtils.vectorForAtom(glucoseRing.get(0)).distance(
                ChemUtils.vectorForAtom(glucoseRing.get(LAST_CARBON_IDX)));
    }

    /**
     * @return the carbonDistances
     */
    public List<Double> getCarbonDistances() {
        return carbonDistances;
    }

    /**
     * @param carbonDistances the carbonDistances to set
     */
    public void setCarbonDistances(List<Double> carbonDistances) {
        this.carbonDistances = carbonDistances;
    }

    /**
     * @return the oxygenDistances
     */
    public List<Double> getOxygenDistances() {
        return oxygenDistances;
    }

    /**
     * @param oxygenDistances the oxygenDistances to set
     */
    public void setOxygenDistances(List<Double> oxygenDistances) {
        this.oxygenDistances = oxygenDistances;
    }
}
