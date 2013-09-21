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
    private Double hmArmAngle1;
    private Double acArmAngle1;
    private Double hmArmAngle2;
    private Double acArmAngle2;
    private Double anoAngle1;
    private Double anoAngle2;

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
        this.hmArmAngle1 = snap.getHmArmAngle1();
        this.hmArmAngle2 = snap.getHmArmAngle2();
        this.acArmAngle1 = snap.getAcArmAngle1();
        this.acArmAngle2 = snap.getAcArmAngle2();
        this.anoAngle1 = snap.getAnoAngle1();
        this.anoAngle2 = snap.getAnoAngle2();
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
     * @see org.cmayes.hartree.model.GlucoseRingResult#getHmArmAngle1()
     */
    public Double getHmArmAngle1() {
        return hmArmAngle1;
    }

    /**
     * @param angle1
     *            the hmArmAngle1 to set
     */
    public void setHmArmAngle1(final Double angle1) {
        this.hmArmAngle1 = angle1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.GlucoseRingResult#getHmArmAngle2()
     */
    public Double getHmArmAngle2() {
        return hmArmAngle2;
    }

    /**
     * @param angle2
     *            the hmArmAngle2 to set
     */
    public void setHmArmAngle2(final Double angle2) {
        this.hmArmAngle2 = angle2;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.GlucoseRingResult#getAcArmAngle1()
     */
    public Double getAcArmAngle1() {
        return acArmAngle1;
    }

    /**
     * @param angle1
     *            the acArmAngle1 to set
     */
    public void setAcArmAngle1(final Double angle1) {
        this.acArmAngle1 = angle1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.GlucoseRingResult#getAcArmAngle2()
     */
    public Double getAcArmAngle2() {
        return acArmAngle2;
    }

    /**
     * @param angle2
     *            the acArmAngle2 to set
     */
    public void setAcArmAngle2(final Double angle2) {
        this.acArmAngle2 = angle2;
    }

    /**
     * @return the thirdAngle1
     */
    public Double getAnoAngle1() {
        return anoAngle1;
    }

    /**
     * @param thirdAngle1
     *            the thirdAngle1 to set
     */
    public void setAnoAngle1(final Double thirdAngle1) {
        this.anoAngle1 = thirdAngle1;
    }

    /**
     * @return the thirdAngle2
     */
    public Double getAnoAngle2() {
        return anoAngle2;
    }

    /**
     * @param thirdAngle2
     *            the thirdAngle2 to set
     */
    public void setAnoAngle2(final Double thirdAngle2) {
        this.anoAngle2 = thirdAngle2;
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof CpCalculationSnapshot)) {
            return false;
        }
        final CpCalculationSnapshot rhs = (CpCalculationSnapshot) object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.anoAngle2, rhs.anoAngle2)
                .append(this.anoAngle1, rhs.anoAngle1)
                .append(this.carbonDistances, rhs.carbonDistances)
                .append(this.ionDistances, rhs.ionDistances)
                .append(this.oxygenDistances, rhs.oxygenDistances)
                .append(this.cpCoords, rhs.cpCoords)
                .append(this.oxygenAtoms, rhs.oxygenAtoms)
                .append(this.hmArmAngle1, rhs.hmArmAngle1)
                .append(this.hmArmAngle2, rhs.hmArmAngle2)
                .append(this.acArmAngle2, rhs.acArmAngle2)
                .append(this.acArmAngle1, rhs.acArmAngle1)
                .append(this.glucoseRing, rhs.glucoseRing).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-334262149, 954656579)
                .appendSuper(super.hashCode()).append(this.anoAngle2)
                .append(this.anoAngle1).append(this.carbonDistances)
                .append(this.ionDistances).append(this.oxygenDistances)
                .append(this.cpCoords).append(this.oxygenAtoms)
                .append(this.hmArmAngle1).append(this.hmArmAngle2)
                .append(this.acArmAngle2).append(this.acArmAngle1)
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
                .append("anoAngle1", this.anoAngle1)
                .append("anoAngle2", this.anoAngle2)
                .append("frequencyValues", this.getFrequencyValues())
                .append("glucoseRing", this.glucoseRing)
                .append("mult", this.getMult())
                .append("ionDistances", this.ionDistances)
                .append("symmetricTop", this.isSymmetricTop())
                .append("sourceName", this.getSourceName())
                .append("hmArmAngle1", this.hmArmAngle1)
                .append("solvent", this.getSolvent())
                .append("terminationDates", this.getTerminationDates())
                .append("enthalpy298", this.getEnthalpy298())
                .append("elecEn", this.getElecEn())
                .append("charge", this.getCharge())
                .append("atoms", this.getAtoms())
                .append("cpuTimes", this.getCpuTimes())
                .append("acArmAngle1", this.acArmAngle1)
                .append("atomCount", this.getAtomCount())
                .append("acArmAngle2", this.acArmAngle2)
                .append("hmArmAngle2", this.hmArmAngle2)
                .append("cpCoords", this.cpCoords)
                .append("rotPart", this.getRotPart())
                .append("oxygenDistances", this.oxygenDistances)
                .append("transPart", this.getTransPart())
                .append("gibbs298", this.getGibbs298())
                .append("zpeCorrection", this.getZpeCorrection())
                .append("oxygenAtoms", this.oxygenAtoms)
                .append("stoichiometry", this.getStoichiometry())
                .append("basisSet", this.getBasisSet())
                .append("carbonDistances", this.carbonDistances)
                .append("dipoleMomentTotal", this.getDipoleMomentTotal())
                .append("atomMap", this.getAtomMap()).toString();
    }

}
