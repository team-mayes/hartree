package org.cmayes.hartree.model;

/**
 * A category for calculations.
 * 
 * @author cmayes
 */
public interface CalculationCategory {
    /**
     * @return the id
     */
    Integer getId();

    /**
     * @param catId
     *            the id to set
     */
    void setId(Integer catId);

    /**
     * @return the name
     */
    String getName();

    /**
     * @param catName
     *            the name to set
     */
    void setName(String catName);

    /**
     * @return the description
     */
    String getDescription();

    /**
     * @param desc
     *            the description to set
     */
    void setDescription(String desc);

}