package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.cmayes.hartree.model.InternalMotion;
import org.cmayes.hartree.model.NormalMode;

/**
 * Default implementation of a normal vibrational mode.
 * 
 * @author cmayes
 */
public class DefaultNormalMode implements NormalMode {
    private List<InternalMotion> motions = new ArrayList<InternalMotion>();

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#getMotions()
     */
    @Override
    public List<InternalMotion> getMotions() {
        return motions;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.NormalMode#setMotions(java.util.List)
     */
    @Override
    public void setMotions(final List<InternalMotion> comps) {
        this.motions = comps;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultNormalMode)) {
            return false;
        }
        final DefaultNormalMode rhs = (DefaultNormalMode) object;
        return new EqualsBuilder().append(this.motions, rhs.motions).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-76103581, -991485133).append(this.motions)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("motions", this.motions)
                .toString();
    }
}
