package com.trueffect.util;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author santiago.mamani
 */
public class ModelObject {

    @JsonIgnore
    public boolean isEmpty() {
        return true;
    }
}
