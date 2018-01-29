package com.trueffect.validation;

import com.trueffect.logic.DateOperation;
import com.trueffect.messages.Message;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * @author santiago.mamani
 */
public class PersonValidation {

    HashMap<String, String> listData;

    public PersonValidation() {
        listData = new HashMap<String, String>();
    }
 
    public boolean isValidTypeIdentifier(String typeIdentifier, ArrayList<String> listError) {
        boolean validTypeIdentifier = true;
        if (!PersonValidationUtil.isValidTypeIdentifier(typeIdentifier)) {
            validTypeIdentifier = false;
            String validTypesId
                    = TypeIdentifier.CI.getDescriptionIdentifier() + ", "
                    + TypeIdentifier.NIT.getDescriptionIdentifier() + ", "
                    + TypeIdentifier.PASS.getDescriptionIdentifier();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.TYPE_IDENTIFIER);
            listData.put(ConstantData.DATA, typeIdentifier.trim());
            listData.put(ConstantData.VALID, validTypesId);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        return validTypeIdentifier;
    }

    public boolean isValidIdentifier(String identifier, ArrayList<String> listError) {
        boolean validIdentifier = true;
        if (!PersonValidationUtil.isValidIdentifier(identifier)) {
            validIdentifier = false;
            String validIden
                    = TypeIdentifier.CI.getDescriptionIdentifier() + " = " + ConstantData.ValidIdentifier.CI.getValidIdentifier() + ", "
                    + TypeIdentifier.PASS.getDescriptionIdentifier() + " = " + ConstantData.ValidIdentifier.PASS.getValidIdentifier() + ", "
                    + TypeIdentifier.NIT.getDescriptionIdentifier() + " = " + ConstantData.ValidIdentifier.NIT.getValidIdentifier();
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.DATA, identifier.trim());
            listData.put(ConstantData.VALID, validIden);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
        return validIdentifier;
    }

    public boolean verifyIdentifiers(String typeIdendifier, String identifier, ArrayList<String> listError) {
        boolean validIdentifier = true;
        if (!PersonValidationUtil.isValidIdentifier(typeIdendifier, identifier)) {
            TypeIdentifier typeId = TypeIdentifier.valueOf(typeIdendifier.toUpperCase());
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.TYPE_DATA_TWO, ConstantData.TYPE_IDENTIFIER);
            listData.put(ConstantData.DATA, identifier.trim());
            listData.put(ConstantData.DATA_TWO, typeId.getDescriptionIdentifier());
            String errorMessages = OperationString.generateMesage(Message.NOT_SAME_TYPE, listData);
            listError.add(errorMessages);
        }
        return validIdentifier;
    }

    public void verifyName(String typeData, String name, ArrayList<String> listError) {
        String nameAux = OperationString.generateName(name);
        if (!PersonValidationUtil.isValidName(nameAux)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, typeData);
            listData.put(ConstantData.DATA, name.trim());
            listData.put(ConstantData.VALID, ConstantData.VALID_NAME_PERSON);
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }

    public void verifyGenre(String genre, ArrayList<String> listError) {
        if (!PersonValidationUtil.isValidGenre(genre)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.GENRE);
            listData.put(ConstantData.DATA, genre.trim());
            listData.put(ConstantData.VALID, GenrePerson.F.getNameGenre() + ", " + GenrePerson.M.getNameGenre());
            String errorMessages = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMessages);
        }
    }
    public void verifyRequiredAge(String date, int ageMinimum, ArrayList<String> listError) {
        if (!DateOperation.yearIsGreaterThan(date, ageMinimum)) {
            listData.clear();
            listData.put(ConstantData.DATA, ageMinimum + "");
            String errorMessages = OperationString.generateMesage(Message.NOT_MEET_THE_AGE, listData);
            listError.add(errorMessages);
        }
    }
}
