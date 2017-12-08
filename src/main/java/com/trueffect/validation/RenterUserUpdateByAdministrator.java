package com.trueffect.validation;
import com.trueffect.util.OperationUpdate;
import com.trueffect.util.ModelObject;
/*
 * @author santiago.mamani
 */
public class RenterUserUpdateByAdministrator implements OperationUpdate{

    @Override
    public boolean complyData(ModelObject object1, ModelObject object2) throws Exception {
        return true;
    }
    
}
