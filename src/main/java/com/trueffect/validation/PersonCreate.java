package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.tools.ConstantData.ValidIdentifier;
import com.trueffect.util.DataCondition;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class PersonCreate implements DataCondition {

    protected HashMap<String, String> listData;
    protected int ageMinimum;

    public PersonCreate(int ageMinimum) {
        this.ageMinimum = ageMinimum;
        listData = new HashMap<String, String>();
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
        Person renterUser = (Person) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        //Validation empty type identifier
        if (PersonValidation.isEmpty(renterUser.getTypeIdentifier())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty identifier
        if (PersonValidation.isEmpty(renterUser.getIdentifier())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty last name
        if (PersonValidation.isEmpty(renterUser.getLastName())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty first name
        if (PersonValidation.isEmpty(renterUser.getFirstName())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.FIRST_NAME);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty genre
        if (PersonValidation.isEmpty(renterUser.getGenre())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.GENRE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty birthday
        if (PersonValidation.isEmpty(renterUser.getBirthday())) {
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
        Person renterUser = (Person) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        boolean validTypeIdentifier = true;
        boolean validIdentifier = true;
        //Validation of identifier
        if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {
            validTypeIdentifier = false;
            String validTypesId
                    = TypeIdentifier.CI.getDescriptionIdentifier() + ", "
                    + TypeIdentifier.NIT.getDescriptionIdentifier() + ", "
                    + TypeIdentifier.PASS.getDescriptionIdentifier();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
            listData.put(ConstantData.DATA, renterUser.getTypeIdentifier());
            listData.put(ConstantData.VALID, validTypesId);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        //Validation of identifier
        if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
            validIdentifier = false;
            String validIden
                    = TypeIdentifier.CI.getDescriptionIdentifier() + " = " + ValidIdentifier.CI.getValidIdentifier() + ", "
                    + TypeIdentifier.PASS.getDescriptionIdentifier() + " = " + ValidIdentifier.PASS.getValidIdentifier() + ", "
                    + TypeIdentifier.NIT.getDescriptionIdentifier() + " = " + ValidIdentifier.NIT.getValidIdentifier();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.DATA, renterUser.getIdentifier());
            listData.put(ConstantData.VALID, validIden);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        //Validation if the identifier belongs to the same type identifier
        if (validTypeIdentifier && validIdentifier) {
            if (!PersonValidation.isValidIdentifier(renterUser.getTypeIdentifier(), renterUser.getIdentifier())) {
                TypeIdentifier typeId = TypeIdentifier.valueOf(renterUser.getTypeIdentifier().toUpperCase());
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
                listData.put(ConstantData.TYPE_DATA_TWO, ConstantData.TYPE_IDENTIFIER);
                listData.put(ConstantData.DATA, renterUser.getIdentifier());
                listData.put(ConstantData.DATA_TWO, typeId.getDescriptionIdentifier());
                errorMessages = OperationString.generateMesage(Message.NOT_SAME_TYPE, listData);
                listError.add(errorMessages);
            }
        }
        //Validation of identifier size
        if (!PersonValidation.isValidSize(renterUser.getIdentifier(), ConstantData.MAX_LENGTH_IDENTIFIER)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.DATA, renterUser.getIdentifier());
            listData.put(ConstantData.SIZE, ConstantData.MAX_LENGTH_IDENTIFIER + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
        //Validation of last name size
        if (!PersonValidation.isValidSize(renterUser.getLastName(), ConstantData.MAX_LENGTH_NAME)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
            listData.put(ConstantData.DATA, renterUser.getLastName());
            listData.put(ConstantData.SIZE, ConstantData.MAX_LENGTH_NAME + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
        //Validation of last name
        if (!PersonValidation.isValidLastName(renterUser.getLastName())) {
            listData.clear();;
            listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
            listData.put(ConstantData.DATA, renterUser.getLastName());
            listData.put(ConstantData.VALID, ConstantData.VALID_LASTNAME);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        //Validation of first name size
        if (!PersonValidation.isValidSize(renterUser.getFirstName(), ConstantData.MAX_LENGTH_NAME)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.FIRST_NAME);
            listData.put(ConstantData.DATA, renterUser.getFirstName());
            listData.put(ConstantData.SIZE, ConstantData.MAX_LENGTH_NAME + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
        //Validation of first name
        if (!PersonValidation.isValidFirstName(renterUser.getFirstName())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.FIRST_NAME);
            listData.put(ConstantData.DATA, renterUser.getFirstName());
            listData.put(ConstantData.VALID, ConstantData.VALID_FIRSTNAME);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        //Validation of genre
        if (!PersonValidation.isValidGenre(renterUser.getGenre())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.GENRE);
            listData.put(ConstantData.DATA, renterUser.getGenre());
            listData.put(ConstantData.VALID, GenrePerson.F.getNameGenre() + ", " + GenrePerson.M.getNameGenre());
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        //Validation of birthday
        if (!DateOperation.isValidDateFormat(renterUser.getBirthday())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.BIRTHDAY);
            listData.put(ConstantData.DATA, renterUser.getBirthday());
            listData.put(ConstantData.VALID, ConstantData.VALID_DATE);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        } else //Validation of age
        {
            if (!DateOperation.dateIsInRangeValid(renterUser.getBirthday())) {
                listData.clear();
                listData.put(ConstantData.DATA, renterUser.getBirthday());
                listData.put(ConstantData.DATA_TWO, DateOperation.getDataCurrent());
                errorMessages = OperationString.generateMesage(Message.DATE_FUTURE, listData);
                listError.add(errorMessages);
            }
            if (!DateOperation.yearIsGreaterThan(renterUser.getBirthday(), ageMinimum)) {
                listData.clear();
                listData.put(ConstantData.DATA, ageMinimum + "");
                errorMessages = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
                listError.add(errorMessages);
            }
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);

        }
        return new Either();
    }
}
