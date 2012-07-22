package org.cmayes.hartree.model;

import static com.cmayes.common.exception.ExceptionUtils.asNotNullCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmayes.common.chem.AtomIdComparator;
import com.cmayes.common.model.Atom;

/**
 * Contains a list of atoms paired with their energies.
 * 
 * @author cmayes
 */
public class LowestEnergyMapper {
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<EnergyAtoms> energyAtoms = new ArrayList<LowestEnergyMapper.EnergyAtoms>();

    /**
     * Adds the energy and atoms to the list.
     * 
     * @param energy
     *            The energy to use.
     * @param atoms
     *            The atoms to use.
     */
    public void add(final double energy, final List<Atom> atoms) {
        final List<Atom> localAtoms = new ArrayList<Atom>(asNotNullCollection(
                atoms, "Atoms are or contain nulls"));
        Collections.sort(localAtoms, new AtomIdComparator());
        energyAtoms.add(new EnergyAtoms(energy, localAtoms));
    }

    /**
     * @return The set of atoms with the lowest energy.
     */
    public List<Atom> getLowestEnergy() {
        if (energyAtoms.size() == 0) {
            throw new IllegalStateException("Atoms list is empty");
        }

        EnergyAtoms lowest = null;
        for (EnergyAtoms curAtom : energyAtoms) {
            if (lowest == null) {
                lowest = curAtom;
                continue;
            }
            if (curAtom.getElecEn() < lowest.getElecEn()) {
                lowest = curAtom;
            }
        }
        logger.debug("Lowest energy: " + lowest.getElecEn());
        return lowest.getAtoms();
    }

    /**
     * Contains a list of atoms paired with their energies.
     * 
     * @author cmayes
     */
    public static class EnergyAtoms {
        private final double elecEn;
        private final List<Atom> atoms;

        /**
         * Creates a pairing.
         * 
         * @param en
         *            The system's energy.
         * @param atomList
         *            The atoms in the system.
         */
        public EnergyAtoms(final double en, final List<Atom> atomList) {
            this.elecEn = en;
            this.atoms = atomList;
        }

        /**
         * @return the elecEn
         */
        public double getElecEn() {
            return elecEn;
        }

        /**
         * @return the atoms
         */
        public List<Atom> getAtoms() {
            return atoms;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(final Object)
     */
    public boolean equals(final Object object) {
        if (!(object instanceof LowestEnergyMapper)) {
            return false;
        }
        final LowestEnergyMapper rhs = (LowestEnergyMapper) object;
        return new EqualsBuilder().append(this.energyAtoms, rhs.energyAtoms)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-910702087, -1866450035).append(
                this.energyAtoms).toHashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("lowestEnergy",
                this.getLowestEnergy()).toString();
    }
}
