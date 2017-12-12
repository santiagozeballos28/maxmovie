package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ErrorContainer;
import java.sql.Connection;
/**
 * @author santiago.mamani
 */
public class OperationPerson {
    public static void verifyIdentifierInDataBase( Connection connection, String typeIdentifier, String identifier, ErrorContainer  errorContainer) throws Exception {
        Person person = (Person) PersonCrud.getPersonByTypeIdentifier( connection,typeIdentifier, identifier);
        if (person != null) {
          errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST,Message.DUPLICATE_IDENTIFIER));
        }
     }  
    public static void verifyNamesInDataBase( Connection connection, String lastName, String firstName,  ErrorContainer  errorContainer)throws Exception{
       Person person = (Person)PersonCrud.getPersonByName(connection, lastName, firstName);
       if(person!=null){
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST,Message.THE_NAMES_ALREADY_EXIST));
       }
    }
    
}
