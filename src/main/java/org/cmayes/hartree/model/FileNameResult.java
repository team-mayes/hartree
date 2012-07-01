package org.cmayes.hartree.model;

public interface FileNameResult {
    /**
     * Returns the file name for this calculation (if applicable).
     * 
     * @return The file name for this calculation.
     */
    String getFileName();

    /**
     * Sets the file name for this calculation.
     * 
     * @param fileName
     *            The file name for this calculation.
     */
    void setFileName(String fileName);
}
