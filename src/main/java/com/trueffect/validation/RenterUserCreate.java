package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.response.ErrorResponse;
import com.trueffect.model.Person;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.DataResourse;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ErrorContainer;

/**
 * @author santiago.mamani
 */
public class RenterUserCreate implements DataCondition {

    @Override
    public boolean complyCondition(ModelObject resource, ErrorContainer errorContainer) {
        boolean res=false;
        if (!existEmpty(resource, errorContainer)){
            res = correctData(resource, errorContainer);
        }
        return res;        
    }

    private boolean existEmpty(ModelObject resource, ErrorContainer errorContainer) {
        Person renterUser = (Person) resource;
        String errorMessages = "";
        //Validation empty type identifier
        if (PersonValidation.isEmpty(renterUser.getTypeIdentifier())) {
            errorMessages = Message.EMPTY_TYPE_IDENTIFIER;
        }
        //Validation empty identifier
        if (PersonValidation.isEmpty(renterUser.getIdentifier())) {
            errorMessages = errorMessages + "\n" + Message.EMPTY_IDENTIFIER;
        }
        //Validation empty last name
        if (PersonValidation.isEmpty(renterUser.getLastName())) {
            errorMessages = errorMessages + "\n" + Message.EMPTY_LAST_NAME;
        }
        //Validation empty first name
        if (PersonValidation.isEmpty(renterUser.getFirstName())) {
            errorMessages = errorMessages + "\n" + Message.EMPTY_FIRST_NAME;
        }
        //Validation empty genre
        if (PersonValidation.isEmpty(renterUser.getGenre())) {
            errorMessages = errorMessages + "\n" + Message.EMPTY_GENRE;
        }
        //Validation empty birthday
        if (PersonValidation.isEmpty(renterUser.getBirthday())) {
            errorMessages = errorMessages + "\n" + Message.EMPTY_BITHDAY;
        }
        //To check if there was an error
        if (!errorMessages.equals("")) {

            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMessages));
            return true;
        }
        return false;
    }

    private boolean correctData(ModelObject resource, ErrorContainer errorContainer) {
        Person renterUser = (Person) resource;
        String errorMessages = "";
        //Validation of identifier
        if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {
            errorMessages = Message.NOT_VALID_TYPE_IDENTIFIER ;
        }
        //Validation of identifier
        if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_IDENTIFIER;
        }
         //Validation of identifier size
        if (!PersonValidation.isValidSize(renterUser.getIdentifier(), DataResourse.MAXIMUM_IDENTIFIER)) {
            errorMessages = errorMessages + "\n" + Message.SIZE_IDENTIFIER;
        }
        //Validation to what the type identifier and the identifier are of the same type
        if (!PersonValidation.isValidIdentifier(renterUser.getTypeIdentifier(), renterUser.getIdentifier())) {
            errorMessages = errorMessages + "\n" + Message.NOT_SAME_TYPE;
        }
        //Validation of last name size
        if (!PersonValidation.isValidSize(renterUser.getLastName(), DataResourse.MAXIMUM_NAMES)) {
            errorMessages = errorMessages + "\n" + Message.SIZE_LAST_NAME;
        }
        //Validation of last name
        if (!PersonValidation.isValidLastName(renterUser.getLastName())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_LAST_NAME;
        }
        //Validation of first name size
        if (!PersonValidation.isValidSize(renterUser.getFirstName(), DataResourse.MAXIMUM_NAMES)) {
            errorMessages = errorMessages + "\n" + Message.SIZE_FIRST_NAME;
        }
        //Validation of first name
        if (!PersonValidation.isValidFirstName(renterUser.getFirstName())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_FIRST_NAME;
        }
        //Validation of genre
        if (!PersonValidation.isValidGenre(renterUser.getGenre())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_GENRE;
        }
        //Validation of birthday
        if (!PersonValidation.isValidBirthday(renterUser.getBirthday())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_BIRTHDAY;
        }
        //To check if there was an error
        if (!errorMessages.equals("")) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMessages));
            return false;
        }
        return true;
    }
}
