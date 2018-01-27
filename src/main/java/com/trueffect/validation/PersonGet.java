package com.trueffect.validation;

import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class PersonGet {

    protected HashMap<String, String> listData;
    private PersonValidation personValidation;
    private ObjectValidation objectValidation;
    private DateValidation dateValidation;

    public PersonGet() {
        listData = new HashMap<String, String>();
        personValidation = new PersonValidation();
        dateValidation = new DateValidation();
    }

    public Either complyCondition(
            String typeId,
            String identifier,
            String lastName,
            String firstName,
            String genre,
            String birthdayStart,
            String birthdayEnd) {
        ArrayList<String> listError = new ArrayList<String>();
        if (StringUtils.isNotBlank(typeId)) {
            personValidation.isValidTypeIdentifier(typeId, listError);
        }
        if (StringUtils.isNotBlank(identifier)) {
            personValidation.isValidIdentifier(identifier, listError);
        }
        if (StringUtils.isNotBlank(lastName)) {
            personValidation.verifyName(ConstantData.LAST_NAME, lastName, listError);
        }
        if (StringUtils.isNotBlank(firstName)) {
            personValidation.verifyName(ConstantData.FIRST_NAME, firstName, listError);
        }
        if (StringUtils.isNotBlank(genre)) {
            personValidation.verifyGenre(genre, listError);
        }
        
        if(StringUtils.isNotBlank(birthdayStart)){
          if(StringUtils.isNotBlank(birthdayEnd)){
           
          }else{
          //se necesita la fecha de fin
          }
        }else{
          if(StringUtils.isNotBlank(birthdayEnd)){
           //se necesita fecha de inicio        
          }
        }   
        if (StringUtils.isNotBlank(birthdayStart) && StringUtils.isNotBlank(birthdayEnd)) {
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
}
