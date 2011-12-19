package org.cmayes.hartree.model;

/**
 * Represents atomic elements, providing the elements' chemical symbol, atomic
 * number, and atomic mass. Most atomic values from {@link http
 * ://www.webelements.com/}
 * 
 * @author cmayes
 */
public enum AtomicElement {
    HYDROGEN("H", 1, 1.00794), HELIUM("He", 2, 4.002602), LITHIUM("Li", 3,
            6.941), BERYLLIUM("Be", 4, 9.012182), BORON("B", 5, 10.811), CARBON(
            "C", 6, 12.0107), NITROGEN("N", 7, 14.0067), OXYGEN("O", 8, 15.9994), FLUORINE(
            "F", 9, 18.9984032), NEON("Ne", 10, 20.1797), SODIUM("Na", 11,
            22.98976928), MAGNESIUM("Mg", 12, 24.3050), ALUMINIUM("Al", 13,
            26.9815386), SILICON("Si", 14, 28.0855), PHOSPHOROUS("P", 15,
            30.973762), SULFUR("S", 16, 32.065), CHLORINE("Cl", 17, 35.453), ARGON(
            "Ar", 18, 39.948), POTASSIUM("K", 19, 39.0983), CALCIUM("Ca", 20,
            40.078);

    private final String symbol;
    private final int atomicNumber;
    private final double atomicMass;

    /**
     * Creates an element with the given symbol and number.
     * 
     * @param sym
     *            The chemical symbol for this element.
     * @param num
     *            The atomic number for this element.
     * @param mass
     *            The atomic mass for this element.
     */
    AtomicElement(final String sym, final int num, final double mass) {
        this.symbol = sym;
        this.atomicNumber = num;
        this.atomicMass = mass;
    }

    /**
     * Returns the chemical symbol for this element.
     * 
     * @return The chemical symbol for this element.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the atomic number for this element.
     * 
     * @return The atomic number for this element.
     */
    public int getNumber() {
        return atomicNumber;
    }

    /**
     * Returns the atomic mass for this element.
     * 
     * @return The atomic mass of this element.
     */
    public double getMass() {
        return atomicMass;
    }

    /**
     * Look up an element by atomic number.
     * 
     * @param number
     *            The atomic number to look up.
     * @return The element matching the given number.
     * @throws IllegalArgumentException
     *             If not elements match the given number.
     */
    public static AtomicElement valueOf(final int number) {
        for (AtomicElement val : AtomicElement.values()) {
            if (number == val.getNumber()) {
                return val;
            }
        }
        throw new IllegalArgumentException("No element number " + number);
    }
}
