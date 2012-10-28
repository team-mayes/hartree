package org.cmayes.hartree.disp.db;

import org.cmayes.hartree.model.def.DefaultBaseResult;
import org.skife.jdbi.v2.BeanMapper;

/**
 * Simple type extension for a bean mapper for
 * {@link org.cmayes.hartree.model.def.DefaultBaseResult}.
 * 
 * @author cmayes
 */
public class BaseResultMapper extends BeanMapper<DefaultBaseResult> {
    /**
     * Sets the bean class.
     */
    public BaseResultMapper() {
        super(DefaultBaseResult.class);
    }
}
