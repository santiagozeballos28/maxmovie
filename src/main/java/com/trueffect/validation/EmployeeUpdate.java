package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;

/**
 *
 * @author Santiago
 */
public class EmployeeUpdate extends PersonUpdate {

    public EmployeeUpdate(String job, int ageMinimum) {
        super(job, ageMinimum);
    }

    @Override
    public Either complyCondition(ModelObject resource) {

        Employee employee = (Employee) resource;
        ArrayList<String> listError = new ArrayList<String>();

        Either eitherPerson = super.complyCondition(resource);
        listError.addAll(eitherPerson.getListError());

        //Validation date of hire
        if (!PersonValidation.isEmpty(employee.getDateOfHire())) {
            validationDateOfHire(employee.getDateOfHire(), listError);
        }
        //Validation date of hire
        if (!PersonValidation.isEmpty(employee.getAddress())) {
            validationAddress(employee.getAddress(), listError);
        }
        //Validation date of hire
        if (!PersonValidation.isEmpty(employee.getJob())) {
            validationJob(employee.getJob(), listError);
        }

        //Validation  phones
        validationPhone(employee.getPhones(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {

            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private void validationDateOfHire(String dateOfHire, ArrayList<String> listError) {
        if (!EmployeeValidation.isValidDateOfHire(dateOfHire)) {
            String errorMessage = "";
            listData.clear();
            listData.put("{typeData}", Message.DATE_OF_HIRE);
            listData.put("{data}", dateOfHire);
            listData.put("{valid}", Message.VALID_B);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessage);
        }
    }

    private void validationAddress(String address, ArrayList<String> listError) {
        if (!EmployeeValidation.isValidAddress(address)) {
            String errorMessage = "";
            listData.clear();
            listData.put("{typeData}", Message.ADDRESS);
            listData.put("{data}", address);
            listData.put("{size}", ConstantData.MAXIMUM_ADDRESS + "");
            errorMessage = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessage);
        }
    }

    private void validationJob(String job, ArrayList<String> listError) {
        if (!EmployeeValidation.isValidJob(job)) {
            String errorMessage = "";
            listData.clear();
            listData.put("{typeData}", Message.JOB);
            listData.put("{data}", job);
            listData.put("{valid}", Message.VALID_JOB);
            errorMessage = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMessage);
        }
    }

    private void validationPhone(ArrayList<Integer> phones, ArrayList<String> listError) {
        if (phones.size() < ConstantData.MINIMUM_AMOUNT_PHONE) {
            listData.clear();
            listData.put("{data}", ConstantData.MINIMUM_AMOUNT_PHONE + "");
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
