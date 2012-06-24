package org.cmayes.hartree.model.def;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Models Cremer-Pople coordinates.
 * 
 * Radius Q means the magnitude of puckering, measuring the deviation from the
 * perfectly flat six-membered ring.
 * 
 * @author cmayes
 */
public class CremerPopleCoordinates {
    private double phi;
    private double theta;
    private double q;
    private String pucker;

    /**
     * Zero-arg constructor.
     */
    public CremerPopleCoordinates() {

    }

    /**
     * Creates coordinates with the given values.
     * 
     * @param thePhi
     *            Phi.
     * @param theTheta
     *            Theta.
     * @param theQ
     *            Magnitude of puckering.
     */
    public CremerPopleCoordinates(final double thePhi, final double theTheta,
            final double theQ) {
        this.phi = thePhi;
        this.theta = theTheta;
        this.q = theQ;
    }

    /**
     * @return the phi
     */
    public double getPhi() {
        return phi;
    }

    /**
     * @param thePhi
     *            the phi to set
     */
    public void setPhi(final double thePhi) {
        this.phi = thePhi;
    }

    /**
     * @return the theta
     */
    public double getTheta() {
        return theta;
    }

    /**
     * @param theTheta
     *            the theta to set
     */
    public void setTheta(final double theTheta) {
        this.theta = theTheta;
    }

    /**
     * @return the q
     */
    public double getQ() {
        return q;
    }

    /**
     * @param theQ
     *            the q to set
     */
    public void setQ(final double theQ) {
        this.q = theQ;
    }

    /**
     * @return The pucker value.
     */
    public String getPucker() {
        return pucker;
    }

    /**
     * @param pucker
     *            The pucker to set.
     */
    public void setPucker(String pucker) {
        this.pucker = pucker;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof CremerPopleCoordinates)) {
            return false;
        }
        CremerPopleCoordinates rhs = (CremerPopleCoordinates) object;
        return new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.theta, rhs.theta).append(this.q, rhs.q)
                .append(this.pucker, rhs.pucker).append(this.phi, rhs.phi)
                .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-518709381, 532456673)
                .appendSuper(super.hashCode()).append(this.theta)
                .append(this.q).append(this.pucker).append(this.phi)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("theta", this.theta)
                .append("q", this.q).append("phi", this.phi)
                .append("pucker", this.pucker).toString();
    }
}
