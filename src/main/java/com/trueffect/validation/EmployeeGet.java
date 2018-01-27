package com.trueffect.validation;

import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeGet extends PersonGet {

    private EmployeeValidation employeeValidation;
    private DateValidation dateValidation;
    private ObjectValidation objectValidation;

    public EmployeeGet() {
        dateValidation = new DateValidation();
        employeeValidation = new EmployeeValidation();
        objectValidation = new ObjectValidation();
    }

    public Either complyCondition(String typeId,
            String identifier,
            String lastName,
            String firstName,
            String genre,
            String birthdayStart,
            String birthdayEnd,
            String dateOfHire,
            String job) {

        ArrayList<String> listError = new ArrayList<String>();

        Either eitherPerson = super.complyCondition(typeId, identifier, lastName, firstName, genre, birthdayStart, birthdayEnd);
        listError.addAll(eitherPerson.getListError());
        if (StringUtils.isNotBlank(dateOfHire)) {
            dateValidation.isValidDate(ConstantData.DATE_OF_HIRE,dateOfHire, listError);
        }
        if (StringUtils.isNotBlank(job)) {
            employeeValidation.verifyJob(job, listError);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
