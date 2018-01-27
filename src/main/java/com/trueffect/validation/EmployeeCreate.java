package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeCreate extends PersonCreate {

    private EmployeeValidation employeeValidation;
    private DateValidation dateValidation;
    private ObjectValidation objectValidation;

    public EmployeeCreate(int ageMinimum) {
        super(ageMinimum);
        dateValidation = new DateValidation();
        employeeValidation = new EmployeeValidation();
        objectValidation = new ObjectValidation();
    }

    @Override
    public Either complyCondition(ModelObject resource) {
        Either eitherRes = verifyEmptyEmployee(resource);
        if (eitherRes.existError()) {
            return eitherRes;
        }
        return verifyDataEmployee(resource);
    }

    private Either verifyEmptyEmployee(ModelObject resource) {
        Employee employee = (Employee) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";

        Either eitherPerson = super.verifyEmpty(resource);
        listError.addAll(eitherPerson.getListError());
        //Validation empty date of hire
        if (StringUtils.isBlank(employee.getDateOfHire())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.DATE_OF_HIRE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty address
        if (StringUtils.isBlank(employee.getAddress())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.ADDRESS);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);

        }
        //Validation empty job
        if (StringUtils.isBlank(employee.getJob())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.JOB);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty phones
        if (employee.getPhones().isEmpty()) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.PHONE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    protected Either verifyDataEmployee(ModelObject resource) {
        Employee employee = (Employee) resource;
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherPerson = super.verifyData(resource);
        listError.addAll(eitherPerson.getListError());
        //Validation date of hire
        boolean validDateOfHire = dateValidation.isValidDate(ConstantData.DATE_OF_HIRE,employee.getDateOfHire(), listError);
        boolean validDateRange;
        if (validDateOfHire) {
            validDateRange = dateValidation.verifyDateRangeValid(employee.getDateOfHire(), listError);
            if (validDateOfHire && validDateRange) {
                employeeValidation.birthdayLessDateOfHire(employee.getBirthday(), employee.getDateOfHire(), listError);
            }
        }
        //Validation of addres size
        objectValidation.verifySize(ConstantData.ADDRESS, employee.getAddress(), ConstantData.MAX_LENGTH_ADDRESS, listError);
        //Validation of job
        employeeValidation.verifyJob(employee.getJob(), listError);
        //Validation the numbers phones
        employeeValidation.verifyPhones(employee.getPhones(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
