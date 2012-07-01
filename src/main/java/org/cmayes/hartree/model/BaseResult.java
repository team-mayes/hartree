package org.cmayes.hartree.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;

import com.cmayes.common.model.Atom;

/**
 * Data common to multiple result types.
 * 
 * @author cmayes
 */
public interface BaseResult extends FileNameResult {
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
     * Looks up the atom with the given ID by pulling the atom in the atoms
     * field by the ID - 1.
     * 
     * @param id
     *            The atom to pull.
     * @return The atom at the zero-based index equivalent of the ID.
     * @throws IllegalArgumentException
     *             If the atom does not exist.
     */
    Atom getAtomById(final int id);

    /**
     * Returns the map of atoms to their IDs.
     * 
     * @return The map of atoms to their IDs.
     */
    Map<Integer, Atom> getAtomMap();

    /**
     * Sets the atom map.
     * 
     * @param atomMap
     *            The atom map to set.
     */
    void setAtomMap(Map<Integer, Atom> atomMap);

    /**
     * Returns a copy of the atom map's values. Note that modifications to the
     * returned list are not applied to the map's values.
     * 
     * @return A list of atoms in this result.
     */
    List<Atom> getAtoms();

    /**
     * Adds the atom to the atom map by using its ID as the map's key.
     * 
     * @param addMe
     *            The atom to add.
     * @throws IllegalArgumentException
     *             If addMe is null.
     */
    public void addAtom(Atom addMe);

    /**
     * @return the functional
     */
    String getFunctional();

    /**
     * @param functional
     *            the functional to set
     */
    void setFunctional(String functional);

    /**
     * @return the basisSet
     */
    String getBasisSet();

    /**
     * @param basisSet
     *            the basisSet to set
     */
    void setBasisSet(String basisSet);

    /**
     * Returns the name of the solvent (e.g. water, ethanol, etc.).
     * 
     * @return The name of the solvent.
     */
    String getSolvent();

    /**
     * Sets the name of the solvent.
     * 
     * @param solvent
     *            The solvent name to set.
     */
    void setSolvent(String solvent);

    /**
     * Returns the zero-point energy correction in Hartree/Particle.
     * 
     * @return The zero-point energy correction in Hartree/Particle
     */
    Double getZpeCorrection();

    /**
     * Sets ZPE correction.
     * 
     * @param zpeCorrection
     *            The value to set.
     */
    void setZpeCorrection(Double zpeCorrection);

    /**
     * Returns the system's charge.
     * 
     * @return The system's charge.
     */
    Integer getCharge();

    /**
     * Sets the system's charge.
     * 
     * @param charge
     *            The charge to set.
     */
    void setCharge(Integer charge);

    /**
     * Returns the stoichiometry.
     * 
     * @return The stoichiometry.
     */
    String getStoichiometry();

    /**
     * Sets the stoichiometry.
     * 
     * @param stoichiometry
     *            The value to set.
     */
    void setStoichiometry(String stoichiometry);

    /**
     * Returns the total dipole moment in Debyes.
     * 
     * @return The total dipole moment in Debyes
     */
    Double getDipoleMomentTotal();

    /**
     * Set the total dipole moment in Debyes.
     * 
     * @param dmTotal
     *            The total to set.
     */
    void setDipoleMomentTotal(Double dmTotal);

    /**
     * Returns the Gibbs free energy in Hartrees at 298 degrees Kelvin.
     * 
     * @return the gibbs298
     */
    Double getGibbs298();

    /**
     * Sets the Gibbs free energy in Hartrees at 298 degrees Kelvin.
     * 
     * @param g298
     *            the gibbs298 to set
     */
    void setGibbs298(final Double g298);

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
     * Returns the number of atoms in this system.
     * 
     * @return The number of atoms in this system.
     */
    Integer getAtomCount();

    /**
     * Sets the number of atoms in this system.
     * 
     * @param count
     *            The number to set.
     */
    void setAtomCount(final Integer count);
}
