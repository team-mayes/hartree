package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.InternalMotion;
import org.cmayes.hartree.model.NormalMode;

/**
 * Default implementation of a normal vibrational mode.
 * 
 * @author cmayes
 */
public class DefaultNormalMode implements NormalMode {
    private List<InternalMotion> motions = new ArrayList<InternalMotion>();
    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#getMotions()
     */
    @Override
    public List<InternalMotion> getMotions() {
        return motions;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#setMotions(java.util.List)
     */
    @Override
    public void setMotions(final List<InternalMotion> comps) {
        this.motions = comps;
    }
}
