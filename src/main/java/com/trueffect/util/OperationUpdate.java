package com.trueffect.util;
import com.trueffect.util.ModelObject;

/**
 * @author santiago.mamani
 */
public interface OperationUpdate {
    abstract boolean complyData(ModelObject object1,ModelObject object2)throws Exception;
}
