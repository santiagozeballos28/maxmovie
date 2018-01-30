
package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *This class will have any validation of the project in general
 * @author santiago.mamani
 */
public class ObjectValidation {
     private HashMap<String, String> listData;

    public ObjectValidation() {
        listData = new HashMap<String, String>();
    }

    public void verifySize(String typeData, String name, int sizeMax, ArrayList<String> listError) {
        String nameAux = OperationString.removeSpace(name);
        if (!ObjectValidationUtil.isValidSize(nameAux, sizeMax)) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, typeData);
            listData.put(ConstantData.DATA, name);
            listData.put(ConstantData.SIZE, sizeMax + "");
            String errorMessages = OperationString.generateMesage(Message.SIZE_MAX, listData);
            listError.add(errorMessages);
        }
    }  
}
