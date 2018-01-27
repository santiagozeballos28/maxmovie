package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Santiago
 */
public class EmployeeUpdate extends PersonUpdate {

    private EmployeeValidation employeeValidation;
    private DateValidation dateValidation;
    private ObjectValidation objectValidation;
    private String birthdayCurrent;

    public EmployeeUpdate(String job, int ageMinimum) {
        super(job, ageMinimum);
        employeeValidation = new EmployeeValidation();
        dateValidation = new DateValidation();
        objectValidation = new ObjectValidation();
        birthdayCurrent = "";
    }

    public void setBirthdayCurrent(String birthdayCurrent) {
        this.birthdayCurrent = birthdayCurrent;
    }

    @Override
    public Either complyCondition(ModelObject resource) {
        Employee employee = (Employee) resource;
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherPerson = super.complyCondition(resource);
        listError.addAll(eitherPerson.getListError());
        //Validation date of hire
        if (StringUtils.isNotBlank(employee.getDateOfHire())) {
            //the birthday is sent, because it is necessary that the date of hire be consistent with the birthday
            validationDateOfHire(employee.getDateOfHire(), employee.getBirthday(), listError);
        }
        //Validation date of hire
        if (StringUtils.isNotBlank(employee.getAddress())) {
            objectValidation.verifySize(ConstantData.ADDRESS, employee.getAddress(), ConstantData.MAX_LENGTH_ADDRESS, listError);
        }
        //Validation date of hire
        if (StringUtils.isNotBlank(employee.getJob())) {
            employeeValidation.verifyJob(employee.getJob(), listError);
        }
        //Validation  phones
        employeeValidation.verifyPhones(employee.getPhones(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private void validationDateOfHire(String dateOfHire, String birthday, ArrayList<String> listError) {
        String errorMessage = "";
        boolean validDateOfHire = dateValidation.isValidDate(dateOfHire, listError);
        boolean validDateOfHireRange;
        if (validDateOfHire) {
            validDateOfHireRange = dateValidation.verifyDateRangeValid(dateOfHire, listError);
            boolean validDateFormatBirthday = true;
            boolean validDateRangeBirthday = true;
            String birthdayAux = birthdayCurrent;
            if (StringUtils.isNotBlank(birthday)) {
                birthdayAux = birthday;
                validDateFormatBirthday = DateOperation.isValidDateFormat(birthday);
                validDateRangeBirthday = DateOperation.dateIsInRangeValid(birthday);
            }
            if (validDateOfHireRange && validDateFormatBirthday && validDateRangeBirthday) {
                employeeValidation.birthdayLessDateOfHire(birthdayAux, dateOfHire, listError);
            }
        }
    }
}
