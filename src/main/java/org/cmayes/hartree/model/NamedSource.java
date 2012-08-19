package org.cmayes.hartree.model;

/**
 * Indicates that the model class has a named source.
 * 
 * @author cmayes
 */
public interface NamedSource {
    /**
     * Returns the identifier for the source data.
     * 
     * @return The identifier for the source data.
     */
    String getSourceName();

    /**
     * Sets the identifier for the source data.
     * 
     * @param srcName
     *            The identifier for the source data.
     */
    void setSourceName(String srcName);
}
