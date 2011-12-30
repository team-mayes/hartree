package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeSummary;

/**
 * Default implementation of a normal mode summary.
 * 
 * @author cmayes
 */
public class DefaultNormalModeSummary extends BaseCalculationResult implements
        NormalModeSummary {
    private List<NormalMode> normalModes = new ArrayList<NormalMode>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#getNormalModes()
     */
    @Override
    public List<NormalMode> getNormalModes() {
        return normalModes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#setNormalModes(java.util.List)
     */
    @Override
    public void setNormalModes(final List<NormalMode> norModes) {
        this.normalModes = norModes;
    }
}
