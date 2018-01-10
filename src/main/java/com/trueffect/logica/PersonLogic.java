package com.trueffect.logica;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Crud;
import com.trueffect.tools.ConstantData.ObjectMovie;
import com.trueffect.tools.ConstantData.Status;
import com.trueffect.util.OperationString;
import com.trueffect.validation.PersonCreate;
import com.trueffect.validation.PersonValidation;
import com.trueffect.validation.PersonUpdate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 * @author santiago.mamani
 */
public class PersonLogic {

    private HashMap<String, String> listData;
    private PersonValidationsDB personValidationsDB;
    private Permission permission;

    public PersonLogic() {
        String renterUser = ObjectMovie.RennterUser.name();
        listData = new HashMap<String, String>();
        permission = new Permission();
        personValidationsDB = new PersonValidationsDB();
        permission.setNameObject(renterUser);
    }

    public Either createPerson(int idUserWhoCreate, Person person, PersonCreate conditiondata) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            
            String create = Crud.create.name();
            //Validation of permission 
            eitherRes = permission.checkUserPermission(connection, idUserWhoCreate, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(person);
            eitherRes = conditiondata.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.addApostrophe(person);
            eitherRes = personValidationsDB.veriryDataInDataBase(connection, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            String identifier = person.getIdentifier().toUpperCase();
            String typeIdentifier = person.getTypeIdentifier().toUpperCase();
            String genre = person.getGenre().toUpperCase();
            person.setIdentifier(identifier);
            person.setTypeIdentifier(typeIdentifier);
            person.setGenre(genre);
            eitherRes = PersonCrud.insertPerson(connection, idUserWhoCreate, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.getPersonByIdentifier(connection, person.getTypeIdentifier(), person.getIdentifier());
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either updateStatus(int idPerson, int idUserModify, String status) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String delete = Crud.delete.name();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserModify, delete);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if exist person
            eitherRes = getPerson(connection, idPerson, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation Status(Active, Inactive)          
            OperationModel operationModel = new OperationModel();
            eitherRes = operationModel.verifyStatus(connection, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updateStatusPerson(connection, idPerson, idUserModify, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.getPerson(connection, idPerson, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either update(Person person, int idRenter, int idUserModify) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String update = Crud.update.name();
            String statusActive = Status.Active.name();
            String renterUser = ObjectMovie.RennterUser.name();
            //Validation of data
            eitherRes = permission.checkUserPermission(connection, idUserModify, update);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationModel operationModel = new OperationModel();
            eitherRes = operationModel.verifyId(idRenter, person.getId(), renterUser);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getPerson(connection, idRenter, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = JobCrud.getJobOf(connection, idUserModify);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //update is a class to specifically validate the conditions to update a person
            PersonUpdate rentUserUpdate = new PersonUpdate(
                    ((Job) eitherRes.getFirstObject()).getNameJob(),
                    ConstantData.MIN_AGE_RENTER);
            OperationString.formatOfTheName(person);
            eitherRes = rentUserUpdate.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //add aphostrophe "'".
            OperationString.addApostrophe(person);
            eitherRes = personValidationsDB.verifyDataUpdate(connection, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            eitherRes = PersonCrud.updatePerson(connection, idUserModify, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.getPerson(connection, idRenter, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either getPerson(Connection connection, int idPerson, String status) {
        String renterUser = ObjectMovie.RennterUser.name();
        Either eitherRes = new Either();
        Person person = new Person();
        ArrayList<String> listError = new ArrayList<String>();
        try {
            eitherRes = PersonCrud.getPerson(connection, idPerson, status);
            person = (Person) eitherRes.getFirstObject();
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        if (person.isEmpty()) {
            listData.clear();
            listData.put(ConstantData.OBJECT, renterUser);
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.NOT_FOUND, listError);
        }
        return eitherRes;
    }

    public Either get(int idUserSearch, String typeId, String identifier, String lastName, String firstName, String genre) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = Crud.get.name();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            if (StringUtils.isNotBlank(lastName)) {
                lastName = OperationString.generateLastName(lastName.trim());
                lastName = OperationString.addApostrophe(lastName);
            }
            if (StringUtils.isNotBlank(firstName)) {
                firstName = OperationString.generateFirstName(firstName.trim());
                firstName = OperationString.addApostrophe(firstName);
            }
            eitherRes = PersonCrud.getPersonBy(connection, typeId, identifier, lastName, firstName, genre);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }
}
