package org.cmayes.hartree.disp.db;

import org.cmayes.hartree.model.def.DefaultCalculationCategory;
import org.skife.jdbi.v2.BeanMapper;

/**
 * Simple type extension for a bean mapper for
 * {@link org.cmayes.hartree.model.def.DefaultCalculationCategory}.
 * 
 * @author cmayes
 */
public class CalculationCategoryMapper extends
        BeanMapper<DefaultCalculationCategory> {
    /**
     * Sets the bean class.
     */
    public CalculationCategoryMapper() {
        super(DefaultCalculationCategory.class);
    }
}
