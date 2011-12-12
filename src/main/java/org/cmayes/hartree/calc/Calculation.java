package org.cmayes.hartree.calc;

/**
 * Contract for performing a calculation on a given input, returning the output
 * of the same type.
 * 
 * @author cmayes
 * 
 * @param <I>
 *            The input type.
 * @param <O>
 *            The output type.
 */
public interface Calculation<I, O> {

    /**
     * Perfoms the calculation.
     * 
     * @param rawInput
     *            The input to process.
     * @return The results of the calculation.
     */
    O calculate(I rawInput);

}