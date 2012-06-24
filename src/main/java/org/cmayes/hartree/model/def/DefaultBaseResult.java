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
    private String fileName;
    private String functional;
    private String basisSet;
    private String solvent;
    private String stoichiometry;
    private Double zpeCorrection;
    private Double dipoleMomentTotal;
    private Double gibbs298;
    private Double elecEn;
    private Integer atomCount;

    /**
     * Zero-arg constructor.
     */
    public DefaultBaseResult() {

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
        this.fileName = baseResult.getFileName();
        this.frequencyValues = baseResult.getFrequencyValues();
        this.functional = baseResult.getFunctional();
        this.gibbs298 = baseResult.getGibbs298();
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
     * @param atomMap
     *            The atom map to set.
     */
    public void setAtomMap(Map<Integer, Atom> atomMap) {
        this.atomMap = new TreeMap<Integer, Atom>(atomMap);
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
    public void addAtom(Atom addMe) {
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
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name for this calculation.
     * 
     * @param fName
     *            The file name for this calculation.
     */
    public void setFileName(final String fName) {
        this.fileName = fName;
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
        Atom atom = atomMap.get(id);
        if (atom == null) {
            throw new IllegalArgumentException("No atom with ID " + id);
        }
        return atom;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getMult()
     */
    public Integer getMult() {
        return mult;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setMult(java.lang.Integer)
     */
    public void setMult(final Integer multVal) {
        this.mult = multVal;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getRotPart()
     */
    public Double getRotPart() {
        return rotPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setRotPart(java.lang.Double)
     */
    public void setRotPart(final Double rotationalPart) {
        this.rotPart = rotationalPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getTransPart()
     */
    public Double getTransPart() {
        return transPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setTransPart(java.lang.Double
     *      )
     */
    public void setTransPart(final Double translationalPart) {
        this.transPart = translationalPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getFrequencyValues()
     */
    public List<Double> getFrequencyValues() {
        return frequencyValues;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setFrequencyValues(java.util
     *      .List)
     */
    public void setFrequencyValues(final List<Double> freqValues) {
        this.frequencyValues = freqValues;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getTerminationDates()
     */
    public List<Date> getTerminationDates() {
        return terminationDates;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setTerminationDates(java.
     *      util.List)
     */
    public void setTerminationDates(final List<Date> termDates) {
        this.terminationDates = termDates;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getCpuTimes()
     */
    public List<Duration> getCpuTimes() {
        return cpuTimes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setCpuTimes(java.util.List)
     */
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

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getElecEn()
     */
    public Double getElecEn() {
        return elecEn;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setElecEn(java.lang.Double)
     */
    public void setElecEn(final Double energies) {
        this.elecEn = energies;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#getAtomCount()
     */
    public Integer getAtomCount() {
        return atomCount;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationResult#setAtomCount(java.lang.Integer
     *      )
     */
    public void setAtomCount(final Integer count) {
        this.atomCount = count;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof DefaultBaseResult)) {
            return false;
        }
        DefaultBaseResult rhs = (DefaultBaseResult) object;
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
                .append(this.atomMap, rhs.atomMap)
                .append(this.charge, rhs.charge)
                .append(this.rotPart, rhs.rotPart)
                .append(this.zpeCorrection, rhs.zpeCorrection)
                .append(this.dipoleMomentTotal, rhs.dipoleMomentTotal)
                .append(this.fileName, rhs.fileName)
                .append(this.terminationDates, rhs.terminationDates)
                .append(this.stoichiometry, rhs.stoichiometry).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-2069282171, -1063907465)
                .append(this.basisSet).append(this.mult).append(this.atomCount)
                .append(this.cpuTimes).append(this.frequencyValues)
                .append(this.isSymmetric).append(this.solvent)
                .append(this.functional).append(this.elecEn)
                .append(this.transPart).append(this.gibbs298)
                .append(this.atomMap).append(this.charge).append(this.rotPart)
                .append(this.zpeCorrection).append(this.dipoleMomentTotal)
                .append(this.fileName).append(this.terminationDates)
                .append(this.stoichiometry).toHashCode();
    }

    /**
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
                .append("zpeCorrection", this.zpeCorrection)
                .append("stoichiometry", this.stoichiometry)
                .append("basisSet", this.basisSet)
                .append("fileName", this.fileName)
                .append("dipoleMomentTotal", this.dipoleMomentTotal)
                .append("solvent", this.solvent)
                .append("terminationDates", this.terminationDates)
                .append("atomMap", this.atomMap).toString();
    }
}
