package org.cmayes.hartree.calc;

/**
 * Contract for performing a calculation on a given input, returning the output
 * of the same type.
 * 
 * @author cmayes
 * 
 * @param <T>
 *            The handled type.
 */
public interface Calculation<T> {

    /**
     * Perfoms the calculation.
     * 
     * @param rawInput
     *            The input to process.
     * @return The results of the calculation.
     */
    T calculate(T rawInput);

}