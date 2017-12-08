package com.trueffect.validation;
import com.trueffect.util.OperationUpdate;
import com.trueffect.response.ErrorResponse;
import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.util.ModelObject;
/**
 * @author santiago.mamani
 */
public class RenterUserUpdateByManager implements OperationUpdate{

    @Override
    public boolean complyData(ModelObject objectActual, ModelObject objectNew) throws Exception {
        boolean res=false;
        Person personActual = (Person)objectActual;
        Person personNew = (Person)objectNew;
        
        String typeIdentifierActual=personActual.getTypeIdentifier();
        String typeIdentifierNew=personNew.getTypeIdentifier();
        
        String identifierActual = personActual.getIdentifier();
        String identifierNew = personNew.getIdentifier();
               
        if(!typeIdentifierActual.equals(typeIdentifierNew)){
            if (!identifierActual.equals(identifierNew)) {
                res = true;
            }
         else{
           throw new ErrorResponse(460,Message.NOT_HAVE_PERMISSION_IDENTIFIER);
        }   
        }else{
           throw new ErrorResponse(460,Message.NOT_HAVE_PERMISSION_TYPE_IDENTIFIER);
        }
        return res;
    }  
}
