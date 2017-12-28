package com.trueffect.logica.person;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.logica.permission.Permission;
import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.model.PersonDetail;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Genre;
import com.trueffect.tools.ConstantData.StatusPerson;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.util.ModelObject;
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
    private Permission permission;

    public PersonLogic() {
        listData = new HashMap<String, String>();
        permission = new Permission();
    }

    public Either createPerson(int idUserWhoCreate, Person person, PersonCreate conditiondata) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of permission 
            eitherRes = permission.checkUserPermission(connection, idUserWhoCreate);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(person);
            eitherRes = conditiondata.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidationsDB.veriryDataInDataBase(connection, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.insertPerson(connection, idUserWhoCreate, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.getPersonByIdentifier(connection, person.getTypeIdentifier(), person.getIdentifier());
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

    public Either updateStatus(int idPerson, int idUserModify, String status) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserModify);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            //Validation Status(Active, Inactive)
            eitherRes = verifyStatus(connection, idPerson, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updateStatusPerson(connection, idPerson, idUserModify, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.getPerson(connection, idPerson, "");
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

    public Either update(Person person, int idRenter, int idUserModify) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of data
            eitherRes = permission.checkUserPermission(connection, idUserModify);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidation.verifyId(person, idRenter);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getPerson(connection, idRenter, "Active");
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
                    ConstantData.MINIMUM_AGE_RENTER);
            OperationString.formatOfTheName(person);
            eitherRes = rentUserUpdate.complyCondition(person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidationsDB.verifyDataUpdate(connection, idRenter, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updatePerson(connection, idRenter, idUserModify, person);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.getPerson(connection, idRenter, "Active");
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationDataBase.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either getPerson(Connection connection, int idPerson, String status) {
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
            listData.put("{object}", "Person");
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.NOT_FOUND, listError);
        }
        return eitherRes;
    }

    private Either verifyStatus(Connection connection, int idRenter, String status) {
        ArrayList<String> listError = new ArrayList<String>();
        try {
            StatusPerson statusPerson = StatusPerson.valueOf(status);
            switch (statusPerson) {
                case Active:
                    return getPerson(connection, idRenter, "");
                case Inactive:
                    return getPerson(connection, idRenter, "Active");

            }
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

    public Either get(int idUserSearch, String typeId, String identifier, String lastName, String firstName, String genre) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            if (StringUtils.isNotBlank(lastName)) {
                lastName = OperationString.generateLastName(lastName);
            }

            if (StringUtils.isNotBlank(firstName)) {
                firstName = OperationString.generateFirstName(firstName);
            }
            eitherRes = PersonCrud.getPersonBy(connection, typeId, identifier, lastName, firstName, genre);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = addDescription(eitherRes);
            OperationDataBase.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either addDescription(Either either) {
        Either eitherRes = new Either();
        eitherRes.setCode(either.getCode());
        ArrayList<ModelObject> listObject = either.getListObject();
        for (int i = 0; i < listObject.size(); i++) {

            PersonDetail person = addDescriptionTypeIdentifier((PersonDetail) listObject.get(i));
            person = addDescriptionGenre(person);
            eitherRes.addModeloObjet(person);
        }
        return eitherRes;
    }

    private PersonDetail addDescriptionTypeIdentifier(PersonDetail person) {
        String typeIdPerson = person.getTypeIdentifier();
        try {
            TypeIdentifier typeIden = TypeIdentifier.valueOf(typeIdPerson);
            switch (typeIden) {
                case CI:
                    person.setTypeIdDescription(Message.CI_DESCRIPTION);

                    break;
                case PASS:
                    person.setTypeIdDescription(Message.PASS_DESCRIPTION);
                    break;
                case NIT:
                    person.setTypeIdDescription(Message.NIT_DESCRIPTION);
                    break;
            }
        } catch (Exception e) {
        }
        return person;
    }

    private PersonDetail addDescriptionGenre(PersonDetail person) {
        String genrePerson = person.getGenre();
        try {
            Genre typeIden = Genre.valueOf(genrePerson);
            switch (typeIden) {
                case M:
                    person.setGenre(Message.M_DESCRIPTION);

                    break;
                case F:
                    person.setGenre(Message.F_DESCRIPTION);
                    break;
            }
        } catch (Exception e) {
        }
        return person;
    }
}
