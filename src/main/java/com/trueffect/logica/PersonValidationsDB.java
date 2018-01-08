package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import com.trueffect.validation.PersonValidation;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class PersonValidationsDB {

    public static Either veriryDataInDataBase(Connection connection, Person personNew) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        HashMap<String, String> listData = new HashMap<String, String>();
        Either eitherPerson = PersonCrud.getPersonByIdentifier(connection, personNew.getTypeIdentifier(), personNew.getIdentifier());
        if (eitherPerson.haveModelObject()) {
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.DATA, personNew.getTypeIdentifier() + " = " + personNew.getIdentifier());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        eitherPerson = PersonCrud.getPersonByName(connection, personNew.getLastName(), personNew.getFirstName());
        if (eitherPerson.haveModelObject()) {
            Person personDB = (Person) eitherPerson.getFirstObject();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME);
            listData.put(ConstantData.DATA, personDB.getLastName() + " " + personDB.getFirstName());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    public static Either verifyDataUpdate(Connection connection, Person personNew) {
        int idPerson = personNew.getId();
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        HashMap<String, String> listData = new HashMap<String, String>();
        Either eitherPersonOld = PersonCrud.getPerson(connection, idPerson, "");
        Person personAux = generatePersonAuxiliary((Person) eitherPersonOld.getFirstObject(), personNew);
        if (!PersonValidation.isValidIdentifier(personAux.getTypeIdentifier(), personAux.getIdentifier())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.TYPE_DATA_TWO, ConstantData.TYPE_IDENTIFIER);
            listData.put(ConstantData.DATA, personAux.getIdentifier());
            listData.put(ConstantData.DATA_TWO, personAux.getTypeIdentifier());
            errorMgs = OperationString.generateMesage(Message.NOT_SAME_TYPE, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        Either eitherPersonById = PersonCrud.getPersonByIdentifier(connection, personAux.getTypeIdentifier(), personAux.getIdentifier());
        if (eitherPersonById.haveModelObject()) {
            Person personEither = (Person) eitherPersonById.getFirstObject();
            if (idPerson != personEither.getId()) {
                listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
                listData.put(ConstantData.DATA, personAux.getTypeIdentifier() + " = " + personAux.getIdentifier());
                errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
            }
        }
        Either eitherPersonByName = PersonCrud.getPersonByName(connection, personAux.getLastName(), personAux.getFirstName());
        if (eitherPersonByName.haveModelObject()) {
            Person personEither = (Person) eitherPersonByName.getFirstObject();
            if (idPerson != personEither.getId()) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.NAME);
                listData.put(ConstantData.DATA, personEither.getLastName() + " " + personEither.getFirstName());
                errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
            }
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private static Person generatePersonAuxiliary(Person personOld, Person personNew) {
        Person personAuxiliary = personOld;
        if (personNew.getTypeIdentifier() != null) {
            personAuxiliary.setTypeIdentifier(personNew.getTypeIdentifier());
        }
        if (personNew.getIdentifier() != null) {
            personAuxiliary.setIdentifier(personNew.getIdentifier());
        }
        if (personNew.getLastName() != null) {
            personAuxiliary.setLastName(personNew.getLastName());
        }
        if (personNew.getFirstName() != null) {
            personAuxiliary.setFirstName(personNew.getFirstName());
        }
        return personAuxiliary;
    }
}
