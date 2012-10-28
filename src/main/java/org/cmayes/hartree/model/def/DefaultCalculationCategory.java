package org.cmayes.hartree.model.def;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cmayes.hartree.model.CalculationCategory;

/**
 * Default implementation of {@link CalculationCategory}.
 * 
 * @author cmayes
 */
public class DefaultCalculationCategory implements CalculationCategory {
    private Integer id;
    private String name;
    private String description;

    /**
     * Zero-arg constructor.
     */
    public DefaultCalculationCategory() {

    }

    /**
     * Creates an instance with the given name and description.
     * 
     * @param catName
     *            The category name.
     * @param desc
     *            The category description.
     */
    public DefaultCalculationCategory(final String catName, final String desc) {
        this.name = catName;
        this.description = desc;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationCategory#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationCategory#setId(java.lang.Integer)
     */
    @Override
    public void setId(final Integer catId) {
        this.id = catId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationCategory#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationCategory#setName(java.lang.String)
     */
    @Override
    public void setName(final String catName) {
        this.name = catName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationCategory#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.CalculationCategory#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(final String desc) {
        this.description = desc;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof CalculationCategory)) {
            return false;
        }
        final CalculationCategory rhs = (CalculationCategory) object;
        return new EqualsBuilder().append(this.id, rhs.getId())
                .append(this.description, rhs.getDescription())
                .append(this.name, rhs.getName()).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1050640559, -1309582445).append(this.id)
                .append(this.description).append(this.name).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("description", this.description)
                .append("name", this.name).append("id", this.id).toString();
    }
}
