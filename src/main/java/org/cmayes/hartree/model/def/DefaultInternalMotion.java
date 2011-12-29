package org.cmayes.hartree.model.def;

import java.util.ArrayList;
import java.util.List;

import org.cmayes.hartree.model.InternalMotion;

import com.cmayes.common.chem.InternalMotionType;

/**
 * Default implementation of an internal motion.
 * 
 * @author cmayes
 */
public class DefaultInternalMotion implements InternalMotion {
    private InternalMotionType type;
    private String name;
    private List<Integer> members = new ArrayList<Integer>();
    private double value;
    private double weight;

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
    public double getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setValue(double)
     */
    @Override
    public void setValue(final double val) {
        this.value = val;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#getWeight()
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.cmayes.hartree.model.InternalMotion#setWeight(double)
     */
    @Override
    public void setWeight(final double wt) {
        this.weight = wt;
    }
}
