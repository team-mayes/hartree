package org.cmayes.hartree.model.def;

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
}
