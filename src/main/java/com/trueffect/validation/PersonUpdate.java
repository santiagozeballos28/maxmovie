package com.trueffect.validation;

import com.trueffect.logica.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Crud;
import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.JobName;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * @author santiago.mamani
 */
public class PersonUpdate implements DataCondition {

    private String job;
    protected HashMap<String, String> listData;
    private int ageMinimum;

    public PersonUpdate(String job, int ageMinimum) {
        this.job = job;
        this.ageMinimum = ageMinimum;
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
        //Validation empty identifier
        if (!PersonValidation.isEmpty(renterUser.getIdentifier())) {
            intentUpdateIdentifier(renterUser, listError);
        }
        //Validation empty last name
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
        String nameAdmi = JobName.ADMIN.name();
        listData.clear();
        if (job.equals(nameAdmi)) {
            if (!PersonValidation.isValidTypeIdentifier(renterUser.getTypeIdentifier())) {
                String validTypeId
                        = TypeIdentifier.CI.getDescriptionIdentifier() + ", "
                        + TypeIdentifier.NIT.getDescriptionIdentifier() + ", "
                        + TypeIdentifier.PASS.getDescriptionIdentifier();
                listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
                listData.put(ConstantData.DATA, renterUser.getTypeIdentifier());
                listData.put(ConstantData.VALID, validTypeId);
                errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
                listError.add(errorMessage);
            }
        } else {
            listData.put(ConstantData.OPERATION, Crud.update.name());
            listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
            errorMessage = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMessage);
        }
    }

    private void intentUpdateIdentifier(Person renterUser, ArrayList<String> listError) {
        String errorMessage = "";
        String nameAdmi = JobName.ADMIN.name();
        listData.clear();
        if (job.equals(nameAdmi)) {
            if (!PersonValidation.isValidIdentifier(renterUser.getIdentifier())) {
                listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
                listData.put(ConstantData.DATA, renterUser.getIdentifier());

                errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
                listError.add(errorMessage);
            }
        } else {
            listData.put(ConstantData.OPERATION, Crud.update.name());
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            errorMessage = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMessage);
        }
    }

    private void validateLastName(String lastName, ArrayList<String> listError) {
        String errorMessage = "";
        if (!PersonValidation.isValidLastName(lastName)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
            listData.put(ConstantData.DATA, lastName);
            listData.put(ConstantData.VALID, ConstantData.VALID_LASTNAME);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessage);
        }
        //Validation of last name size
        if (!PersonValidation.isValidSize(lastName, ConstantData.MAX_LENGTH_NAME)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.LAST_NAME);
            listData.put(ConstantData.DATA, lastName);
            listData.put(ConstantData.SIZE, ConstantData.MAX_LENGTH_NAME + "");
            errorMessage = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessage);
        }
    }

    private void validateFirstName(String firstName, ArrayList<String> listError) {
        String errorMessage = "";
        if (!PersonValidation.isValidLastName(firstName)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.FIRST_NAME);
            listData.put(ConstantData.DATA, firstName);
            listData.put(ConstantData.VALID, ConstantData.VALID_FIRSTNAME);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessage);
        }
        //Validation of first name size
        if (!PersonValidation.isValidSize(firstName, ConstantData.MAX_LENGTH_NAME)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.FIRST_NAME);
            listData.put(ConstantData.DATA, firstName);
            listData.put(ConstantData.SIZE, ConstantData.MAX_LENGTH_NAME + "");
            errorMessage = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessage);
        }
    }

    private void validationGenre(String genre, ArrayList<String> listError) {

        if (!PersonValidation.isValidGenre(genre)) {
            String errorMessage = "";
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.GENRE);
            listData.put(ConstantData.DATA, genre);
            listData.put(ConstantData.VALID, GenrePerson.F.getNameGenre() + ", " + GenrePerson.M.getNameGenre());
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessage);
        }
    }

    private void validationBirthday(String birthday, ArrayList<String> listError) {
        String errorMessage = "";
        //Validation of birthday
        if (!DateOperation.isValidDateFormat(birthday)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.BIRTHDAY);
            listData.put(ConstantData.DATA, birthday);
            listData.put(ConstantData.VALID, ConstantData.VALID_BIRTHDAY);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessage);
        } else //Validation of age
        {
            if (!DateOperation.dateIsInRangeValid(birthday)) {
                listData.clear();
                listData.put(ConstantData.DATA, birthday);
                listData.put(ConstantData.DATA_TWO, DateOperation.getDataCurrent());
                errorMessage = OperationString.generateMesage(Message.DATE_FUTURE, listData);
                listError.add(errorMessage);
            }
            if (!DateOperation.yearIsGreaterThan(birthday, ageMinimum)) {
                listData.clear();
                listData.put(ConstantData.DATA, ageMinimum + "");
                errorMessage = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
                listError.add(errorMessage);
            }
        }
    }
}
