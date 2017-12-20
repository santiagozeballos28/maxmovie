package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.response.ErrorResponse;
import com.trueffect.model.Person;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.DataCondition;
import com.trueffect.util.OperationString;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class RenterUserCreate implements DataCondition {

    private HashMap<String, String> listData;

    public RenterUserCreate() {
        listData = new HashMap<String, String>();
    }

    @Override
    public void complyCondition(ModelObject resource) throws Exception {
        verifyEmpty(resource);
        verifyData(resource);

    }

    private void verifyEmpty(ModelObject resource) throws Exception {
        Person renterUser = (Person) resource;
        String errorMessages = "";
        //Validation empty type identifier
        if (PersonValidation.isEmpty(renterUser.getTypeIdentifier())) {
            listData.clear();
            listData.put("{typeData}", Message.TYPE_IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
        }
        //Validation empty identifier
        if (PersonValidation.isEmpty(renterUser.getIdentifier())) {
            listData.clear();
            listData.put("{typeData}", Message.IDENTIFIER);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.EMPTY_DATA, listData);

        }
        //Validation empty last name
        if (PersonValidation.isEmpty(renterUser.getLastName())) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.EMPTY_DATA, listData);
        }
        //Validation empty first name
        if (PersonValidation.isEmpty(renterUser.getFirstName())) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.EMPTY_DATA, listData);
        }
        //Validation empty genre
        if (PersonValidation.isEmpty(renterUser.getGenre())) {
            listData.clear();
            listData.put("{typeData}", Message.GENRE);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.EMPTY_DATA, listData);;;
        }
        //Validation empty birthday
        if (PersonValidation.isEmpty(renterUser.getBirthday())) {
            listData.clear();
            listData.put("{typeData}", Message.BIRTHDAY);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.EMPTY_DATA, listData);
        }
        //To check if there was an error
        if (!errorMessages.equals("")) {
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, errorMessages);
        }
    }

    private void verifyData(ModelObject resource) throws Exception {
        Person renterUser = (Person) resource;
        String errorMessages = "";
        //Validation of identifier
        if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {
            listData.clear();
            listData.put("{typeData}", Message.TYPE_IDENTIFIER);
            listData.put("{data}", renterUser.getTypeIdentifier());
            listData.put("{valid}", Message.VALID_TI);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
        }
        //Validation of identifier
        if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
            listData.clear();
            listData.put("{typeData}", Message.IDENTIFIER);
            listData.put("{data}", renterUser.getIdentifier());
            listData.put("{valid}", Message.VALID_I);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
        }
        //Validation of identifier size
        if (!PersonValidation.isValidSize(renterUser.getIdentifier(), ConstantData.MAXIMUM_IDENTIFIER)) {
            listData.clear();
            listData.put("{typeData}", Message.IDENTIFIER);
            listData.put("{data}", renterUser.getIdentifier());
            listData.put("{size}", ConstantData.MAXIMUM_IDENTIFIER + "");
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.SIZE_MAX, listData);
        }

        //Validation of last name size
        if (!PersonValidation.isValidSize(renterUser.getLastName(), ConstantData.MAXIMUM_NAMES)) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", renterUser.getLastName());
            listData.put("{size}", ConstantData.MAXIMUM_NAMES + "");
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.SIZE_MAX, listData);
        }
        //Validation of last name
        if (!PersonValidation.isValidLastName(renterUser.getLastName())) {
            listData.clear();;
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", renterUser.getLastName());
            listData.put("{valid}", Message.VALID_LN);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
        }
        //Validation of first name size
        if (!PersonValidation.isValidSize(renterUser.getFirstName(), ConstantData.MAXIMUM_NAMES)) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", renterUser.getFirstName());
            listData.put("{size}", ConstantData.MAXIMUM_NAMES + "");
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.SIZE_MAX, listData);
        }
        //Validation of first name
        if (!PersonValidation.isValidFirstName(renterUser.getFirstName())) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", renterUser.getFirstName());
            listData.put("{valid}", Message.VALID_FN);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
        }
        //Validation of genre
        if (!PersonValidation.isValidGenre(renterUser.getGenre())) {
            listData.clear();
            listData.put("{typeData}", Message.GENRE);
            listData.put("{data}", renterUser.getGenre());
            listData.put("{valid}", Message.VALID_G);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
        }
        //Validation of birthday
        if (!PersonValidation.isValidBirthday(renterUser.getBirthday())) {
            listData.clear();
            listData.put("{typeData}", Message.BIRTHDAY);
            listData.put("{data}", renterUser.getBirthday());
            listData.put("{valid}", Message.VALID_B);
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
        }
        //Validation of birthday
        if (!PersonValidation.isValidAge(renterUser.getBirthday(), ConstantData.MINIMUM_AGE)) {
            listData.clear();
            errorMessages = errorMessages + "\n" + OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
        }
        //To check if there was an error
        if (!errorMessages.equals("")) {
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, errorMessages);
        }
    }

    public void identifiersAreOfTheSameType(Person renterUser) throws Exception {
        //Validation to what the type identifier and the identifier are of the same type
        if (!PersonValidation.isValidIdentifier(renterUser.getTypeIdentifier(), renterUser.getIdentifier())) {
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_SAME_TYPE);
        }
    }
}
