package com.trueffect.logic;

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
    private PersonCrud personCrud;
    private JobCrud jobCrud;

    public PersonLogic() {
        String renterUser = ObjectMovie.RennterUser.name();
        listData = new HashMap<String, String>();
        permission = new Permission();
        personValidationsDB = new PersonValidationsDB();
        permission.setNameObject(renterUser);
        personCrud = new PersonCrud();
        jobCrud = new JobCrud();
    }

    public Either createPerson(long idUserCreate, Person person, PersonCreate conditiondata) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();

            String create = Crud.create.name();
            String status = Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserCreate, status, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation of permission 
            eitherRes = permission.checkUserPermission(connection, idUserCreate, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = conditiondata.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = personValidationsDB.veriryDataInDataBase(connection, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(person);
            //add apostrophe(') for the names that have this symbol, 
            //this so that there are no problems when inserting in the database
            OperationString.addApostrophe(person);
            String identifier = person.getIdentifier().toUpperCase();
            String typeIdentifier = person.getTypeIdentifier().toUpperCase();
            String genre = person.getGenre().toUpperCase();
            person.setIdentifier(identifier);
            person.setTypeIdentifier(typeIdentifier);
            person.setGenre(genre);
            eitherRes = personCrud.insertPerson(connection, idUserCreate, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = personCrud.getPersonByIdentifier(connection, person.getTypeIdentifier(), person.getIdentifier());
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

    public Either updateStatus(long idPerson, long idUserModify, String status) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String delete = Crud.delete.name();
            String statusActive = Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserModify, statusActive, delete);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
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
            eitherRes = personCrud.updateStatusPerson(connection, idPerson, idUserModify, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = personCrud.getRenterUser(connection, idPerson, null);
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

    public Either update(Person person, long idRenter, long idUserModify) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String update = Crud.update.name();
            String statusActive = Status.Active.name();
            String renterUser = ObjectMovie.RennterUser.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserModify, statusActive, update);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
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
            eitherRes = jobCrud.getJobOf(connection, idUserModify);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //update is a class to specifically validate the conditions to update a person
            PersonUpdate rentUserUpdate = new PersonUpdate(
                    ((Job) eitherRes.getFirstObject()).getNameJob(),
                    ConstantData.MIN_AGE_RENTER);

            eitherRes = rentUserUpdate.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //add apostrophe(') for the names that have this symbol, 
            //this so that there are no problems when inserting in the database

            eitherRes = personValidationsDB.verifyDataUpdate(connection, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(person);
            //add apostrophe(') for the names that have this symbol, 
            //this so that there are no problems when inserting in the database
            OperationString.addApostrophe(person);
            String typeIdentifier = person.getTypeIdentifier();
            if (StringUtils.isNotBlank(typeIdentifier)) {
                person.setTypeIdentifier(typeIdentifier.toUpperCase());
            }
            String identifier = person.getIdentifier();
            if (StringUtils.isNotBlank(identifier)) {
                person.setIdentifier(identifier.toUpperCase());
            }
            String genre = person.getGenre();
            if (StringUtils.isNotBlank(genre)) {
                person.setGenre(genre.toUpperCase());
            }
            eitherRes = personCrud.updatePerson(connection, idUserModify, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = personCrud.getRenterUser(connection, idRenter, statusActive);
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

    private Either getPerson(Connection connection, long idPerson, String status) {
        String renterUser = ObjectMovie.RennterUser.name();
        Either eitherRes = new Either();
        Person person = new Person();
        ArrayList<String> listError = new ArrayList<String>();
        try {
            eitherRes = personCrud.getRenterUser(connection, idPerson, status);
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

    public Either get(long idUserSearch, String typeId, String identifier, String lastName, String firstName, String genre) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = Crud.get.name();
            String status = Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserSearch, status, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            if (StringUtils.isNotBlank(lastName)) {
                lastName = OperationString.generateName(lastName.trim());
                lastName = OperationString.addApostrophe(lastName);
            }
            if (StringUtils.isNotBlank(firstName)) {
                firstName = OperationString.generateName(firstName.trim());
                firstName = OperationString.addApostrophe(firstName);
            }
            eitherRes = personCrud.getPersonBy(connection, typeId, identifier, lastName, firstName, genre);
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
