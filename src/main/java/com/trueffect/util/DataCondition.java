package com.trueffect.util;

/**
 * @author Santiago
 */
public interface DataCondition {

    abstract boolean complyCondition(ModelObject resource, ErrorContainer errorContainer) throws Exception;

}
