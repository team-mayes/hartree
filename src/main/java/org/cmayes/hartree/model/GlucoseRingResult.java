package org.cmayes.hartree.model;

import java.util.List;

/**
 * Defines a result containing a glucose ring.
 * 
 * @author cmayes
 */
public interface GlucoseRingResult extends BaseResult {
    /**
     * @return the glucoseRing
     */
    List<Atom> getGlucoseRing();

    /**
     * @param ring
     *            the glucoseRing to set
     */
    void setGlucoseRing(final List<Atom> ring);
}
