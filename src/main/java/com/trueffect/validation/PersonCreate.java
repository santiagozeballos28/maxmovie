package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.DataCondition;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 * @author santiago.mamani
 */
public class PersonCreate implements DataCondition {

    protected HashMap<String, String> listData;
    protected int ageMinimum;
    protected String nameObject;
    private PersonValidation personValidation;
    private ObjectValidation objectValidation;
    private DateValidation dateValidation;

    public PersonCreate(int ageMinimum, String nameObject) {
        this.ageMinimum = ageMinimum;
        this.nameObject = nameObject;
        listData = new HashMap<String, String>();
        personValidation = new PersonValidation();
        objectValidation = new ObjectValidation();
        dateValidation = new DateValidation();
    }

    @Override
    public Either complyCondition(ModelObject resource) {
        Either eitherRes = verifyEmpty(resource);
        if (eitherRes.existError()) {
            return eitherRes;
        }
        return verifyData(resource);
    }

    protected Either verifyEmpty(ModelObject resource) {
        Person person = (Person) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        //Validation empty type identifier
        if (StringUtils.isBlank(person.getTypeIdentifier())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty identifier
        if (StringUtils.isBlank(person.getIdentifier())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty last name
        if (StringUtils.isBlank(person.getLastName())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty first name
        if (StringUtils.isBlank(person.getFirstName())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.FIRST_NAME);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty genre
        if (StringUtils.isBlank(person.getGenre())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.GENRE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty birthday
        if (StringUtils.isBlank(person.getBirthday())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.BIRTHDAY);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    protected Either verifyData(ModelObject resource) {
        Person person = (Person) resource;
        ArrayList<String> listError = new ArrayList<String>();
        //Validation of type identifier
        boolean validTypeIdentifier = personValidation.isValidTypeIdentifier(person.getTypeIdentifier(), listError);
        //Validation of identifier
        boolean validIdentifier = personValidation.isValidIdentifier(person.getIdentifier(), listError);
        //Validation if the identifier belongs to the same type identifier
        if (validTypeIdentifier && validIdentifier) {
            personValidation.verifyIdentifiers(person.getTypeIdentifier(), person.getIdentifier(), listError);
        }
        //Validation of identifier size identifier
        objectValidation.verifySize(ConstantData.IDENTIFIER, person.getIdentifier(), ConstantData.MAX_LENGTH_IDENTIFIER, listError);
        //Validation of last name size
        objectValidation.verifySize(ConstantData.LAST_NAME, person.getLastName(), ConstantData.MAX_LENGTH_NAME, listError);
        //Validation of last name
        personValidation.verifyName(ConstantData.LAST_NAME, person.getLastName(), listError);
        //Validation of first name size
        objectValidation.verifySize(ConstantData.FIRST_NAME, person.getFirstName(), ConstantData.MAX_LENGTH_NAME, listError);
        //Validation of first name
        personValidation.verifyName(ConstantData.FIRST_NAME, person.getFirstName(), listError);
        //Validation of genre
        personValidation.verifyGenre(person.getGenre(), listError);
        //Validation of birthday (format date)
        boolean validBirthdayFormat = dateValidation.isValidDate(ConstantData.BIRTHDAY, person.getBirthday(), listError);
        if (validBirthdayFormat) {
            //Validation of age
            dateValidation.verifyDateRangeValid(person.getBirthday(), listError);
            // Validation minimun age
            personValidation.verifyRequiredAge(person.getBirthday(), ageMinimum,nameObject, listError);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
