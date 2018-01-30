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

    public boolean isValidDate(String typeData, String date, ArrayList<String> listError) {
        boolean validDate = true;
        if (!DateOperation.isValidDateFormat(date)) {
            validDate = false;
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, typeData);
            listData.put(ConstantData.DATA, date);
            listData.put(ConstantData.VALID, ConstantData.VALID_DATE);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        return validDate;
    }

    public boolean verifyDateRangeValid(String date, ArrayList<String> listError) {
        boolean rangeValid = true;
        if (!DateOperation.isValidDate(date)) {
            rangeValid = false;
            listData.clear();
            listData.put(ConstantData.DATA, date);
            listData.put(ConstantData.DATA_TWO, DateOperation.getDateCurrent());
            String errorMessages = OperationString.generateMesage(Message.DATE_FUTURE, listData);
            listError.add(errorMessages);
        }
        return rangeValid;
    }

    public void emptyDate(String typeDate, ArrayList<String> listError) {
        listData.clear();
        listData.put(ConstantData.TYPE_DATA, typeDate);
        String errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
        listError.add(errorMessages);
    }

    public boolean verifyIsLess(String typeDateFirst, String typeDateSecond, String dateFirst, String dateSecond, ArrayList<String> listError) {
        boolean isLess = true;
        if (!DateOperation.isLess(dateFirst, dateSecond)) {
            isLess = false;
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, typeDateFirst);
            listData.put(ConstantData.DATA, dateFirst.trim());
            listData.put(ConstantData.TYPE_DATA_TWO, typeDateSecond);
            listData.put(ConstantData.DATA_TWO, dateSecond.trim());
            String errorMessages = OperationString.generateMesage(Message.DATE_INCOHERENT, listData);
            listError.add(errorMessages);
        }
        return isLess;
    }

    public void verifyDiferenceYear(String typeDateFirst, String typeDateSecond, String dateFirst, String dateSecond, int minYear,String nameObject, ArrayList<String> listError) {
        if (DateOperation.diferenceYear(dateFirst, dateSecond) < minYear) {
            listData.clear();
             listData.put(ConstantData.OBJECT, nameObject);
            listData.put(ConstantData.DATA, minYear + "");
            String errorMessages = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
            listError.add(errorMessages);
        }
    }
}
