package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeCreate extends PersonCreate {

    public EmployeeCreate(int ageMinimum) {
        super(ageMinimum);
    }

    @Override
    public Either complyCondition(ModelObject resource) {
        Either eitherRes = verifyEmptyEmployee(resource);
        if (eitherRes.existError()) {
            return eitherRes;
        }
        return verifyData(resource);
    }

    private Either verifyEmptyEmployee(ModelObject resource) {
        Employee employee = (Employee) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";

        Either eitherPerson = super.verifyEmpty(resource);
        listError.addAll(eitherPerson.getListError());
        //Validation empty type identifier
        if (PersonValidation.isEmpty(employee.getDateOfHire())) {
            listData.clear();
            listData.put("{typeData}", Message.DATE_OF_HIRE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty identifier
        if (PersonValidation.isEmpty(employee.getAddress())) {
            listData.clear();
            listData.put("{typeData}", Message.ADDRESS);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);

        }
        //Validation empty last name
        if (PersonValidation.isEmpty(employee.getJob())) {
            listData.clear();
            listData.put("{typeData}", Message.JOB);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty first name
        if (employee.getPhones().isEmpty()) {
            listData.clear();
            listData.put("{typeData}", Message.PHONE);
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
        String errorMessages = "";
        Either eitherPerson = super.verifyData(resource);
        listError.addAll(eitherPerson.getListError());

        //Validation of first name
        if (!EmployeeValidation.isValidDateOfHire(employee.getDateOfHire())) {
            listData.clear();
            listData.put("{typeData}", Message.DATE_OF_HIRE);
            listData.put("{data}", employee.getDateOfHire());
            listData.put("{valid}", Message.BIRTHDAY);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of first name
        if (!EmployeeValidation.isValidAddress(employee.getAddress())) {
            listData.clear();
            listData.put("{typeData}", Message.ADDRESS);
            listData.put("{data}", employee.getAddress());
            listData.put("{valid}", "AUMENTAR VALIDO");
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of first name
        if (!EmployeeValidation.isValidJob(employee.getJob())) {
            listData.clear();
            listData.put("{typeData}", Message.JOB);
            listData.put("{data}", employee.getAddress());
            listData.put("{valid}", Message.VALID_JOB);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of first name
        validPhones(employee.getPhones(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);

        }
        return new Either();
    }

    private void validPhones(ArrayList<Integer> phones, ArrayList<String> listError) {
        Either either = new Either();
        for (int i = 0; i < phones.size(); i++) {
            if (!EmployeeValidation.isValidPhone(phones.get(i))) {
                listData.clear();
                listData.put("{typeData}", Message.PHONE);
                listData.put("{data}", phones.get(i) + "");
                listData.put("{valid}", Message.VALID_PHONE);
                String errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
                listError.add(errorMessages);
            }
        }
    }
}
