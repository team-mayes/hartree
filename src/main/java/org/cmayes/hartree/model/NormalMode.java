package org.cmayes.hartree.model;

import java.util.List;


/**
 * Describes a normal vibrational mode.
 * 
 * @author cmayes
 */
public interface NormalMode {
    /**
     * Returns the component internal motions that are a part of this mode.
     * 
     * @return the components
     */
    List<InternalMotion> getMotions();

    /**
     * @param comps
     *            the components to set
     */
    void setMotions(final List<InternalMotion> comps);
    
    
}