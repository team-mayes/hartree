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
}