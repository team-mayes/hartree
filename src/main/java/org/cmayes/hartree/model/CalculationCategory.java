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
    Long getId();

    /**
     * @param catId
     *            the id to set
     */
    void setId(Long catId);

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