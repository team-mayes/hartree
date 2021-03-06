package org.cmayes.hartree.model.def;

import static com.cmayes.common.exception.ExceptionUtils.asNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cmayes.hartree.model.BaseResult;
import org.joda.time.Duration;

import com.cmayes.common.model.Atom;

/**
 * Data common to multiple result types.
 * 
 * @author cmayes
 */
public class DefaultBaseResult implements BaseResult {
    private Map<Integer, Atom> atomMap = new TreeMap<Integer, Atom>();
    private List<Date> terminationDates = new ArrayList<Date>();
    private List<Duration> cpuTimes = new ArrayList<Duration>();
    private Double transPart;
    private Double rotPart;
    private Integer mult;
    private Integer charge;
    private List<Double> frequencyValues = new ArrayList<Double>();
    private boolean isSymmetric = true;
    private String sourceName;
    private String functional;
    private String basisSet;
    private String solvent;
    private String stoichiometry;
    private Double zpeCorrection;
    private Double dipoleMomentTotal;
    private Double gibbs298;
    private Double enthalpy298;
    private Double elecEn;
    private Integer atomCount;
    private Double bsse;

    /**
     * Zero-arg constructor.
     */
    public DefaultBaseResult() {

    }

    /**
     * Creates the base result with the given source identifier.
     * 
     * @param srcName
     *            Identifier for the data's source.
     */
    public DefaultBaseResult(final String srcName) {
        sourceName = srcName;
    }

    /**
     * Copy constructor for a base result.
     * 
     * TODO: Deeper copy
     * 
     * @param baseResult
     *            The base result to copy.
     */
    public DefaultBaseResult(final BaseResult baseResult) {
        this.atomCount = baseResult.getAtomCount();
        this.atomMap = new TreeMap<Integer, Atom>(baseResult.getAtomMap());
        this.basisSet = baseResult.getBasisSet();
        this.charge = baseResult.getCharge();
        this.cpuTimes = baseResult.getCpuTimes();
        this.dipoleMomentTotal = baseResult.getDipoleMomentTotal();
        this.elecEn = baseResult.getElecEn();
        this.sourceName = baseResult.getSourceName();
        this.frequencyValues = baseResult.getFrequencyValues();
        this.functional = baseResult.getFunctional();
        this.gibbs298 = baseResult.getGibbs298();
        this.enthalpy298 = baseResult.getEnthalpy298();
        this.isSymmetric = baseResult.isSymmetricTop();
        this.mult = baseResult.getMult();
        this.rotPart = baseResult.getRotPart();
        this.solvent = baseResult.getSolvent();
        this.stoichiometry = baseResult.getStoichiometry();
        this.terminationDates = baseResult.getTerminationDates();
        this.transPart = baseResult.getTransPart();
        this.zpeCorrection = baseResult.getZpeCorrection();
    }

    /**
     * Returns the map of atoms to their IDs.
     * 
     * @return The map of atoms to their IDs.
     */
    public Map<Integer, Atom> getAtomMap() {
        return atomMap;
    }

    /**
     * Sets the atom map.
     * 
     * @param atoms
     *            The atom map to set.
     */
    public void setAtomMap(final Map<Integer, Atom> atoms) {
        this.atomMap = new TreeMap<Integer, Atom>(atoms);
    }

    /**
     * Returns a copy of the atom map's values. Note that modifications to the
     * returned list are not applied to the map's values.
     * 
     * @return A list of atoms in this result.
     */
    public List<Atom> getAtoms() {
        return new ArrayList<Atom>(atomMap.values());
    }

