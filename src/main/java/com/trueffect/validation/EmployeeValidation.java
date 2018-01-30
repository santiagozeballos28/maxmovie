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

    public void verifyJob(String job, ArrayList<String> listError) {
        if (!EmployeeValidationUtil.isValidJob(job.trim().toUpperCase())) {
            String validNameJob
                    = ConstantData.JobName.MGR.getDescriptionJobName() + ", "
                    + ConstantData.JobName.CSHR.getDescriptionJobName() + ", "
                    + ConstantData.JobName.CC.getDescriptionJobName();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.JOB);
            listData.put(ConstantData.DATA, job.trim());
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
