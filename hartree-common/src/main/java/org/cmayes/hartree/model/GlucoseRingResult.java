package org.cmayes.hartree.model;

import java.util.List;

import com.cmayes.common.model.Atom;

/**
 * Defines a result containing a glucose ring.
 * 
 * @author cmayes
 */
public interface GlucoseRingResult {
    /**
     * @return the glucoseRing
     */
    List<Atom> getGlucoseRing();

    /**
     * @param ring
     *            the glucoseRing to set
     */
    void setGlucoseRing(final List<Atom> ring);

    /**
     * @return the carbonDistances
     */
    List<Double> getCarbonDistances();

    /**
     * @return the oxygenDistances
     */
    List<Double> getOxygenDistances();

    /**
     * @return the ionDistances
     */
    List<Double> getIonDistances();

    /**
     * The first Hydroxymethyl arm angle in degrees for O5 - C5 - C6 - O6.
     * 
     * @return the hmArmAngle1
     */
    Double getHmArmAngle1();

    /**
     * The second Hydroxymethyl arm angle in degrees for C4 - C5 - C6 - O6.
     * 
     * @return the hmArmAngle2
     */
    Double getHmArmAngle2();
    
    /**
     * The first dihedral angle in degrees for the Acetyl arm of NAG: C1 - C2 -
     * N - C7.
     * 
     * @return the acArmAngle1
     */
    Double getAcArmAngle1();

    /**
     * The second dihedral angle in degrees for the Acetyl arm of NAG: C3 - C2 - N - C7.
     * 
     * @return the acArmAngle2
     */
    Double getAcArmAngle2();
    
    /**
     * The first third angle in degrees for C5 - O5 - C1 - O1.
     * 
     * @return the thirdAngle1
     */
    Double getAnoAngle1();

    /**
     * The second third angle in degrees for C3 - C2 - C1 - O1.
     * 
     * @return the thirdAngle2
     */
    Double getAnoAngle2();
}
