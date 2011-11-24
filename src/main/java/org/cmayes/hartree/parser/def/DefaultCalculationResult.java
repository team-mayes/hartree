package org.cmayes.hartree.parser.def;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.cmayes.hartree.parser.CalculationResult;
import org.joda.time.Duration;

/**
 * Default implementation of a value object for a calculation result.
 * 
 * @author cmayes
 */
public class DefaultCalculationResult implements CalculationResult {
    private List<Date> terminationDates = new ArrayList<Date>();
    private List<Duration> cpuTimes = new ArrayList<Duration>();
    private Double transPart;
    private Double rotPart;
    private Double elecEn;
    private Integer mult;
    private Integer atomCount;
    private List<Double> frequencyValues = new ArrayList<Double>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getElecEn()
     */
    public Double getElecEn() {
        return elecEn;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setElecEn(java.lang.Double)
     */
    public void setElecEn(final Double energies) {
        this.elecEn = energies;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getMult()
     */
    public Integer getMult() {
        return mult;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setMult(java.lang.Integer)
     */
    public void setMult(final Integer multVal) {
        this.mult = multVal;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getRotPart()
     */
    public Double getRotPart() {
        return rotPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setRotPart(java.lang.Double)
     */
    public void setRotPart(final Double rotationalPart) {
        this.rotPart = rotationalPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getTransPart()
     */
    public Double getTransPart() {
        return transPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setTransPart(java.lang.Double
     *      )
     */
    public void setTransPart(final Double translationalPart) {
        this.transPart = translationalPart;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getAtomCount()
     */
    public Integer getAtomCount() {
        return atomCount;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setAtomCount(java.lang.Integer
     *      )
     */
    public void setAtomCount(final Integer count) {
        this.atomCount = count;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getFrequencyValues()
     */
    public List<Double> getFrequencyValues() {
        return frequencyValues;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setFrequencyValues(java.util
     *      .List)
     */
    public void setFrequencyValues(final List<Double> freqValues) {
        this.frequencyValues = freqValues;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getTerminationDates()
     */
    public List<Date> getTerminationDates() {
        return terminationDates;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setTerminationDates(java.
     *      util.List)
     */
    public void setTerminationDates(final List<Date> termDates) {
        this.terminationDates = termDates;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#getCpuTimes()
     */
    public List<Duration> getCpuTimes() {
        return cpuTimes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.parser.CalculationResult#setCpuTimes(java.util.List)
     */
    public void setCpuTimes(final List<Duration> durations) {
        this.cpuTimes = durations;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append(String.format("cpuTimes(%d)", this.cpuTimes.size()),
                        this.cpuTimes)
                .append(String.format("terminationDates(%d)",
                        this.terminationDates.size()), this.terminationDates)
                .append("transPart", this.transPart)
                .append("elecEn", this.elecEn)
                .append(String.format("frequencyValues(%d)",
                        this.frequencyValues.size()), this.frequencyValues)
                .append("mult", this.mult).append("atomCount", this.atomCount)
                .append("rotPart", this.rotPart).toString();
    }
}
