package org.cmayes.hartree.loader;

import java.util.Date;
import java.util.List;

import org.joda.time.Duration;

/**
 * Defines a value object for a calculation result.
 * 
 * @author cmayes
 */
public interface CalculationResult {

    /**
     * Returns electronic energy.
     * 
     * @return electronic energy.
     */
    Double getElecEn();

    /**
     * Sets electronic energy.
     * 
     * @param energy
     *            The energy to set.
     */
    void setElecEn(final Double energy);

    /**
     * Returns the multiplicity of the molecules.
     * 
     * @return The multiplicity of the molecules.
     */
    Integer getMult();

    /**
     * Sets the multiplicity of the molecules.
     * 
     * @param multVal
     *            The multiplicity value to set.
     */
    void setMult(final Integer multVal);

    /**
     * Returns the rotational partition value.
     * 
     * @return The rotational partition value.
     */
    Double getRotPart();

    /**
     * Returns the rotational partition value.
     * 
     * @param rotationalPart
     *            The rotational value to set.
     */
    void setRotPart(final Double rotationalPart);

    /**
     * Returns the translational partition value.
     * 
     * @return The translational partition value.
     */
    Double getTransPart();

    /**
     * Returns the translational partition value.
     * 
     * @param translationalPart
     *            The translational partition value to set.
     */
    void setTransPart(final Double translationalPart);

    /**
     * Returns the number of atoms in the calculation.
     * 
     * @return The number of atoms in the calculation.
     */
    Integer getAtomCount();

    /**
     * Sets the number of atoms in the calculation.
     * 
     * @param count
     *            the number of atoms to set.
     */
    void setAtomCount(final Integer count);

    /**
     * Returns the frequency values for each degree of freedom.
     * 
     * @return The frequency values for each degree of freedom.
     */
    List<Double> getFrequencyValues();

    /**
     * Sets the frequency values for each degree of freedom.
     * 
     * @param freqValues
     *            The frequency values to set.
     */
    void setFrequencyValues(final List<Double> freqValues);

    /**
     * Return the termination dates for each step of the calculation.
     * 
     * @return The termination dates for each step of the calculation.
     */
    List<Date> getTerminationDates();

    /**
     * Sets the termination dates for each step of the calculation.
     * 
     * @param termDates
     *            The dates to set.
     */
    void setTerminationDates(final List<Date> termDates);

    /**
     * Return the durations for each step of the calculation.
     * 
     * @return The durations for each step of the calculation.
     */
    List<Duration> getCpuTimes();

    /**
     * Set the durations for each step of the calculation.
     * 
     * @param durations
     *            The durations to set.
     */
    void setCpuTimes(final List<Duration> durations);
}