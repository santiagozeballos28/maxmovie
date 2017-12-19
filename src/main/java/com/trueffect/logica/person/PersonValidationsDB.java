package com.trueffect.logica.person;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ErrorContainer;
import com.trueffect.validation.PersonValidation;
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
        Person person = PersonCrud.getPersonByIdentifier(connection, personNew.getTypeIdentifier(), personNew.getIdentifier());
        if (!person.isEmpty()) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.DUPLICATE_IDENTIFIER));
        }
        person = PersonCrud.getPersonByName(connection, personNew.getLastName(), personNew.getFirstName());
        if (!person.isEmpty()) {
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
        if(!PersonValidation.isValidIdentifier(personAux.getTypeIdentifier(),personAux.getIdentifier())){
        throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_SAME_TYPE);
        }       
        Person personById = PersonCrud.getPersonByIdentifier(connection, personAux.getTypeIdentifier(), personAux.getIdentifier());
        Person personByName = PersonCrud.getPersonByName(connection, personAux.getLastName(), personAux.getFirstName());
        if (!personById.isEmpty()) {
            if (id != personById.getId()) {
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.DUPLICATE_IDENTIFIER));
            }
        }
        if (!personByName.isEmpty()) {
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
