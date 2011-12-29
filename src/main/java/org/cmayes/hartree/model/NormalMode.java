package org.cmayes.hartree.model;

import java.util.List;


/**
 * Describes a normal vibrational mode.
 * 
 * @author cmayes
 */
public interface NormalMode {

    /**
     * @return the id
     */
    Integer getId();

    /**
     * @param theId
     *            the id to set
     */
    void setId(final Integer theId);

    /**
     * Returns the component internal motions that are a part of this mode.
     * 
     * @return the components
     */
    List<InternalMotion> getComponents();

    /**
     * @param comps
     *            the components to set
     */
    void setComponents(final List<InternalMotion> comps);

}