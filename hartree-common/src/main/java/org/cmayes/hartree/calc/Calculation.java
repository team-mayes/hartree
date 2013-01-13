package org.cmayes.hartree.calc;

/**
 * Contract for performing a calculation on a given input.
 * 
 * @author cmayes
 */
public interface Calculation {

    /**
     * Perfoms the calculation.
     * 
     * @param procResult
     *            The input to process.
     * @return The results of the calculation.
     */
    Object calculate(Object procResult);

}