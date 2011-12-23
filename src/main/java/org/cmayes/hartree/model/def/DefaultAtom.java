package org.cmayes.hartree.model.def;

import org.cmayes.hartree.model.Atom;

import com.cmayes.common.AtomicElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Default atom implementation.
 * 
 * @author cmayes
 */
public class DefaultAtom implements Atom {
    private double xPos;
    private double yPos;
    private double zPos;
    private int id;
    private AtomicElement type;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#getxPos()
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#setxPos(double)
     */
    public void setxPos(final double xPosition) {
        this.xPos = xPosition;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#getyPos()
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#setyPos(double)
     */
    public void setyPos(final double yPosition) {
        this.yPos = yPosition;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#getzPos()
     */
    public double getzPos() {
        return zPos;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#setzPos(double)
     */
    public void setzPos(final double zPosition) {
        this.zPos = zPosition;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#getId()
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#setId(int)
     */
    public void setId(final int atomId) {
        this.id = atomId;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#getType()
     */
    public AtomicElement getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.Atom#setType(com.cmayes.common.AtomicElement)
     */
    public void setType(final AtomicElement atomType) {
        this.type = atomType;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultAtom)) {
            return false;
        }
        final DefaultAtom rhs = (DefaultAtom) object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.id, rhs.id).append(this.yPos, rhs.yPos)
                .append(this.xPos, rhs.xPos).append(this.zPos, rhs.zPos)
                .append(this.type, rhs.type).isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1651062229, 1240753677)
                .appendSuper(super.hashCode()).append(this.id)
                .append(this.yPos).append(this.xPos).append(this.zPos)
                .append(this.type).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("type", this.type)
                .append("yPos", this.yPos).append("xPos", this.xPos)
                .append("zPos", this.zPos).append("id", this.id).toString();
    }
}
