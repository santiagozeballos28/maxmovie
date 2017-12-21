package com.trueffect.logica.person;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class PersonLogic {

    private HashMap<String, String> listData;

    public PersonLogic() {
        listData = new HashMap<String, String>();
    }

    public Either createPerson(int idUserWhoCreate, Person person, RenterUserCreate conditiondata) {
        Either eitherRes = new Either();
        ErrorContainer errorContainer = new ErrorContainer();
        //open conection 
        Connection connection = DataBasePostgres.getConection();
        try {
            //Validation of permission 
            eitherRes = checkUserPermission(connection, idUserWhoCreate);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            person.formatOfTheName();

            eitherRes = conditiondata.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = conditiondata.identifiersAreOfTheSameType(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidationsDB.veriryDataInDataBase(connection, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.insertRenterUser(connection, idUserWhoCreate, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationDataBase.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either deleteById(int idPerson, int idUserModify, String status) {
        Either eitherRes = new Either();
        Connection connection = DataBasePostgres.getConection();
        try {
            //Check if the user can modify
            eitherRes = checkUserPermission(connection, idUserModify);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            //Validation Status(Active, Inactive)
            eitherRes = verifyStatus(status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //check if the personr exists
            eitherRes = existPerson(connection, idPerson);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updateStatusPerson(connection, idPerson, idUserModify, status);
            OperationDataBase.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }

        return eitherRes;
    }

    private Either existPerson(Connection connection, int idPerson) {
        Either eitherRes = new Either();
        Person person = new Person();
        ArrayList<String> listError = new ArrayList<String>();
        try {
            Either eitherAux = PersonCrud.getPerson(connection, idPerson);
            person = (Person) eitherAux.getModelObject();
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }

        if (person.isEmpty()) {
            listData.clear();
            listData.put("{object}", "Person");
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.NOT_FOUND, listError);

        }
        return eitherRes;
    }

    public Person update(Person person, int idRenter, int idUserModify) throws Exception {
        Person personRes = new Person();
//        ErrorContainer errorContainer = new ErrorContainer();
//        //open conection 
//        Connection connection = DataBasePostgres.getConection();
//        try {
//            //Validation of data
//            checkUserPermission(connection, idUserModify);
//
//            existPerson(connection, idRenter);
//            verifyId(person, idRenter);
//            Job job = JobCrud.getJobOf(connection, idUserModify);
//            //update is a class to specifically validate the conditions to update a person
//            RenterUserUpdate rentUserUpdate = new RenterUserUpdate(job.getNameJob());
//            person.formatOfTheName();
//            rentUserUpdate.complyCondition(person);
//            PersonValidationsDB.verifyDataUpdate(connection, idRenter, person);
//            personRes = PersonCrud.updateRenterUser(connection, idRenter, idUserModify, person);
//            connection.commit();
//        } catch (ErrorResponse exception) {
//            errorContainer.addError(new ErrorResponse(exception.getCode(), exception.getMessage()));
//            OperationDataBase.connectionRollback(connection, errorContainer);
//        } finally {
//            OperationDataBase.connectionClose(connection, errorContainer);
//        }
//        if (!errorContainer.isEmpty()) {
//            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
//        }
        return personRes;
    }

    private Either checkUserPermission(Connection connection, int idUserModify) {
        Either eitherJob = JobCrud.getJobOf(connection, idUserModify);
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        if (eitherJob.haveModelObject()) {
            String nameJob = ((Job) eitherJob.getModelObject()).getNameJob();
            try {
                EmployeeWithPermissionModify employee = EmployeeWithPermissionModify.valueOf(nameJob);
                return new Either();
            } catch (Exception e) {
                listData.clear();
                listData.put("{typeData}", "Person");
                String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.FORBIDDEN, listError);
            }
        } else {
            listError.add(Message.NOT_FOUND_USER_MODIFY);
            return new Either(CodeStatus.BAD_REQUEST, listError);

        }

    }

    private void verifyId(Person person, int idRenter) throws Exception {
        ArrayList<String> listError = new ArrayList<String>();
        if (person.getId() != 0) {
            if (person.getId() != idRenter) {
                throw new ErrorResponse(CodeStatus.CONFLICT, Message.CONFLCT_ID);
            }
        }
    }

    private Either verifyStatus(String status) {
        ArrayList<String> listError = new ArrayList<String>();
        try {
            StatusPerson statusPerson = StatusPerson.valueOf(status);
            return new Either();
        } catch (Exception e) {
            listData.clear();
            listData.put("{typeData}", "Status");
            listData.put("{data}", status);
            listData.put("{valid}", Message.VALID_STATUS);
            String errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);

        }
    }
}
