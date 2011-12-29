package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.Atom;
import org.cmayes.hartree.model.NormalMode;
import org.cmayes.hartree.model.NormalModeSummary;

/**
 * Default implementation of a normal mode summary.
 * 
 * @author cmayes
 */
public class DefaultNormalModeSummary implements NormalModeSummary {
    private List<Atom> atoms = new ArrayList<Atom>();

    private List<NormalMode> normalModes = new ArrayList<NormalMode>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#getAtoms()
     */
    @Override
    public List<Atom> getAtoms() {
        return atoms;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalModeSummary#setAtoms(java.util.List)
     */
    @Override
    public void setAtoms(final List<Atom> ats) {
        this.atoms = ats;
    }

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
