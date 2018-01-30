package com.trueffect.validation;

import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class PersonGet {

    private PersonValidation personValidation;
    private ObjectValidation objectValidation;
    private DateValidation dateValidation;

    public PersonGet() {
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
        String typeDataBirthdayEnd = ConstantData.BIRTHDAY + " " + ConstantData.END;
        String typeDataBirthdayStart = ConstantData.BIRTHDAY + " " + ConstantData.START;
        if (StringUtils.isNotBlank(birthdayStart)) {
            if (StringUtils.isNotBlank(birthdayEnd)) {
                dateValidation.isValidDate(typeDataBirthdayStart, birthdayStart, listError);
                dateValidation.isValidDate(typeDataBirthdayEnd, birthdayEnd, listError);
            } else {

                dateValidation.emptyDate(typeDataBirthdayEnd, listError);
            }
        } else if (StringUtils.isNotBlank(birthdayEnd)) {

            dateValidation.emptyDate(typeDataBirthdayStart, listError);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
