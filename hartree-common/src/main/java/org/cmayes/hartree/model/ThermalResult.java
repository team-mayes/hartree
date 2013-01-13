package org.cmayes.hartree.model;

/**
 * Represents the result of a thermal calculation.
 * 
 * @author cmayes
 */
public interface ThermalResult {

    /**
     * @return the temperature
     */
    double getTemperature();

    /**
     * @param temp
     *            the temperature to set
     */
    void setTemperature(final double temp);

    /**
     * @return the entropy
     */
    double getEntropy();

    /**
     * @param theEntropy
     *            the entropy to set
     */
    void setEntropy(final double theEntropy);

    /**
     * @return the enthalpy
     */
    double getEnthalpy();

    /**
     * @param theEnthalpy
     *            the enthalpy to set
     */
    void setEnthalpy(final double theEnthalpy);

    /**
     * @return the heatCapacity
     */
    double getHeatCapacity();

    /**
     * @param heatCap
     *            the heatCapacity to set
     */
    void setHeatCapacity(final double heatCap);

}