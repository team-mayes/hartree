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
    private Integer id;
    private List<InternalMotion> components = new ArrayList<InternalMotion>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#setId(java.lang.Integer)
     */
    @Override
    public void setId(final Integer theId) {
        this.id = theId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#getComponents()
     */
    @Override
    public List<InternalMotion> getComponents() {
        return components;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#setComponents(java.util.List)
     */
    @Override
    public void setComponents(final List<InternalMotion> comps) {
        this.components = comps;
    }
}
