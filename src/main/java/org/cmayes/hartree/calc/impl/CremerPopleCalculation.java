package org.cmayes.hartree.calc.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.model.Atom;
import com.cmayes.common.util.ChemUtils;

public class CremerPopleCalculation implements Calculation {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object calculate(Object rawInput) {
        if (rawInput instanceof CpCalculationSnapshot) {
            final CpCalculationSnapshot cpSnap = new CpCalculationSnapshot(
                    (CpCalculationSnapshot) rawInput);
            final List<Atom> gRing = ((CpCalculationSnapshot) rawInput)
                    .getGlucoseRing();
            if (gRing == null) {
                logger.warn(String.format(
                        "No glucose ring for CP calculation: '%s'", rawInput));
                return cpSnap;
            }
            cpSnap.setCpCoords(findCoords(gRing));
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * @param gRing
     * @return
     */
    private CremerPopleCoordinates findCoords(List<Atom> gRing) {
        List<Vector3D> ringVecs = new ArrayList<Vector3D>();
        for (Atom atom : gRing) {
            ringVecs.add(ChemUtils.vectorForAtom(atom));
        }
        final List<Vector3D> cenVecs = center(ringVecs);

        Vector3D r1a = null;
        Vector3D r2a = null;

        int i = 0;
        for (Vector3D cenVec : cenVecs) {
            final Vector3D sinVec = new Vector3D(Math.sin(2 * Math.PI * i / 6),
                    cenVec);
            r1a = r1a == null ? sinVec : r1a.add(sinVec);
            final Vector3D cosVec = new Vector3D(Math.cos(2 * Math.PI * i / 6),
                    cenVec);
            r2a = r2a == null ? cosVec : r2a.add(cosVec);
            i++;
        }

        Vector3D n = r1a.crossProduct(r2a);

        n = new Vector3D(1 / n.getNorm(), n);

        List<Double> z = new ArrayList<Double>();
        for (Vector3D cenVec : cenVecs) {
            z.add(Vector3D.dotProduct(cenVec, n));
        }

        double q2cosphi = 0;
        double q2sinphi = 0;
        double q3 = 0;
        double bigQ = 0;
        final double sqrt2 = Math.sqrt(2);
        final double invSqrt6 = Math.sqrt(1.0 / 6.0);

        int j = 0;
        for (Double zVal : z) {
            q2cosphi += zVal * Math.cos(2.0 * Math.PI * 2.0 * j / 6);
            q2sinphi -= zVal * Math.sin(2.0 * Math.PI * 2.0 * j / 6);
            q3 += zVal * Math.cos(j * Math.PI);
            bigQ += zVal * zVal;
            j++;
        }

        q2cosphi = sqrt2 * invSqrt6 * q2cosphi;
        q2sinphi = sqrt2 * invSqrt6 * q2sinphi;
        q3 = invSqrt6 * q3;
        final double q2 = Math.sqrt(q2cosphi * q2cosphi + q2sinphi * q2sinphi);
        bigQ = Math.sqrt(bigQ);

        double phi;
        if (q2cosphi > 0) {
            if (q2sinphi > 0) {
                phi = Math.toDegrees(Math.atan(q2sinphi / q2cosphi));
            } else {
                phi = 360 - Math.abs(Math.toDegrees(Math.atan(q2sinphi
                        / q2cosphi)));
            }
        } else {
            if (q2sinphi > 0) {
                phi = 180 - Math.abs(Math.toDegrees(Math.atan(q2sinphi
                        / q2cosphi)));
            } else {
                phi = 180 + Math.abs(Math.toDegrees(Math.atan(q2sinphi
                        / q2cosphi)));
            }
        }
        double theta = Math.toDegrees(Math.atan(q2 / q3));

        if (q3 > 0) {
            if (q2 > 0) {
                theta = Math.toDegrees(Math.atan(q2 / q3));
            } else {
                theta = 360 - Math.abs(Math.toDegrees(Math.atan(q2 / q3)));
            }
        } else {
            if (q2 > 0.) {
                theta = 180. - Math.abs(Math.toDegrees(Math.atan(q2 / q3)));
            } else {
                theta = 180. + Math.abs(Math.toDegrees(Math.atan(q2 / q3)));
            }
        }
        return new CremerPopleCoordinates(phi, theta, bigQ);
    }

    private List<Vector3D> center(List<Vector3D> ringVecs) {
        Vector3D center = new Vector3D(0, 0, 0);

        for (Vector3D ringVec : ringVecs) {
            center = center.add(ringVec);
        }
        final int rSize = ringVecs.size();
        center = new Vector3D(center.getX() / rSize, center.getY() / rSize,
                center.getZ() / rSize);

        final List<Vector3D> centeredVecs = new ArrayList<Vector3D>();
        for (Vector3D ringVec : ringVecs) {
            centeredVecs.add(ringVec.subtract(center));
        }
        return centeredVecs;
    }
}
