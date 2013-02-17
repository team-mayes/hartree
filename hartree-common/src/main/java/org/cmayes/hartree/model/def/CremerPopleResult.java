package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.GlucoseRingResult;

/**
 * Defines a result containing Cremer-Pople coordinates.
 * 
 * @author cmayes
 */
public interface CremerPopleResult extends GlucoseRingResult {
    /**
     * @return the cpCoords
     */
    CremerPopleCoordinates getCpCoords();

    /**
     * @param coords
     *            the cpCoords to set
     */
    void setCpCoords(final CremerPopleCoordinates coords);
}
