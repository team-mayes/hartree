package org.cmayes.hartree.model.def;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
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

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultThermalResult)) {
            return false;
        }
        final DefaultThermalResult rhs = (DefaultThermalResult) object;
        return new EqualsBuilder().append(this.enthalpy, rhs.enthalpy)
                .append(this.entropy, rhs.entropy)
                .append(this.heatCapacity, rhs.heatCapacity)
                .append(this.temperature, rhs.temperature).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-742512927, 2044755235)
                .append(this.enthalpy).append(this.entropy)
                .append(this.heatCapacity).append(this.temperature)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("temperature", this.temperature)
                .append("heatCapacity", this.heatCapacity)
                .append("entropy", this.entropy)
                .append("enthalpy", this.enthalpy).toString();
    }
}
