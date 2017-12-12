package com.trueffect.util;
import com.trueffect.util.ModelObject;
import com.trueffect.util.ModelObject;
import java.sql.Connection;

/**
 * @author Santiago
 */
public interface DataCondition {
   
     abstract boolean complyCondition(ModelObject resource)throws Exception;
    
}
