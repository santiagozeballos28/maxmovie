package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.JobName;
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
        return verifyDataEmployee(resource);
    }

    private Either verifyEmptyEmployee(ModelObject resource) {
        Employee employee = (Employee) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";

        Either eitherPerson = super.verifyEmpty(resource);
        listError.addAll(eitherPerson.getListError());
        //Validation empty date of hire
        if (PersonValidation.isEmpty(employee.getDateOfHire())) {
            listData.clear();
            listData.put("{typeData}", Message.DATE_OF_HIRE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty address
        if (PersonValidation.isEmpty(employee.getAddress())) {
            listData.clear();
            listData.put("{typeData}", Message.ADDRESS);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);

        }
        //Validation empty job
        if (PersonValidation.isEmpty(employee.getJob())) {
            listData.clear();
            listData.put("{typeData}", Message.JOB);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty phones
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

        //Validation date of hire
        if (!EmployeeValidation.isValidDateOfHire(employee.getDateOfHire())) {
            listData.clear();
            listData.put("{typeData}", Message.DATE_OF_HIRE);
            listData.put("{data}", employee.getDateOfHire());
            listData.put("{valid}", Message.VALID_BIRTHDAY);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation of job
        if (!EmployeeValidation.isValidAddress(employee.getAddress())) {
            listData.clear();
            listData.put("{typeData}", Message.ADDRESS);
            listData.put("{data}", employee.getAddress());
            listData.put("{size}", ConstantData.MAX_LENGTH_ADDRESS + "");
            errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
        //Validation of job
        if (!EmployeeValidation.isValidJob(employee.getJob())) {
            String validNameJob
                    = JobName.MGR.getDescriptionJobName() + ", "
                    + JobName.CSHR.getDescriptionJobName() + ", "
                    + JobName.CC.getDescriptionJobName();
            listData.clear();
            listData.put("{typeData}", Message.JOB);
            listData.put("{data}", employee.getJob());
            listData.put("{valid}", validNameJob);
            errorMessages = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation the numbers phones
        validPhones(employee.getPhones(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);

        }
        return new Either();
    }

    private void validPhones(ArrayList<Integer> phones, ArrayList<String> listError) {
        if (phones.size() < ConstantData.MIN_AMOUNT_PHONE) {
            listData.clear();
            listData.put("{data}", ConstantData.MIN_AMOUNT_PHONE + "");
            String errorMessages = OperationString.generateMesage(Message.REFERENCE_PHONE, listData);
            listError.add(errorMessages);
        }
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
