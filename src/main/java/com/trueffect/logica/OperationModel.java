package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.PersonDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.TypeIdentifier;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class OperationModel {

    private HashMap<String, String> listData;

    public OperationModel() {
        listData = new HashMap<String, String>();
    }

    public Either verifyStatus(Connection connection, String status) {
        ArrayList<String> listError = new ArrayList<String>();
        try {
            ConstantData.Status statusPerson = ConstantData.Status.valueOf(status);
            return new Either();
        } catch (Exception e) {
            listData.clear();
            listData.put("{typeData}", "Status");
            listData.put("{data}", status);
            listData.put("{valid}", Message.VALID_STATUS);
            String errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }
}
