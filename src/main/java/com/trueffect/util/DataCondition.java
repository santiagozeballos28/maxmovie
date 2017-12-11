package com.trueffect.util;
import com.trueffect.util.ModelObject;
import com.trueffect.util.ModelObject;
import java.sql.Connection;

/**
 * @author Santiago
 */
public interface DataCondition {
   
     abstract boolean complyCondition(int id , ModelObject resource,   Connection connection)throws Exception;
    
}
