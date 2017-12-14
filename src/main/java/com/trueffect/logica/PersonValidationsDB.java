package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ErrorContainer;
import java.sql.Connection;

/**
 * @author santiago.mamani
 */
public class PersonValidationsDB {

    /*
    * For dates duplidates
     */
    public static void veriryDataInDataBase(Connection connection, Person personNew) throws Exception {
        ErrorContainer errorContainer = new ErrorContainer();
        Person person = (Person) PersonCrud.getPersonByIdentifier(connection, personNew.getTypeIdentifier(), personNew.getIdentifier());
        if (person != null) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.DUPLICATE_IDENTIFIER));
        }
        person = (Person) PersonCrud.getPersonByName(connection, personNew.getLastName(), person.getFirstName());
        if (person != null) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.THE_NAMES_ALREADY_EXIST));
        }
        if (!errorContainer.isEmpty()) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }
    }

    public static void verifyDataUpdate(Connection connection, int id, Person personNew) throws Exception {
        ErrorContainer errorContainer = new ErrorContainer();
        Person personOld = PersonCrud.getPerson(connection, id);
        Person personAux = generatePersonAuxiliary(personOld, personNew);
        Person personById = (Person) PersonCrud.getPersonByIdentifier(connection, personAux.getTypeIdentifier(), personAux.getIdentifier());
        Person personByName = (Person) PersonCrud.getPersonByName(connection, personAux.getLastName(), personAux.getFirstName());
        if (personById != null) {
            if (id != personById.getId()) {
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.DUPLICATE_IDENTIFIER));
            }
        }
        if (personByName != null) {
            if (id != personByName.getId()) {
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.THE_NAMES_ALREADY_EXIST));
            }
        }
        if (!errorContainer.isEmpty()) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }

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
