package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.TypeIdentifier;
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
            listData.put("{typeData}", Message.TYPE_IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty identifier
        if (PersonValidation.isEmpty(renterUser.getIdentifier())) {
            listData.clear();
            listData.put("{typeData}", Message.IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty last name
        if (PersonValidation.isEmpty(renterUser.getLastName())) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty first name
        if (PersonValidation.isEmpty(renterUser.getFirstName())) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty genre
        if (PersonValidation.isEmpty(renterUser.getGenre())) {
            listData.clear();
            listData.put("{typeData}", Message.GENRE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty birthday
        if (PersonValidation.isEmpty(renterUser.getBirthday())) {
            listData.clear();
            listData.put("{typeData}", Message.BIRTHDAY);
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
            listData.clear();
            listData.put("{typeData}", Message.TYPE_IDENTIFIER);
            listData.put("{data}", renterUser.getTypeIdentifier());
            listData.put("{valid}", TypeIdentifier.CI+ ", " +TypeIdentifier.NIT +", "+TypeIdentifier.PASS);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of identifier
        if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
            validIdentifier = false;
            listData.clear();
            listData.put("{typeData}", Message.IDENTIFIER);
            listData.put("{data}", renterUser.getIdentifier());
            listData.put("{valid}", Message.VALID_IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation if the identifier belongs to the same type identifier
        if (validTypeIdentifier && validIdentifier) {
            if (!PersonValidation.isValidIdentifier(renterUser.getTypeIdentifier(), renterUser.getIdentifier())) {
                listData.clear();
                listData.put("{typeData1}", Message.IDENTIFIER);
                listData.put("{typeData2}", Message.TYPE_IDENTIFIER);
                listData.put("{data1}", renterUser.getIdentifier());
                listData.put("{data2}", renterUser.getTypeIdentifier());
                errorMessages = OperationString.generateMesage(Message.NOT_SAME_TYPE, listData);
                listError.add(errorMessages);
            }
        }
        //Validation of identifier size
        if (!PersonValidation.isValidSize(renterUser.getIdentifier(), ConstantData.MAX_LENGTH_IDENTIFIER)) {
            listData.clear();
            listData.put("{typeData}", Message.IDENTIFIER);
            listData.put("{data}", renterUser.getIdentifier());
            listData.put("{size}", ConstantData.MAX_LENGTH_IDENTIFIER + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }

        //Validation of last name size
        if (!PersonValidation.isValidSize(renterUser.getLastName(), ConstantData.MAX_LENGTH_NAME)) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", renterUser.getLastName());
            listData.put("{size}", ConstantData.MAX_LENGTH_NAME + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
        //Validation of last name
        if (!PersonValidation.isValidLastName(renterUser.getLastName())) {
            listData.clear();;
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", renterUser.getLastName());
            listData.put("{valid}", Message.VALID_LASTNAME);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of first name size
        if (!PersonValidation.isValidSize(renterUser.getFirstName(), ConstantData.MAX_LENGTH_NAME)) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", renterUser.getFirstName());
            listData.put("{size}", ConstantData.MAX_LENGTH_NAME + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
        //Validation of first name
        if (!PersonValidation.isValidFirstName(renterUser.getFirstName())) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", renterUser.getFirstName());
            listData.put("{valid}", Message.VALID_FIRSTNAME);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of genre
        if (!PersonValidation.isValidGenre(renterUser.getGenre())) {
            listData.clear();
            listData.put("{typeData}", Message.GENRE);
            listData.put("{data}", renterUser.getGenre());
            listData.put("{valid}", GenrePerson.F.getNameGenre()+", " +GenrePerson.M.getNameGenre());
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of birthday
        if (!PersonValidation.isValidBirthday(renterUser.getBirthday())) {
            listData.clear();
            listData.put("{typeData}", Message.BIRTHDAY);
            listData.put("{data}", renterUser.getBirthday());
            listData.put("{valid}", Message.VALID_BIRTHDAY);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of birthday
        if (!PersonValidation.isValidAge(renterUser.getBirthday(), ageMinimum)) {
            listData.clear();
            listData.put("{data}", ageMinimum + "");
            errorMessages = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
            listError.add(errorMessages);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);

        }
        return new Either();
    }
}
