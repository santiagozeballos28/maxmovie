package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Crud;
import com.trueffect.tools.ConstantData.JobName;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/*
 * @author santiago.mamani
 */
public class PersonUpdate implements DataCondition {

    protected HashMap<String, String> listData;
    private String job;
    private PersonValidation personValidation;
    private ObjectValidation objectValidation;
    private DateValidation dateValidation;
    private int ageMinimum;

    public PersonUpdate(String job, int ageMinimum) {
        listData = new HashMap<String, String>();
        this.job = job;
        personValidation = new PersonValidation();
        objectValidation = new ObjectValidation();
        dateValidation = new DateValidation();
        this.ageMinimum = ageMinimum;
    }

    @Override
    public Either complyCondition(ModelObject resource) {

        Person person = (Person) resource;
        ArrayList<String> listError = new ArrayList<String>();
        //Validation empty type identifier
        if (StringUtils.isNotBlank(person.getTypeIdentifier())) {
            intentUpdateTypeIdentifier(person, listError);
        }
        //Validation empty identifier
        if (StringUtils.isNotBlank(person.getIdentifier())) {
            intentUpdateIdentifier(person, listError);
        }
        //Validation empty last name
        if (StringUtils.isNotBlank(person.getLastName())) {
            //Validation of last name size
            objectValidation.verifySize(ConstantData.LAST_NAME, person.getLastName(), ConstantData.MAX_LENGTH_NAME, listError);
            //Validation of last name
            personValidation.verifyName(ConstantData.LAST_NAME, person.getLastName(), listError);
        }
        //Validation empty first name
        if (StringUtils.isNotBlank(person.getFirstName())) {
            //Validation of first name size
            objectValidation.verifySize(ConstantData.FIRST_NAME, person.getFirstName(), ConstantData.MAX_LENGTH_NAME, listError);
            //Validation of first name
            personValidation.verifyName(ConstantData.FIRST_NAME, person.getFirstName(), listError);
        }
        //Validation empty genre
        if (StringUtils.isNotBlank(person.getGenre())) {
            //Validation of genre
            personValidation.verifyGenre(person.getGenre(), listError);
        }
        //Validation empty birthday
        if (StringUtils.isNotBlank(person.getBirthday())) {
            //Validation of birthday (format date)
            boolean validBirthdayFormat = dateValidation.isValidDate(person.getBirthday(), listError);
            if (validBirthdayFormat) {
                //Validation of age
                dateValidation.verifyDateRangeValid(person.getBirthday(), listError);
                // Validation minimun age
                personValidation.verifyRequiredAge(person.getBirthday(), ageMinimum, listError);
            }
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private void intentUpdateTypeIdentifier(Person person, ArrayList<String> listError) {
        String nameAdmi = JobName.ADMIN.name();
        listData.clear();
        if (job.equals(nameAdmi)) {
            personValidation.isValidTypeIdentifier(person.getTypeIdentifier(), listError);
        } else {
            listData.put(ConstantData.OPERATION, Crud.update.name());
            listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
            String errorMessage = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMessage);
        }
    }

    private void intentUpdateIdentifier(Person person, ArrayList<String> listError) {
        String nameAdmi = JobName.ADMIN.name();
        listData.clear();
        if (job.equals(nameAdmi)) {
            personValidation.isValidIdentifier(person.getIdentifier(), listError);
        } else {
            listData.put(ConstantData.OPERATION, Crud.update.name());
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            String errorMessage = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMessage);
        }
    }
}
