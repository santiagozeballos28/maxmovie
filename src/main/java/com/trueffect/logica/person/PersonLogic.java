package com.trueffect.logica.person;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData.EmployeeWithPermissionModify;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ErrorContainer;
import com.trueffect.validation.RenterUserUpdate;
import java.sql.Connection;

/**
 * @author santiago.mamani
 */
public class PersonLogic {
    
    public Person createPerson(int id, Person person, DataCondition conditiondata) throws Exception {
        Person personRes = new Person();
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        System.out.println("ENTRO A create person:");
        try {
            //Validation of data 
            System.out.println("ENTRO a try:");
            person.formatOfTheName();
             System.out.println("acmbio en formato adecuado:");
            conditiondata.complyCondition(person);
            PersonValidationsDB.veriryDataInDataBase(connection, person);
            System.out.println("verify data base:");
            personRes = PersonCrud.insertRenterUser(connection, id, person);
            connection.commit();
        } catch (ErrorResponse errorResponse) {
            errorContainer.addError(new ErrorResponse(errorResponse.getCode(), errorResponse.getMessage()));
            OperationDataBase.connectionRollback(connection, errorContainer);
        } finally {
            OperationDataBase.connectionClose(connection, errorContainer);
        }
        if (!errorContainer.isEmpty()) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return personRes;
    }
    
    public Person deleteById(int idPerson, int idUserModify) throws Exception {
        Person res = new Person();
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
        if (!errorContainer.isEmpty()) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return res;
    }
    
    private void verifyPerson(Connection connection, int idPerson) throws Exception {
        Person person = PersonCrud.getPerson(connection, idPerson);
        if (person.isEmpty()) {
            throw new ErrorResponse(CodeStatus.NOT_FOUND, Message.NOT_RESOURCE);
        }
    }
    
    public Person update(Person person, int idRenter, int idUserModify) throws Exception {
        Person personRes = new Person();
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        try {
            //Validation of data
            verifyPerson(connection, idRenter);
            verifyId(person, idRenter);
            verifyModifierUser(connection, idUserModify);
            Job job = JobCrud.getJobOf(connection, idUserModify);
            //update is a class to specifically validate the conditions to update a person
            RenterUserUpdate rentUserUpdate = new RenterUserUpdate(job.getNameJob());
            rentUserUpdate.complyCondition(person);
            PersonValidationsDB.verifyDataUpdate(connection, idRenter, person);
            personRes = PersonCrud.updateRenterUser(connection, idRenter, idUserModify, person);
            connection.commit();
        } catch (ErrorResponse exception) {
            errorContainer.addError(new ErrorResponse(exception.getCode(), exception.getMessage()));
            OperationDataBase.connectionRollback(connection, errorContainer);
        } finally {
            OperationDataBase.connectionClose(connection, errorContainer);
        }
        if (!errorContainer.isEmpty()) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
        return personRes;
    }
    
    private void verifyModifierUser(Connection connection, int idUserModify) throws Exception {
        Job job = JobCrud.getJobOf(connection, idUserModify);
        if (job != null) {
            String nameJob = job.getNameJob();
            try {
                EmployeeWithPermissionModify employee = EmployeeWithPermissionModify.valueOf(nameJob);
            } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.FORBIDDEN, Message.NOT_HAVE_PERMISSION_FOR_MODIFY);
            }
        } else {
            throw new ErrorResponse(CodeStatus.FORBIDDEN, Message.NOT_HAVE_PERMISSION_FOR_MODIFY);
        }
    }
    
    private void verifyId(Person person, int idRenter) throws Exception {        
        if (person.getId() != 0) {
            if (person.getId() != idRenter) {
                throw new ErrorResponse(CodeStatus.CONFLICT, Message.CONFLCT_ID);
            }
        }
    }
}
