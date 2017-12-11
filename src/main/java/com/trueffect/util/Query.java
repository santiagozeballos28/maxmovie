
package com.trueffect.util;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public interface Query {
     abstract boolean executeQuey(int id , ModelObject resource)throws Exception;
    
}
