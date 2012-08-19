package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.CremerPopleResult;
import org.cmayes.hartree.model.GlucoseRingResult;

import com.cmayes.common.model.Atom;

/**
 * Extends a calculation snapshot to have a glucose ring and a Cremer-Pople
 * result.
 * 
 * @author cmayes
 */
public class CpCalculationSnapshot extends DefaultBaseResult implements
        CremerPopleResult, GlucoseRingResult {
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
        this.carbonDistances = snap.getCarbonDistances();
        this.oxygenDistances = snap.getOxygenDistances();
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
     * @return the carbonDistances
     */
    public List<Double> getCarbonDistances() {
        return carbonDistances;
    }

    /**
     * @param carbonDist
     *            the carbonDistances to set
     */
    public void setCarbonDistances(final List<Double> carbonDist) {
        this.carbonDistances = carbonDist;
    }

    /**
     * @return the oxygenDistances
     */
    public List<Double> getOxygenDistances() {
        return oxygenDistances;
    }

    /**
     * @param oxygenDist
     *            the oxygenDistances to set
     */
    public void setOxygenDistances(final List<Double> oxygenDist) {
        this.oxygenDistances = oxygenDist;
    }
}
