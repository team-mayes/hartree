package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cmayes.hartree.model.BaseResult;
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
    private List<Atom> oxygenAtoms = new ArrayList<Atom>();
    private List<Double> carbonDistances = new ArrayList<Double>();
    private List<Double> oxygenDistances = new ArrayList<Double>();
    private List<Double> ionDistances = new ArrayList<Double>();
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
        this.ionDistances = snap.getIonDistances();
        this.oxygenAtoms = snap.getOxygenAtoms();
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
     * @return the oxygenAtoms
     */
    public List<Atom> getOxygenAtoms() {
        return oxygenAtoms;
    }

    /**
     * @param oxyAtoms
     *            the oxygen atoms to set
     */
    public void setOxygenAtoms(final List<Atom> oxyAtoms) {
        this.oxygenAtoms = oxyAtoms;
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
     * Returns the bond lengths between oxygen atoms and carbon members of the
     * glucose ring. Note that the fifth item is O6; O5 is omitted as it is a
     * member of the ring itself.
     * 
     * @return the oxygenDistances
     */
    public List<Double> getOxygenDistances() {
        return oxygenDistances;
    }

    /**
     * @param oxygenDist
     *            the oxygenDistances to set
     * @see #getOxygenDistances()
     */
    public void setOxygenDistances(final List<Double> oxygenDist) {
        this.oxygenDistances = oxygenDist;
    }

    /**
     * Returns the distances between an ion atom and carbon members of the
     * glucose ring.
     * 
     * @return the ionDistances
     */
    public List<Double> getIonDistances() {
        return ionDistances;
    }

    /**
     * @param ionDist
     *            the ionDistances to set
     * @see #getIonDistances()
     */
    public void setIonDistances(final List<Double> ionDist) {
        this.ionDistances = ionDist;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof CpCalculationSnapshot)) {
            return false;
        }
        final CpCalculationSnapshot rhs = (CpCalculationSnapshot) object;
        return new EqualsBuilder().appendSuper(super.equals(this))
                .append(this.carbonDistances, rhs.carbonDistances)
                .append(this.oxygenDistances, rhs.oxygenDistances)
                .append(this.cpCoords, rhs.cpCoords)
                .append(this.glucoseRing, rhs.glucoseRing).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1831333135, 1254953807)
                .appendSuper(super.hashCode()).append(this.carbonDistances)
                .append(this.oxygenDistances).append(this.cpCoords)
                .append(this.glucoseRing).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("functional", this.getFunctional())
                .append("frequencyValues", this.getFrequencyValues())
                .append("glucoseRing", this.glucoseRing)
                .append("mult", this.getMult())
                .append("symmetricTop", this.isSymmetricTop())
                .append("sourceName", this.getSourceName())
                .append("solvent", this.getSolvent())
                .append("terminationDates", this.getTerminationDates())
                .append("enthalpy298", this.getEnthalpy298())
                .append("elecEn", this.getElecEn())
                .append("atoms", this.getAtoms())
                .append("charge", this.getCharge())
                .append("cpuTimes", this.getCpuTimes())
                .append("atomCount", this.getAtomCount())
                .append("rotPart", this.getRotPart())
                .append("cpCoords", this.cpCoords)
                .append("oxygenDistances", this.oxygenDistances)
                .append("transPart", this.getTransPart())
                .append("gibbs298", this.getGibbs298())
                .append("zpeCorrection", this.getZpeCorrection())
                .append("stoichiometry", this.getStoichiometry())
                .append("basisSet", this.getBasisSet())
                .append("carbonDistances", this.carbonDistances)
                .append("dipoleMomentTotal", this.getDipoleMomentTotal())
                .append("atomMap", this.getAtomMap()).toString();
    }
}