    /**
     * Adds the atom to the atom map by using its ID as the map's key.
     * 
     * @param addMe
     *            The atom to add.
     * @throws IllegalArgumentException
     *             If addMe is null.
     */
    public void addAtom(final Atom addMe) {
        this.atomMap.put(asNotNull(addMe, "Atom is null").getId(), addMe);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#getSolvent()
     */
    public String getSolvent() {
        return solvent;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#setSolvent(java.lang.String)
     */
    public void setSolvent(final String solv) {
        this.solvent = solv;
    }

    /**
     * Returns the file name for this calculation (if applicable).
     * 
     * @return The file name for this calculation.
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Sets the file name for this calculation.
     * 
     * @param fName
     *            The file name for this calculation.
     */
    public void setSourceName(final String fName) {
        this.sourceName = fName;
    }

    /**
     * Looks up the atom with the given ID by pulling the atom in the atoms
     * field by the ID - 1.
     * 
     * @param id
     *            The atom to pull.
     * @return The atom at the zero-based index equivalent of the ID.
     * @throws IllegalArgumentException
     *             If the atom does not exist.
     */
    public Atom getAtomById(final int id) {
        final Atom atom = atomMap.get(id);
        if (atom == null) {
            throw new IllegalArgumentException("No atom with ID " + id);
        }
        return atom;
    }

    public Integer getMult() {
        return mult;
    }

    public void setMult(final Integer multVal) {
        this.mult = multVal;
    }

    public Double getRotPart() {
        return rotPart;
    }

    public void setRotPart(final Double rotationalPart) {
        this.rotPart = rotationalPart;
    }

    public Double getTransPart() {
        return transPart;
    }

    public void setTransPart(final Double translationalPart) {
        this.transPart = translationalPart;
    }

    public List<Double> getFrequencyValues() {
        return frequencyValues;
    }

    public void setFrequencyValues(final List<Double> freqValues) {
        this.frequencyValues = freqValues;
    }

    public List<Date> getTerminationDates() {
        return terminationDates;
    }

    public void setTerminationDates(final List<Date> termDates) {
        this.terminationDates = termDates;
    }

    public List<Duration> getCpuTimes() {
        return cpuTimes;
    }

    public void setCpuTimes(final List<Duration> durations) {
        this.cpuTimes = durations;
    }

    /**
     * Return whether the molecule's top is symmetric.
     * 
     * @return the isSymmetricTop
     */
    public boolean isSymmetricTop() {
        return isSymmetric;
    }

    /**
     * @param isSymTop
     *            the isSymmetricTop to set
     */
    public void setSymmetricTop(final boolean isSymTop) {
        this.isSymmetric = isSymTop;
    }

    /**
     * @return the functional
     */
    public String getFunctional() {
        return functional;
    }

    /**
     * @param func
     *            the functional to set
     */
    public void setFunctional(final String func) {
        this.functional = func;
    }

    /**
     * @return the basisSet
     */
    public String getBasisSet() {
        return basisSet;
    }

    /**
     * @param bSet
     *            the basisSet to set
     */
    public void setBasisSet(final String bSet) {
        this.basisSet = bSet;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#getZpeCorrection()
     */
    public Double getZpeCorrection() {
        return zpeCorrection;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#setZpeCorrection(java.lang.Double)
     */
    public void setZpeCorrection(final Double zpeCorr) {
        this.zpeCorrection = zpeCorr;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#getCharge()
     */
    public Integer getCharge() {
        return charge;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#setCharge(java.lang.Integer)
     */
    public void setCharge(final Integer chg) {
        this.charge = chg;
    }

    /**
     * Returns the stoichiometry.
     * 
     * @return The stoichiometry.
     */
    public String getStoichiometry() {
        return stoichiometry;
    }

    /**
     * Sets the stoichiometry.
     * 
     * @param stoi
     *            The value to set.
     */
    public void setStoichiometry(final String stoi) {
        this.stoichiometry = stoi;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#getDipoleMomentTotal()
     */
    public Double getDipoleMomentTotal() {
        return dipoleMomentTotal;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#setDipoleMomentTotal(java.lang.Double)
     */
    public void setDipoleMomentTotal(final Double dmTotal) {
        this.dipoleMomentTotal = dmTotal;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#getGibbs298()
     */
    @Override
    public Double getGibbs298() {
        return gibbs298;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.BaseResult#setGibbs298(java.lang.Double)
     */
    @Override
    public void setGibbs298(final Double g298) {
        this.gibbs298 = g298;
    }

    public Double getElecEn() {
        return elecEn;
    }

    public void setElecEn(final Double energies) {
        this.elecEn = energies;
    }

    public Integer getAtomCount() {
        return atomCount;
    }

        public void setAtomCount(final Integer count) {
        this.atomCount = count;
    }

    /**
     * @return the enthalpy298
     */
    public Double getEnthalpy298() {
        return enthalpy298;
    }

    /**
     * @param enthalpy
     *            the enthalpy298 to set
     */
    public void setEnthalpy298(final Double enthalpy) {
        this.enthalpy298 = enthalpy;
    }

    @Override
    public Double getBsse() {
        return this.bsse;
    }

    @Override
    public void setBsse(Double bsse) {
        this.bsse = bsse;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultBaseResult)) {
            return false;
        }
        final DefaultBaseResult rhs = (DefaultBaseResult) object;
        return new EqualsBuilder().append(this.basisSet, rhs.basisSet)
                .append(this.mult, rhs.mult)
                .append(this.atomCount, rhs.atomCount)
                .append(this.cpuTimes, rhs.cpuTimes)
                .append(this.frequencyValues, rhs.frequencyValues)
                .append(this.isSymmetric, rhs.isSymmetric)
                .append(this.solvent, rhs.solvent)
                .append(this.functional, rhs.functional)
                .append(this.elecEn, rhs.elecEn)
                .append(this.transPart, rhs.transPart)
                .append(this.gibbs298, rhs.gibbs298)
                .append(this.enthalpy298, rhs.enthalpy298)
                .append(this.atomMap, rhs.atomMap)
                .append(this.charge, rhs.charge)
                .append(this.rotPart, rhs.rotPart)
                .append(this.zpeCorrection, rhs.zpeCorrection)
                .append(this.dipoleMomentTotal, rhs.dipoleMomentTotal)
                .append(this.sourceName, rhs.sourceName)
                .append(this.terminationDates, rhs.terminationDates)
                .append(this.stoichiometry, rhs.stoichiometry).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-2069282171, -1063907465)
                .append(this.basisSet).append(this.mult).append(this.atomCount)
                .append(this.cpuTimes).append(this.frequencyValues)
                .append(this.isSymmetric).append(this.solvent)
                .append(this.functional).append(this.elecEn)
                .append(this.transPart).append(this.gibbs298)
                .append(this.enthalpy298).append(this.atomMap)
                .append(this.charge).append(this.rotPart)
                .append(this.zpeCorrection).append(this.dipoleMomentTotal)
                .append(this.sourceName).append(this.terminationDates)
                .append(this.stoichiometry).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("elecEn", this.elecEn)
                .append("functional", this.functional)
                .append("charge", this.charge).append("atoms", this.getAtoms())
                .append("frequencyValues", this.frequencyValues)
                .append("cpuTimes", this.cpuTimes).append("mult", this.mult)
                .append("atomCount", this.atomCount)
                .append("rotPart", this.rotPart)
                .append("transPart", this.transPart)
                .append("symmetricTop", this.isSymmetricTop())
                .append("gibbs298", this.gibbs298)
                .append("enthalpy298", this.enthalpy298)
                .append("zpeCorrection", this.zpeCorrection)
                .append("stoichiometry", this.stoichiometry)
                .append("basisSet", this.basisSet)
                .append("fileName", this.sourceName)
                .append("dipoleMomentTotal", this.dipoleMomentTotal)
                .append("solvent", this.solvent)
                .append("terminationDates", this.terminationDates)
                .append("atomMap", this.atomMap).toString();
    }
}
