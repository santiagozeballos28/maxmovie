/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ErrorContainer;
import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class RenterUserUpdate implements DataCondition {

    private String job;

    public void setIdUserModify(String job) {
        this.job = job;
    }

    @Override
    public void complyCondition(ModelObject resource) throws Exception {

        Person renterUser = (Person) resource;
        ErrorContainer errorContainer = new ErrorContainer();
        //Validation empty type identifier
        if (!PersonValidation.isEmpty(renterUser.getTypeIdentifier())) {
            intentUpdateTypeIdentifier(renterUser, errorContainer);
        }
        //Validation empty type identifier
        if (!PersonValidation.isEmpty(renterUser.getIdentifier())) {
            intentUpdateIdentifier(renterUser, errorContainer);
        }
        if (!PersonValidation.isEmpty(renterUser.getLastName())) {
            validateLastName(renterUser.getLastName(), errorContainer);
        }
        //Validation empty first name
        if (!PersonValidation.isEmpty(renterUser.getFirstName())) {
            validateFirstName(renterUser.getFirstName(), errorContainer);
        }
        //Validation empty genre
        if (!PersonValidation.isEmpty(renterUser.getGenre())) {
            validationGenre(renterUser.getGenre(), errorContainer);
        }
        //Validation empty birthday
        if (!PersonValidation.isEmpty(renterUser.getBirthday())) {
            validationBirthday(renterUser.getBirthday(), errorContainer);
        }
        //To check if there was an error
        if (errorContainer.size() > 0) {
            throw new ErrorResponse(errorContainer.getCodeStatusEnd(), errorContainer.allMessagesError());
        }

    }

    private void intentUpdateTypeIdentifier(Person renterUser, ErrorContainer errorContainer) {
        if (job.equals("Administrator")) {
            if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_TYPE_IDENTIFIER));
            }
        } else {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_HAVE_PERMISSION_TYPE_IDENTIFIER));
        }

    }

    private void intentUpdateIdentifier(Person renterUser, ErrorContainer errorContainer) {
        if (job.equals("Administrator")) {
            if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_IDENTIFIER));
            }
        } else {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_HAVE_PERMISSION_IDENTIFIER));
        }
    }

    private void validateLastName(String lastName, ErrorContainer errorContainer) {
        if (!PersonValidation.isValidLastName(lastName)) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_LAST_NAME));
        }
    }

    private void validateFirstName(String firstName, ErrorContainer errorContainer) {
        if (!PersonValidation.isValidFirstName(firstName)) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_FIRST_NAME));
        }
    }

    private void validationGenre(String genre, ErrorContainer errorContainer) {
        if (!PersonValidation.isValidGenre(genre)) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_GENRE));
        }
    }

    private void validationBirthday(String birthday, ErrorContainer errorContainer) {
        if (!PersonValidation.isValidBirthday(birthday)) {
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, Message.NOT_VALID_BIRTHDAY));
        }
    }
}
