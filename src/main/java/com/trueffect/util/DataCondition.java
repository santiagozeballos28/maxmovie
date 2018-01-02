package com.trueffect.util;

import com.trueffect.response.Either;

/**
 * @author Santiago
 */
public interface DataCondition {

    abstract Either complyCondition(ModelObject resource);

}
