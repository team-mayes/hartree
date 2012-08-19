package org.cmayes.hartree.calc.impl;

import static org.junit.Assert.assertEquals;

import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
import org.junit.Test;

/**
 * Tests for {@link CremerPoplePuckeringCalculation}.
 * 
 * @author cmayes
 */
public class TestCremerPoplePuckeringCalculation {
    private static final String FOURC1 = "4c1";
    private static final CremerPoplePuckeringCalculation FILE_CALC = new CremerPoplePuckeringCalculation();

    /**
     * Test for getting the zero-degree puckering.
     */
    @Test
    public void testZeroGlob() {
        final CpCalculationSnapshot snap = new CpCalculationSnapshot();
        final CremerPopleCoordinates coords = new CremerPopleCoordinates(0, 0,
                1);
        snap.setCpCoords(coords);
        FILE_CALC.calculate(snap);
        assertEquals(FOURC1, coords.getPucker());
    }

    /**
     * Test for getting the 360-degree puckering (with rounding).
     */
    @Test
    public void test360Glob() {
        final CpCalculationSnapshot snap = new CpCalculationSnapshot();
        final CremerPopleCoordinates coords = new CremerPopleCoordinates(0,
                356, 1);
        snap.setCpCoords(coords);
        FILE_CALC.calculate(snap);
        assertEquals(FOURC1, coords.getPucker());
    }
}
