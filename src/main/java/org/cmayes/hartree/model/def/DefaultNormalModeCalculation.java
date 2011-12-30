package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeCalculation;

/**
 * Default implementation of a normal mode summary.
 * 
 * @author cmayes
 */
public class DefaultNormalModeCalculation extends BaseCalculationResult
        implements NormalModeCalculation {
    private List<NormalMode> normalModes = new ArrayList<NormalMode>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeCalculation#getNormalModes()
     */
    @Override
    public List<NormalMode> getNormalModes() {
        return normalModes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeCalculation#setNormalModes(java.util.List)
     */
    @Override
    public void setNormalModes(final List<NormalMode> norModes) {
        this.normalModes = norModes;
    }
}
