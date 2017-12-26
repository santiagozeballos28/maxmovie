package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * @author santiago.mamani
 */
public class RenterUserUpdate implements DataCondition {

    private String job;
    private HashMap<String, String> listData;
    private int ageMinimum;

    public RenterUserUpdate(String job,int ageMinimum) {
        this.job = job;
        this.ageMinimum=ageMinimum;
        listData = new HashMap<String, String>();
    }

    @Override
    public Either complyCondition(ModelObject resource) {

        Person renterUser = (Person) resource;
        ArrayList<String> listError = new ArrayList<String>();
        //Validation empty type identifier
        if (!PersonValidation.isEmpty(renterUser.getTypeIdentifier())) {
            intentUpdateTypeIdentifier(renterUser, listError);
        }
        //Validation empty type identifier
        if (!PersonValidation.isEmpty(renterUser.getIdentifier())) {
            intentUpdateIdentifier(renterUser, listError);
        }
        if (!PersonValidation.isEmpty(renterUser.getLastName())) {
            validateLastName(renterUser.getLastName(), listError);
        }
        //Validation empty first name
        if (!PersonValidation.isEmpty(renterUser.getFirstName())) {
            validateFirstName(renterUser.getFirstName(), listError);
        }
        //Validation empty genre
        if (!PersonValidation.isEmpty(renterUser.getGenre())) {
            validationGenre(renterUser.getGenre(), listError);
        }
        //Validation empty birthday
        if (!PersonValidation.isEmpty(renterUser.getBirthday())) {
            validationBirthday(renterUser.getBirthday(), listError);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {

            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private void intentUpdateTypeIdentifier(Person renterUser, ArrayList<String> listError) {
        String errorMessage = "";
        listData.clear();
        if (job.equals("Administrator")) {
            if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {

                listData.put("{typeData}", Message.TYPE_IDENTIFIER);
                listData.put("{data}", renterUser.getTypeIdentifier());
                listData.put("{valid}", Message.VALID_TI);
                errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
                listError.add(errorMessage);
            }
        } else {
            listData.put("{typeData}", Message.TYPE_IDENTIFIER);
            errorMessage = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMessage);
        }
    }

    private void intentUpdateIdentifier(Person renterUser, ArrayList<String> listError) {
        String errorMessage = "";
        listData.clear();
        if (job.equals("Administrator")) {
            if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
                listData.put("{typeData}", Message.IDENTIFIER);
                listData.put("{data}", renterUser.getIdentifier());
                listData.put("{valid}", Message.VALID_I);
                errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
                listError.add(errorMessage);
            }
        } else {
            listData.put("{typeData}", Message.IDENTIFIER);
            errorMessage = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMessage);

        }
    }

    private void validateLastName(String lastName, ArrayList<String> listError) {
        String errorMessage = "";
        if (!PersonValidation.isValidLastName(lastName)) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", lastName);
            listData.put("{valid}", Message.VALID_LN);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessage);
        }
        //Validation of last name size
        if (!PersonValidation.isValidSize(lastName, ConstantData.MAXIMUM_NAMES)) {
            listData.clear();
            listData.put("{typeData}", Message.LAST_NAME);
            listData.put("{data}", lastName);
            listData.put("{size}", ConstantData.MAXIMUM_NAMES + "");
            errorMessage = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessage);
        }
    }

    private void validateFirstName(String firstName, ArrayList<String> listError) {
        String errorMessage = "";
        if (!PersonValidation.isValidLastName(firstName)) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", firstName);
            listData.put("{valid}", Message.VALID_FN);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessage);
        }
        //Validation of last name size
        if (!PersonValidation.isValidSize(firstName, ConstantData.MAXIMUM_NAMES)) {
            listData.clear();
            listData.put("{typeData}", Message.FIRST_NAME);
            listData.put("{data}", firstName);
            listData.put("{size}", ConstantData.MAXIMUM_NAMES + "");
            errorMessage = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessage);

        }
    }

    private void validationGenre(String genre, ArrayList<String> listError) {

        if (!PersonValidation.isValidGenre(genre)) {
            String errorMessage = "";
            listData.clear();
            listData.put("{typeData}", Message.GENRE);
            listData.put("{data}", genre);
            listData.put("{valid}", Message.VALID_G);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessage);
        }
    }

    private void validationBirthday(String birthday, ArrayList<String> listError) {
        String errorMessage = "";
        if (!PersonValidation.isValidBirthday(birthday)) {
            listData.clear();
            listData.put("{typeData}", Message.BIRTHDAY);
            listData.put("{data}", birthday);
            listData.put("{valid}", Message.VALID_B);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessage);
        }
        //Validation of birthday
        if (!PersonValidation.isValidAge(birthday, ageMinimum)) {
            listData.clear();
            errorMessage = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
            listError.add(errorMessage);
        }
    }
}
