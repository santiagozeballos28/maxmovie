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
import com.trueffect.tools.ConstantData.StatusPerson;
import com.trueffect.util.ErrorContainer;
import com.trueffect.util.OperationString;
import com.trueffect.validation.RenterUserCreate;
import com.trueffect.validation.RenterUserUpdate;
import java.sql.Connection;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class PersonLogic {

    private HashMap<String, String> listData;

    public PersonLogic() {
        listData = new HashMap<String, String>();
    }

    public Person createPerson(int idUserWhoCreate, Person person, RenterUserCreate conditiondata) throws Exception {
        Person personRes = new Person();
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        try {
            //Validation of data 
            checkUserPermission(connection, idUserWhoCreate);
            person.formatOfTheName();
            conditiondata.complyCondition(person);
            conditiondata.identifiersAreOfTheSameType(person);
            PersonValidationsDB.veriryDataInDataBase(connection, person);
            personRes = PersonCrud.insertRenterUser(connection, idUserWhoCreate, person);
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

    public Person deleteById(int idPerson, int idUserModify, String status) throws Exception {
        Person res = new Person();
        ErrorContainer errorContainer = new ErrorContainer();
        Connection connection = DataBasePostgres.getConection();
        try {
            checkUserPermission(connection, idUserModify);
            verifyStatus(status);
            existPerson(connection, idPerson);
            res = PersonCrud.updateStatusPerson(connection, idPerson, idUserModify, status);
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

    private void existPerson(Connection connection, int idPerson) throws Exception {
        Person person = PersonCrud.getPerson(connection, idPerson);
        if (person.isEmpty()) {
            listData.clear();
            listData.put("{object}", "Person");
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
            throw new ErrorResponse(CodeStatus.NOT_FOUND, errorMgs);
        }
    }

    public Person update(Person person, int idRenter, int idUserModify) throws Exception {
        Person personRes = new Person();
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        try {
            //Validation of data
            checkUserPermission(connection, idUserModify);
            existPerson(connection, idRenter);
            verifyId(person, idRenter);
            Job job = JobCrud.getJobOf(connection, idUserModify);
            //update is a class to specifically validate the conditions to update a person
            RenterUserUpdate rentUserUpdate = new RenterUserUpdate(job.getNameJob());
            person.formatOfTheName();
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

    private void checkUserPermission(Connection connection, int idUserModify) throws Exception {
        Job job = JobCrud.getJobOf(connection, idUserModify);
        if (job != null) {
            String nameJob = job.getNameJob();
            try {
                EmployeeWithPermissionModify employee = EmployeeWithPermissionModify.valueOf(nameJob);
            } catch (Exception e) {
                listData.clear();
                listData.put("{typeData}", "Person");
                String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
                throw new ErrorResponse(CodeStatus.FORBIDDEN, errorMgs);
            }
        } else {
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_FOUND_USER_MODIFY);
        }
    }

    private void verifyId(Person person, int idRenter) throws Exception {
        if (person.getId() != 0) {
            if (person.getId() != idRenter) {
                throw new ErrorResponse(CodeStatus.CONFLICT, Message.CONFLCT_ID);
            }
        }
    }

    private void verifyStatus(String status) throws Exception {
        try {
            StatusPerson statusPerson = StatusPerson.valueOf(status);
        } catch (Exception e) {
            listData.clear();
            listData.put("{typeData}", "Status");
            listData.put("{data}", status);
            listData.put("{valid}", Message.VALID_STATUS);
            String errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs);
        }
    }
}
