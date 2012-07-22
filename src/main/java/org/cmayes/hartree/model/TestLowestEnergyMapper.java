package org.cmayes.hartree.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.cmayes.common.model.Atom;
import com.cmayes.common.model.impl.DefaultAtom;

/**
 * Tests for {@link LowestEnergyMapper}.
 * 
 * @author cmayes
 */
public class TestLowestEnergyMapper {

    /**
     * Tests that the lowest-energy atom set is returned.
     */
    @Test
    public void testLowest() {
        final LowestEnergyMapper mapper = new LowestEnergyMapper();
        final Atom a1 = new DefaultAtom();
        a1.setId(1);
        mapper.add(222, Arrays.asList(a1));
        final Atom a2 = new DefaultAtom();
        a2.setId(2);
        mapper.add(777, Arrays.asList(a2));
        final Atom a3 = new DefaultAtom();
        a3.setId(3);
        mapper.add(1, Arrays.asList(a3));
        assertEquals(Arrays.asList(a3), mapper.getLowestEnergy());
    }

    /**
     * Tests that the lowest-energy atom set with unordered atoms set in the
     * proper order is returned.
     */
    @Test
    public void testLowestUnorderedAtoms() {
        final LowestEnergyMapper mapper = new LowestEnergyMapper();
        final Atom a1 = new DefaultAtom();
        a1.setId(1);
        final Atom a2 = new DefaultAtom();
        a2.setId(2);
        mapper.add(3, Arrays.asList(a2, a1));
        final Atom a3 = new DefaultAtom();
        a3.setId(3);
        mapper.add(99, Arrays.asList(a3));
        assertEquals(Arrays.asList(a1, a2), mapper.getLowestEnergy());
    }

    /**
     * Tests exception for zero items.
     */
    @Test(expected = IllegalStateException.class)
    public void testZeroItems() {
        final LowestEnergyMapper mapper = new LowestEnergyMapper();
        mapper.getLowestEnergy();
    }

}
