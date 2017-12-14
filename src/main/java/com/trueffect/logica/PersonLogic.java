package com.trueffect.logica;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ErrorContainer;
import com.trueffect.validation.RenterUserUpdate;
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
            conditiondata.complyCondition(person);
            PersonValidationsDB.veriryDataInDataBase(connection, person);
            personRes = PersonCrud.insertrenterUser(connection, id, person);
            connection.commit();
        } catch (ErrorResponse errorResponse) {
            errorContainer.addError(new ErrorResponse(errorResponse.getCode(), errorResponse.getMessage()));
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
            verifyPerson(connection, idPerson);
            res = PersonCrud.deleteById(connection, idPerson, idUserModify);
            connection.commit();
        } catch (ErrorResponse e) {
            errorContainer.addError(new ErrorResponse(e.getCode(), e.getMessage()));
            OperationDataBase.connectionRollback(connection, errorContainer);
        } finally {
            OperationDataBase.connectionClose(connection, errorContainer);
        }
        if (errorContainer.size() > 0) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return res;
    }

    private static void verifyPerson(Connection connection, int idPerson) throws Exception {
        Person person = PersonCrud.getPerson(connection, idPerson);
        if (person == null) {
            throw new ErrorResponse(CodeStatus.NOT_FOUND, Message.NOT_RESOURCE);
        }
    }

    public Person update(Person person, int idRenter, int idUserModify) throws Exception {
        Person personRes = null;
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        try {
            //Validation of data             
            verifyModifierUser(connection, idUserModify);
            Job job = JobCrud.getJobOf(connection, idUserModify);
            RenterUserUpdate rentUserUpdate = new RenterUserUpdate();
            rentUserUpdate.setIdUserModify(job.getNameJob());
            rentUserUpdate.complyCondition(person);
            PersonValidationsDB.verifyDataUpdate(connection, idRenter, person);
            String setString = Generator.getStringSet(person);
            personRes = PersonCrud.updateRenterUser(connection, idRenter, idUserModify, setString);
            connection.commit();
        } catch (ErrorResponse exception) {
            errorContainer.addError(new ErrorResponse(exception.getCode(), exception.getMessage()));
            OperationDataBase.connectionRollback(connection, errorContainer);
        } finally {
            OperationDataBase.connectionClose(connection, errorContainer);
        }
        if (errorContainer.size() > 0) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return personRes;
    }

    private boolean verifyModifierUser(Connection connection, int idUserModify) throws Exception {
        Job job = JobCrud.getJobOf(connection, idUserModify);
        if (job != null) {
            String nameJob = job.getNameJob();
            if (nameJob.equals("Administrator") || nameJob.equals("Manager")) {
                return true;

            } else {
                throw new ErrorResponse(CodeStatus.FORBIDDEN, Message.NOT_HAVE_PERMISSION_FOR_MODIFY);
            }
        } else {
            throw new ErrorResponse(CodeStatus.FORBIDDEN, Message.NOT_HAVE_PERMISSION_FOR_MODIFY);
        }
    }

}
