package com.trueffect.logica;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ErrorContainer;
import java.sql.Connection;

/**
 * @author santiago.mamani
 */
public class PersonLogic {

    public Person createPerson(int id, Person person, DataCondition conditiondata) throws Exception {
        Person personRes = null;
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        try {
            //Validation of data 
            if (conditiondata.complyCondition(person, errorContainer)) {
                OperationPerson.verifyIdentifierInDataBase(connection, person.getTypeIdentifier(), person.getIdentifier(), errorContainer);
                OperationPerson.verifyNamesInDataBase(connection, person.getLastName(), person.getFirstName(), errorContainer);
                personRes = PersonCrud.insertrenterUser(connection, id, person);
                connection.commit();
            }

        } catch (Exception exception) {
            errorContainer.addError(new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
            OperationDataBase.connectionRollback(connection, errorContainer);
        } finally {
            OperationDataBase.connectionClose(connection, errorContainer);
        }
        if (errorContainer.size() > 0) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return personRes;
    }

    public Person deleteById(int idPerson, int idUserModify) throws Exception {
        Person res = null;
        ErrorContainer errorContainer = new ErrorContainer();
        Connection connection = DataBasePostgres.getConection();
        try {
            if (OperationPerson.getPerson(connection, idPerson, errorContainer) != null) {
                res = PersonCrud.deleteById(connection, idPerson, idUserModify);
                connection.commit();
            }
        } catch (Exception e) {
            errorContainer.addError(new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
            OperationDataBase.connectionRollback(connection, errorContainer);
        } finally {
            OperationDataBase.connectionClose(connection, errorContainer);
        }
        if (errorContainer.size() > 0) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return res;
    }
}
