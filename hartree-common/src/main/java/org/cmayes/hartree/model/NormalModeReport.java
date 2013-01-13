package org.cmayes.hartree.model;

import java.util.Map;

/**
 * Defines a report made on the normal modes for a given calculation.
 * 
 * @author cmayes
 */
public interface NormalModeReport {
    /**
     * Returns a map of normal modes that have the highest percentage of a
     * degree of freedom for a dihedral pair, keyed by that pair. The value for
     * the dihedral can be looked up from the summary map.
     * 
     * @return A map of normal modes that have the highest percentage of a
     *         degree of freedom for a dihedral pair, keyed by that pair.
     */
    Map<DihedralPair, NormalMode> findHighestDihedrals();

    /**
     * Returns summaries of the normal mode's degree of freedom keyed by the
     * normal mode.
     * 
     * @return A map of normal modes to summaries.
     */
    Map<NormalMode, NormalModeSummary> getSummaries();
}