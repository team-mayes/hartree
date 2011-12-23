package org.cmayes.hartree.model;

import com.cmayes.common.AtomicElement;

/**
 * Defines the properties of an atom.
 * 
 * @author cmayes
 */
public interface Atom {

    /**
     * 
     * @return the xPos
     */
    double getxPos();

    /**
     * @param xPosition
     *            the xPos to set
     */
    void setxPos(final double xPosition);

    /**
     * @return the yPos
     */
    double getyPos();

    /**
     * @param yPosition
     *            the yPos to set
     */
    void setyPos(final double yPosition);

    /**
     * @return the zPos
     */
    double getzPos();

    /**
     * @param zPosition
     *            the zPos to set
     */
    void setzPos(final double zPosition);

    /**
     * @return the id
     */
    int getId();

    /**
     * @param atomId
     *            the id to set
     */
    void setId(final int atomId);

    /**
     * @return the type
     */
    AtomicElement getType();

    /**
     * @param atomType
     *            the type to set
     */
    void setType(final AtomicElement atomType);

}