package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.cmayes.hartree.model.Atom;
import org.cmayes.hartree.model.BaseResult;
import org.joda.time.Duration;

/**
 * Data common to multiple result types.
 * 
 * @author cmayes
 */
public class BaseCalculationResult implements BaseResult {
    private List<Atom> atoms = new ArrayList<Atom>();
    private List<Date> terminationDates = new ArrayList<Date>();
    private List<Duration> cpuTimes = new ArrayList<Duration>();
    private Double transPart;
    private Double rotPart;
    private Integer mult;
    private List<Double> frequencyValues = new ArrayList<Double>();
    private boolean isSymmetric = true;
    private String fileName;

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
     * @param fileName
     *            The file name for this calculation.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Looks up the atom with the given ID by pulling the atom in the atoms
     * field by the ID - 1.
     * 
     * @param id
     *            The atom to pull.
     * @return The atom at the zero-based index equivalent of the ID.
     */
    public Atom getAtomById(final int id) {
        try {
            return getAtoms().get(id - 1);
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No atom with ID " + id, e);
        }
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
     * @return the atoms
     */
    public List<Atom> getAtoms() {
        return atoms;
    }

    /**
     * @param ats
     *            the atoms to set
     */
    public void setAtoms(final List<Atom> ats) {
        this.atoms = ats;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof BaseCalculationResult)) {
            return false;
        }
        final BaseCalculationResult rhs = (BaseCalculationResult) object;
        return new EqualsBuilder().append(this.atoms, rhs.atoms)
                .append(this.mult, rhs.mult)
                .append(this.frequencyValues, rhs.frequencyValues)
                .append(this.rotPart, rhs.rotPart)
                .append(this.cpuTimes, rhs.cpuTimes)
                .append(this.isSymmetric, rhs.isSymmetric)
                .append(this.terminationDates, rhs.terminationDates)
                .append(this.transPart, rhs.transPart).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1174730777, -1227814673).append(this.atoms)
                .append(this.mult).append(this.frequencyValues)
                .append(this.rotPart).append(this.cpuTimes)
                .append(this.isSymmetric).append(this.terminationDates)
                .append(this.transPart).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("symmetricTop", this.isSymmetricTop())
                .append("transPart", this.transPart)
                .append("atoms", this.atoms)
                .append("frequencyValues", this.frequencyValues)
                .append("cpuTimes", this.cpuTimes).append("mult", this.mult)
                .append("terminationDates", this.terminationDates)
                .append("rotPart", this.rotPart).toString();
    }
}
