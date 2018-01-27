package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeValidation {

    HashMap<String, String> listData;

    public EmployeeValidation() {
        listData = new HashMap<String, String>();
    }

    public boolean birthdayLessDateOfHire(String birthday, String dateOfHire, ArrayList<String> listError) {
        boolean rangeValid = true;
        if (!DateOperation.isLess(birthday, dateOfHire)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.DATE_OF_HIRE);
            listData.put(ConstantData.DATA, dateOfHire);
            listData.put(ConstantData.TYPE_DATA_TWO, ConstantData.BIRTHDAY);
            listData.put(ConstantData.DATA_TWO, birthday);
            String errorMessages = OperationString.generateMesage(Message.DATE_INCOHERENT, listData);
            listError.add(errorMessages);
        }
        return rangeValid;
    }

    public void verifyJob(String job, ArrayList<String> listError) {
        if (!EmployeeValidationUtil.isValidJob(job.toUpperCase())) {
            String validNameJob
                    = ConstantData.JobName.MGR.getDescriptionJobName() + ", "
                    + ConstantData.JobName.CSHR.getDescriptionJobName() + ", "
                    + ConstantData.JobName.CC.getDescriptionJobName();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.JOB);
            listData.put(ConstantData.DATA, job);
            listData.put(ConstantData.VALID, validNameJob);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyPhones(ArrayList<Integer> phones, ArrayList<String> listError) {
        if (phones.size() < ConstantData.MIN_AMOUNT_PHONE) {
            listData.clear();
            listData.put(ConstantData.DATA, ConstantData.MIN_AMOUNT_PHONE + "");
            String errorMessages = OperationString.generateMesage(Message.REFERENCE_PHONE, listData);
            listError.add(errorMessages);
        }
        for (int i = 0; i < phones.size(); i++) {
            if (!EmployeeValidationUtil.isValidPhone(phones.get(i))) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.PHONE);
                listData.put(ConstantData.DATA, phones.get(i) + "");
                listData.put(ConstantData.VALID, ConstantData.VALID_PHONE);
                String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
                listError.add(errorMessages);
            }
        }
    }
}
