package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author santiago.mamani
 */
public class DateValidation {

    protected HashMap<String, String> listData;

    public DateValidation() {
        listData = new HashMap<String, String>();
    }

    public boolean isValidDate(String date, ArrayList<String> listError) {
        boolean validDate = true;
        if (!DateOperation.isValidDateFormat(date)) {
            validDate = false;
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.BIRTHDAY);
            listData.put(ConstantData.DATA, date);
            listData.put(ConstantData.VALID, ConstantData.VALID_DATE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        return validDate;
    }

    public boolean verifyDateRangeValid(String date, ArrayList<String> listError) {
        boolean rangeValid = true;
        if (!DateOperation.dateIsInRangeValid(date)) {
            rangeValid = false;
            listData.clear();
            listData.put(ConstantData.DATA, date);
            listData.put(ConstantData.DATA_TWO, DateOperation.getDataCurrent());
            String errorMessages = OperationString.generateMesage(Message.DATE_FUTURE, listData);
            listError.add(errorMessages);
        }
        return rangeValid;
    }
}
