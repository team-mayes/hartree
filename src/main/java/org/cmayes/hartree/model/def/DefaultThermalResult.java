package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.ThermalResult;

/**
 * Default implementation of a thermal calculation result.
 * 
 * @author cmayes
 */
public class DefaultThermalResult implements ThermalResult {
    private double temperature;
    private double entropy;
    private double enthalpy;
    private double heatCapacity;

    /**
     * Zero-arg constructor.
     */
    public DefaultThermalResult() {

    }

    /**
     * Creates a result for the given temperature.
     * 
     * @param temp
     *            The temperature for this calculation.
     */
    public DefaultThermalResult(final double temp) {
        this.temperature = temp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#getTemperature()
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#setTemperature(double)
     */
    public void setTemperature(final double temp) {
        this.temperature = temp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#getEntropy()
     */
    public double getEntropy() {
        return entropy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#setEntropy(double)
     */
    public void setEntropy(final double theEntropy) {
        this.entropy = theEntropy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#getEnthalpy()
     */
    public double getEnthalpy() {
        return enthalpy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#setEnthalpy(double)
     */
    public void setEnthalpy(final double theEnthalpy) {
        this.enthalpy = theEnthalpy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#getHeatCapacity()
     */
    public double getHeatCapacity() {
        return heatCapacity;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.ThermalResult#setHeatCapacity(double)
     */
    public void setHeatCapacity(final double heatCap) {
        this.heatCapacity = heatCap;
    }
}
