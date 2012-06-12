package org.cmayes.hartree.calc.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cmayes.hartree.calc.Calculation;
import org.cmayes.hartree.model.BaseResult;
import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.AtomicElement;
import com.cmayes.common.model.Atom;
import com.cmayes.common.util.ChemUtils;

/**
 * Sets a glucose ring on a returned copy of the value. Sets null if none are
 * found.
 * 
 * @author cmayes
 */
public class GlucoseRingCalculation implements Calculation {
    private static final int DEF_RING_SIZE = 6;
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int ringSize = DEF_RING_SIZE;

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.calc.Calculation#calculate(java.lang.Object)
     */
    @Override
    public Object calculate(final Object rawInput) {
        if (rawInput instanceof BaseResult) {
            final CpCalculationSnapshot cpSnap = new CpCalculationSnapshot(
                    (BaseResult) rawInput);
            final List<Atom> atoms = ((BaseResult) rawInput).getAtoms();
            if (atoms == null) {
                logger.warn(String
                        .format("No atoms for glucose ring calculation: '%s'",
                                rawInput));
                return cpSnap;
            }
            cpSnap.setGlucoseRing(findGlucoseRing(atoms));
            return cpSnap;
        } else {
            throw new IllegalArgumentException(String.format(
                    "Unhandled class '%s'", rawInput.getClass()));
        }
    }

    /**
     * Returns a glucose ring, or null if none are found.
     * 
     * @param atoms
     *            The atoms to evaluate.
     * @return A glucose ring of the configured size, or null.
     */
    private List<Atom> findGlucoseRing(final List<Atom> atoms) {
        final List<Atom> oxys = new ArrayList<Atom>();
        final List<Atom> carbs = new ArrayList<Atom>();
        for (Atom atom : atoms) {
            if (AtomicElement.CARBON.equals(atom.getType())) {
                carbs.add(atom);
            } else if (AtomicElement.OXYGEN.equals(atom.getType())) {
                oxys.add(atom);
            }
        }

        for (Atom oxy : oxys) {
            if (hasTwoCarbs(oxy, carbs)) {
                final List<Atom> ring = new ArrayList<Atom>();
                ring.add(oxy);
                final List<Atom> carbonCopy = new ArrayList<Atom>(carbs);
                boolean isFound = true;
                while (isFound) {
                    isFound = findNextCarbon(carbonCopy, ring);
                }
                if (ring.size() != ringSize) {
                    logger.debug(String.format(
                            "Ring size '%d' instead of '%d'. Rejecting.",
                            ring.size(), ringSize));
                    continue;
                }
                if (!ChemUtils.hasBond(ring.get(ring.size() - 1), oxy)) {
                    logger.debug("Last carbon is not bonded to oxygen.  Rejecting.");
                    continue;
                }
                return ring;
            }
        }
        logger.warn(String.format("No glucose ring of size '%d' found.",
                ringSize));
        return null;
    }

    /**
     * Returns whether this oxygen has bonds with two carbons.
     * 
     * @param oxy
     *            The candidate oxygen.
     * @param carbs
     *            The carbons to check.
     * @return whether this oxygen has bonds with two carbons.
     */
    private boolean hasTwoCarbs(final Atom oxy, final List<Atom> carbs) {
        int carbCount = 0;
        for (Atom carb : carbs) {
            if (ChemUtils.hasBond(oxy, carb)) {
                carbCount++;
            }
        }
        return carbCount == 2;
    }

    /**
     * Tries to find an adjoining carbon for the last element in the ring.
     * Returns whether successful.
     * 
     * @param carbonCopy
     *            The carbons to search. Note that the matching carbon is
     *            removed from this list.
     * @param ring
     *            The target ring.
     * @return Whether the method found a carbon with a bond to the last element
     *         in the ring.
     */
    private boolean findNextCarbon(final List<Atom> carbonCopy,
            final List<Atom> ring) {
        for (final Iterator<Atom> iterator = carbonCopy.iterator(); iterator
                .hasNext();) {
            final Atom curCarb = iterator.next();
            if (ChemUtils.hasBond(ring.get(ring.size() - 1), curCarb)) {
                ring.add(curCarb);
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * @return the ringSize
     */
    public int getRingSize() {
        return ringSize;
    }

    /**
     * @param size
     *            the ringSize to set
     */
    public void setRingSize(final int size) {
        this.ringSize = size;
    }

}
