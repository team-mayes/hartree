package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.InternalMotion;

import com.cmayes.common.chem.InternalMotionType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Default implementation of an internal motion.
 * 
 * @author cmayes
 */
public class DefaultInternalMotion implements InternalMotion {
    private InternalMotionType type;
    private String name;
    private List<Integer> members = new ArrayList<Integer>();
    private Double value;
    private Double weight;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#getType()
     */
    @Override
    public InternalMotionType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setType(com.cmayes.common.chem.InternalMotionType)
     */
    @Override
    public void setType(final InternalMotionType motionType) {
        this.type = motionType;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setName(java.lang.String)
     */
    @Override
    public void setName(final String theName) {
        this.name = theName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#getMembers()
     */
    @Override
    public List<Integer> getMembers() {
        return members;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setMembers(java.util.List)
     */
    @Override
    public void setMembers(final List<Integer> mems) {
        this.members = mems;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#getValue()
     */
    @Override
    public Double getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setValue(Double)
     */
    @Override
    public void setValue(final Double val) {
        this.value = val;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#getWeight()
     */
    @Override
    public Double getWeight() {
        return weight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setWeight(Double)
     */
    @Override
    public void setWeight(final Double wt) {
        this.weight = wt;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultInternalMotion)) {
            return false;
        }
        final DefaultInternalMotion rhs = (DefaultInternalMotion) object;
        return new EqualsBuilder().append(this.weight, rhs.weight)
                .append(this.name, rhs.name).append(this.value, rhs.value)
                .append(this.type, rhs.type).append(this.members, rhs.members)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1621625929, 1520039581).append(this.weight)
                .append(this.name).append(this.value).append(this.type)
                .append(this.members).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("value", this.value)
                .append("type", this.type).append("name", this.name)
                .append("weight", this.weight).append("members", this.members)
                .toString();
    }
}
