package com.trueffect.logica.person;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.OperationString;
import com.trueffect.validation.PersonValidation;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class PersonValidationsDB {

    /*
    * For dates duplidates
     */
    public static Either veriryDataInDataBase(Connection connection, Person personNew) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        HashMap<String, String> listData = new HashMap<String, String>();
        Either eitherPerson = PersonCrud.getPersonByIdentifier(connection, personNew.getTypeIdentifier(), personNew.getIdentifier());
        if (eitherPerson.haveModelObject()) {
            listData.put("{typeData}", Message.IDENTIFIERS);
            listData.put("{data}", personNew.getTypeIdentifier() + " = " + personNew.getIdentifier());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        eitherPerson = PersonCrud.getPersonByName(connection, personNew.getLastName(), personNew.getFirstName());
        if (eitherPerson.haveModelObject()) {
            listData.clear();
            listData.put("{typeData}", Message.NAMES);
            listData.put("{data}", personNew.getLastName() + " = " + personNew.getFirstName());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    public static Either verifyDataUpdate(Connection connection, int id, Person personNew) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        HashMap<String, String> listData = new HashMap<String, String>();
        Either eitherPersonOld = PersonCrud.getPerson(connection, id);
        Person personAux = generatePersonAuxiliary((Person) eitherPersonOld.getModelObject(), personNew);
        if (!PersonValidation.isValidIdentifier(personAux.getTypeIdentifier(), personAux.getIdentifier())) {

            listData.clear();
            listData.put("{typeData1}", Message.IDENTIFIER);
            listData.put("{typeData2}", Message.TYPE_IDENTIFIER);
            listData.put("{data1}", personAux.getIdentifier());
            listData.put("{data2}", personAux.getTypeIdentifier());
            errorMgs = OperationString.generateMesage(Message.NOT_SAME_TYPE, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        Either eitherPersonById = PersonCrud.getPersonByIdentifier(connection, personAux.getTypeIdentifier(), personAux.getIdentifier());
        if (eitherPersonById.haveModelObject()) {
            Person personEither = (Person) eitherPersonById.getModelObject();
            if (id != personEither.getId()) {
                listData.put("{typeData}", Message.IDENTIFIERS);
                listData.put("{data}", personAux.getTypeIdentifier() + " = " + personAux.getIdentifier());
                errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
            }
        }
        Either eitherPersonByName = PersonCrud.getPersonByName(connection, personAux.getLastName(), personAux.getFirstName());
        if (eitherPersonByName.haveModelObject()) {
            Person personEither = (Person) eitherPersonByName.getModelObject();
            if (id != personEither.getId()) {
                listData.clear();
                listData.put("{typeData}", Message.NAMES);
                listData.put("{data}", personAux.getLastName() + " = " + personAux.getFirstName());
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
