package org.cmayes.hartree.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents the central pair of atoms in a dihedral.
 * 
 * @author cmayes
 */
public class DihedralPair implements Comparable<DihedralPair> {
    private final int lower;
    private final int higher;

    /**
     * Creates a dihedral pair. The order of the arguments does not matter.
     * 
     * @param first
     *            The first atom.
     * @param second
     *            The second atom.
     */
    public DihedralPair(final int first, final int second) {
        if (first > second) {
            this.higher = first;
            this.lower = second;
        } else {
            this.higher = second;
            this.lower = first;
        }
    }

    /**
     * @return the lower
     */
    public int getLower() {
        return lower;
    }

    /**
     * @return the higher
     */
    public int getHigher() {
        return higher;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DihedralPair)) {
            return false;
        }
        final DihedralPair rhs = (DihedralPair) object;
        return new EqualsBuilder().append(this.lower, rhs.lower)
                .append(this.higher, rhs.higher).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1654337759, 2118448817).append(this.lower)
                .append(this.higher).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("lower", this.lower).append("higher", this.higher)
                .toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(final DihedralPair object) {
        final DihedralPair myClass = (DihedralPair) object;
        return new CompareToBuilder().append(this.lower, myClass.lower)
                .append(this.higher, myClass.higher).toComparison();
    }
}
