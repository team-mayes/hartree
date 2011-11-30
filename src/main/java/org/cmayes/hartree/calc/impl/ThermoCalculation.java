package org.cmayes.hartree.calc.impl;

import org.cmayes.hartree.calc.Calculation;

/**
 * Calculates enthalpy, entropy, and heat capacity needed for calculating Gibbs
 * free energy and kinetic parameters.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The type of data processed and returned.
 */
public class ThermoCalculation<T> implements Calculation<T> {
    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.calc.impl.Calculation#calculate(T)
     */
    public T calculate(final T rawInput) {
        return null;
    }
}
