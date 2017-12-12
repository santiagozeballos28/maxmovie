package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.util.ModelObject;
import com.trueffect.response.ErrorResponse;
import com.trueffect.model.Person;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.DataResourse;
import com.trueffect.util.DataCondition;
import java.sql.Connection;

/**
 * @author santiago.mamani
 */
public class RenterUser implements DataCondition {

    @Override
    public boolean complyCondition(ModelObject resource) throws Exception {
        Person renterUser = (Person) resource;
        String errorMessages = "";
        //Validation of Type identifier
        if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {
            errorMessages = Message.NOT_VALID_TYPE_IDENTIFIER;
        }
        //Validation of identifier
        if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_IDENTIFIER;
        }
        //Validation of identifier
        if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_IDENTIFIER;
        }
        //Validation of last name
        if (!PersonValidation.isValidLastName(renterUser.getLastName(), DataResourse.MAXIMUM_VALUES)) {
            errorMessages = errorMessages + "\n" + Message.NOT_VALID_LAST_NAME;
        }

        //Validation of first name
        if (!PersonValidation.isValidFirstName(renterUser.getFirstName(), DataResourse.MAXIMUM_VALUES)) {
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
        if (!errorMessages.equals("")) {
            throw new ErrorResponse(CodeStatus.BAD_REQUEST, errorMessages);
        }
        return true;
    }

}
