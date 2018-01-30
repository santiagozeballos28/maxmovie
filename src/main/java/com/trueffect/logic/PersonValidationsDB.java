package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.util.OperationString;
import com.trueffect.validation.PersonValidationUtil;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 * @author santiago.mamani
 */
public class PersonValidationsDB {

    protected HashMap<String, String> listData;
    private PersonCrud personCrud;

    public PersonValidationsDB() {
        this.listData = new HashMap<String, String>();
        personCrud = new PersonCrud();
    }

    public Either veriryDataInDataBase(Connection connection, Person personNew) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherPerson = personCrud.getPersonByIdentifier(connection, personNew.getTypeIdentifier().toUpperCase(), personNew.getIdentifier().toUpperCase());
        if (eitherPerson.haveModelObject()) {
            listData.clear();
            TypeIdentifier typeIdenEnum = TypeIdentifier.valueOf(personNew.getTypeIdentifier().toUpperCase());
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.DATA, typeIdenEnum.getDescriptionIdentifier() + " = " + personNew.getIdentifier());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        String lastNameAux = OperationString.generateName(personNew.getLastName());
        lastNameAux = OperationString.addApostrophe(lastNameAux);
        String firstNameAux = OperationString.generateName(personNew.getFirstName());
        firstNameAux = OperationString.addApostrophe(firstNameAux);
        eitherPerson = personCrud.getPersonByName(connection, lastNameAux, firstNameAux);
        if (eitherPerson.haveModelObject()) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME);
            listData.put(ConstantData.DATA, personNew.getLastName() + " " + personNew.getFirstName());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    public Either verifyDataUpdate(Connection connection, Person personNew) {
        long idPerson = personNew.getId();
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherPersonOld = personCrud.getPerson(connection, idPerson, null);
        String typeIdOld = ((Person) eitherPersonOld.getFirstObject()).getTypeIdentifier();
        Person personAux = generatePersonAuxiliary((Person) eitherPersonOld.getFirstObject(), personNew);
        if (!PersonValidationUtil.isValidIdentifier(personAux.getTypeIdentifier().toUpperCase(), personAux.getIdentifier().toUpperCase())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.TYPE_DATA_TWO, ConstantData.TYPE_IDENTIFIER);
            listData.put(ConstantData.DATA, personAux.getIdentifier());
            listData.put(ConstantData.DATA_TWO, personAux.getTypeIdentifier());
            errorMgs = OperationString.generateMesage(Message.NOT_SAME_TYPE, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        Either eitherPersonById = personCrud.getPersonByIdentifier(connection, personAux.getTypeIdentifier().toUpperCase(), personAux.getIdentifier().toUpperCase());
        if (eitherPersonById.haveModelObject()) {
            Person personEither = (Person) eitherPersonById.getFirstObject();
            if (idPerson != personEither.getId()) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
                listData.put(ConstantData.DATA, personAux.getTypeIdentifier() + " = " + personAux.getIdentifier());
                errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
            }
        }
        OperationString.addApostrophe(personAux);
        Either eitherPersonByName = personCrud.getPersonByName(connection, personAux.getLastName(), personAux.getFirstName());
        if (eitherPersonByName.haveModelObject()) {
            Person personEither = (Person) eitherPersonByName.getFirstObject();
            if (idPerson != personEither.getId()) {
                String lastName = personAux.getLastName();
                String firstName = personAux.getFirstName();
                if (StringUtils.isNotBlank(personNew.getLastName())) {
                    lastName = personNew.getLastName();//to show the current last name
                }
                if (StringUtils.isNotBlank(personNew.getFirstName())) {
                    firstName = personNew.getFirstName();//to show the current first name
                }
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.NAME);
                listData.put(ConstantData.DATA, lastName + " " + firstName);
                errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
            }
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private Person generatePersonAuxiliary(Person personOld, Person personNew) {
        Person personAuxiliary = personOld;
        if (StringUtils.isNotBlank(personNew.getTypeIdentifier())) {
            personAuxiliary.setTypeIdentifier(personNew.getTypeIdentifier());
        }
        if (StringUtils.isNotBlank(personNew.getIdentifier())) {
            personAuxiliary.setIdentifier(personNew.getIdentifier());
        }
        if (StringUtils.isNotBlank(personNew.getLastName())) {
            personAuxiliary.setLastName(personNew.getLastName());
        }
        if (StringUtils.isNotBlank(personNew.getFirstName())) {
            personAuxiliary.setFirstName(personNew.getFirstName());
        }
        return personAuxiliary;
    }
}
