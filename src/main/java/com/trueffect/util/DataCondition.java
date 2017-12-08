package com.trueffect.util;
import com.trueffect.util.ModelObject;
import com.trueffect.util.ModelObject;

/**
 * @author Santiago
 */
public interface DataCondition {
   
     abstract boolean complyCondition(int id , ModelObject resource)throws Exception;
    
}
