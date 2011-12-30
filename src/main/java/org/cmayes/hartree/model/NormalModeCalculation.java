package org.cmayes.hartree.model;

import java.util.List;


/**
 * Produces summaries of the normal modes found in the given atomic system.
 * 
 * @author cmayes
 */
public interface NormalModeCalculation extends BaseResult {
    /**
     * @return the normalModes
     */
    List<NormalMode> getNormalModes();

    /**
     * @param norModes
     *            the normalModes to set
     */
    void setNormalModes(final List<NormalMode> norModes);
}