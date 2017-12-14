package com.trueffect.logica;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
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
            if (conditiondata.complyCondition(person, errorContainer)) {
                PersonValidationsDB.verifyIdentifierInDataBase(connection, person.getTypeIdentifier(), person.getIdentifier(), errorContainer);
                PersonValidationsDB.verifyNamesInDataBase(connection, person.getLastName(), person.getFirstName(), errorContainer);
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
            if (PersonValidationsDB.getPerson(connection, idPerson, errorContainer) != null) {
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

    public Person update(Person person,int idRenter, int idUserModify) throws Exception {
        Person personRes = null;
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
         Connection connection = DataBasePostgres.getConection();
        
        try {
            //Validation of data 
            Job job = JobCrud.getJobOf(connection,idUserModify);
             if (job != null) {
                 String nameJob = job.getNameJob();
                if (nameJob.equals("Administrator") || nameJob.equals("Manager")) {
                    RenterUserUpdate rentUserUpdate = new RenterUserUpdate();
                    rentUserUpdate.setIdUserModify(nameJob);
                    if (rentUserUpdate.complyCondition(person, errorContainer)) {
                        PersonValidationsDB.verifyNamesInDataBase(connection, person.getLastName(), person.getFirstName(), errorContainer);                
                        String setString = Generator.getStringSet(person);
                          System.out.println("SET " +setString);
                        personRes = PersonCrud.updateRenterUser(connection, idRenter, idUserModify,setString);
                        connection.commit();
                    }
                }
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
}
