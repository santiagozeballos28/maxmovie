package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ErrorContainer;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.HashMap;

/*
 * @author santiago.mamani
 */
public class RenterUserUpdate implements DataCondition {

    private String job;
    private HashMap<String, String> listData;

    public RenterUserUpdate(String job) {
        this.job = job;
        listData = new HashMap<String, String>();
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
        String errorMgs = "";
        listData.clear();
        if (job.equals("Administrator")) {
            if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {

                listData.put("{typeData}", Message.TYPE_IDENTIFIER);
                listData.put("{data}", renterUser.getTypeIdentifier());
                listData.put("{valid}", Message.VALID_TI);
                errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
            }
        } else {
            listData.put("{typeData}", Message.TYPE_IDENTIFIER);
            errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
    }

    private void intentUpdateIdentifier(Person renterUser, ErrorContainer errorContainer) {
        String errorMgs = "";
        listData.clear();
        if (job.equals("Administrator")) {
            if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
                listData.put("{typeData}", Message.IDENTIFIER);
                listData.put("{data}", renterUser.getIdentifier());
                listData.put("{valid}", Message.VALID_I);
                errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
                errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
            }
        } else {
            listData.put("{typeData}", Message.IDENTIFIER);
            errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
    }

    private void validateLastName(String lastName, ErrorContainer errorContainer) {
        String errorMgs="";
        if (!PersonValidation.isValidLastName(lastName)) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", lastName);
            listData.put("{valid}", Message.VALID_LN);
            errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
          //Validation of last name size
        if (!PersonValidation.isValidSize(lastName, ConstantData.MAXIMUM_NAMES)) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", lastName);
            listData.put("{size}", ConstantData.MAXIMUM_NAMES + "");
            errorMgs = OperationString.generateMesage(Message.SIZE_MAX, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
    }

    private void validateFirstName(String firstName, ErrorContainer errorContainer) {
            String errorMgs="";
        if (!PersonValidation.isValidLastName(firstName)) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", firstName);
            listData.put("{valid}", Message.VALID_FN);
            errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
          //Validation of last name size
        if (!PersonValidation.isValidSize(firstName, ConstantData.MAXIMUM_NAMES)) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", firstName);
            listData.put("{size}", ConstantData.MAXIMUM_NAMES + "");
            errorMgs =  OperationString.generateMesage(Message.SIZE_MAX, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
    }

    private void validationGenre(String genre, ErrorContainer errorContainer) {
        String errorMgs="";
        if (!PersonValidation.isValidGenre(genre)) {
              listData.clear();
            listData.put("{typeData}", Message.GENRE);
            listData.put("{data}", genre);
            listData.put("{valid}", Message.VALID_G);
            errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST, errorMgs));
        }
    }

    private void validationBirthday(String birthday, ErrorContainer errorContainer) {
        String errorMgs="";
        if (!PersonValidation.isValidBirthday(birthday)) {
            listData.clear();
            listData.put("{typeData}", Message.BIRTHDAY);
            listData.put("{data}", birthday);
            listData.put("{valid}", Message.VALID_B);
            errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST,errorMgs));
        }
           //Validation of birthday
        if (!PersonValidation.isValidAge(birthday, ConstantData.MINIMUM_AGE)) {
            listData.clear();
            errorMgs = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
            errorContainer.addError(new ErrorResponse(CodeStatus.BAD_REQUEST,errorMgs));
        }
    }
}
