package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Status;
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
            listData.put(ConstantData.TYPE_DATA, "Status22");
            listData.put(ConstantData.DATA, status);
            listData.put(ConstantData.VALID, Status.Active + ", " + Status.Inactive);
            String errorMgs = OperationString.generateMesage(Message.NOT_VALID_DATA_THE_VALID_DATA_ARE, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }

    public Either verifyId(long idUrl, long idPayload, String nameObject) {
        ArrayList<String> listError = new ArrayList<String>();
        listData.clear();
        if (idPayload == 0) {
            listData.put(ConstantData.OBJECT, nameObject);
            String errorMessage = OperationString.generateMesage(Message.MANDATORY_ID, listData);
            listError.add(errorMessage);
            return new Either(CodeStatus.CONFLICT, listError);
        } else if (idUrl != idPayload) {
            listData.put(ConstantData.OBJECT, nameObject);
            String errorMessage = OperationString.generateMesage(Message.CONFLCT_ID, listData);
            listError.add(errorMessage);
            return new Either(CodeStatus.CONFLICT, listError);
        }
        return new Either();
    }
}
