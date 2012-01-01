package org.cmayes.hartree.model;

import java.util.Date;
import java.util.List;

import org.joda.time.Duration;

/**
 * Data common to multiple result types.
 * 
 * @author cmayes
 */
public interface BaseResult {
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

    /**
     * @param isSymTop
     *            the isSymmetricTop to set
     */
    void setSymmetricTop(boolean isSymTop);

    /**
     * Return whether the molecule's top is symmetric.
     * 
     * @return the isSymmetricTop
     */
    boolean isSymmetricTop();

    /**
     * Returns the atoms used in the calculation.
     * 
     * @return The atoms used in the calculation.
     */
    List<Atom> getAtoms();

    /**
     * Sets the atoms used in the calculation.
     * 
     * @param atoms
     *            The atoms to set.
     */
    void setAtoms(List<Atom> atoms);

    /**
     * Looks up the atom with the given ID by pulling the atom in the atoms
     * field by the ID - 1.
     * 
     * @param id
     *            The atom to pull.
     * @return The atom at the zero-based index equivalent of the ID.
     */
    Atom getAtomById(final int id);
}
