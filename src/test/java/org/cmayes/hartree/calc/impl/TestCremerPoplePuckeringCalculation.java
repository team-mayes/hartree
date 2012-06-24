package org.cmayes.hartree.calc.impl;

import static org.junit.Assert.assertEquals;

import org.cmayes.hartree.model.def.CpCalculationSnapshot;
import org.cmayes.hartree.model.def.CremerPopleCoordinates;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCremerPoplePuckeringCalculation {
    private static final String FOURC1 = "4c1";
    private static final CremerPoplePuckeringCalculation FILE_CALC = new CremerPoplePuckeringCalculation();
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testZeroGlob() {
        CpCalculationSnapshot snap = new CpCalculationSnapshot();
        CremerPopleCoordinates coords = new CremerPopleCoordinates(0, 0, 1);
        snap.setCpCoords(coords);
        FILE_CALC.calculate(snap);
        assertEquals(FOURC1, coords.getPucker());
    }
    
    @Test
    public void test360Glob() {
        CpCalculationSnapshot snap = new CpCalculationSnapshot();
        CremerPopleCoordinates coords = new CremerPopleCoordinates(0, 356, 1);
        snap.setCpCoords(coords);
        FILE_CALC.calculate(snap);
        assertEquals(FOURC1, coords.getPucker());
    }
}
