package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Status;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

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
            String statusNew = StringUtils.capitalize(status.trim().toLowerCase());
            Status statusPerson = Status.valueOf(statusNew);
            return new Either();
        } catch (Exception e) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.STATUS);
            listData.put(ConstantData.DATA, status);
            listData.put(ConstantData.VALID, Status.Active + ", " + Status.Inactive);
            String errorMgs = OperationString.generateMesage(Message.NOT_VALID_THE_VALID_DATA_ARE, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }

    public Either verifyId(long idUrl, long idPayload, String nameObject) {
        ArrayList<String> listError = new ArrayList<String>();
        listData.clear();
        if (idPayload == 0) {
            listData.put(ConstantData.TYPE_DATA, ConstantData.ID);
            String errorMessage = OperationString.generateMesage(Message.EMPTY_DATA, listData);
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

    public Either verifyIdString(String idUrl, String idPayload, String nameObject) {
        ArrayList<String> listError = new ArrayList<String>();
        listData.clear();
        if (StringUtils.isBlank(idPayload)) {
            listData.put(ConstantData.TYPE_DATA, ConstantData.ID);
            String errorMessage = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessage);
            return new Either(CodeStatus.CONFLICT, listError);
        } else {
            String idUrlAux = idUrl.trim().toUpperCase();
            String idPayLoadAux = idPayload.trim().toUpperCase();
            if (!idUrlAux.equals(idPayLoadAux)) {
                listData.put(ConstantData.OBJECT, nameObject);
                String errorMessage = OperationString.generateMesage(Message.CONFLCT_ID, listData);
                listError.add(errorMessage);
                return new Either(CodeStatus.CONFLICT, listError);
            }
        }
        return new Either();
    }

    public Either verifyStatusModelObject(String nameObject, String statusObject, String statusNew) {
        ArrayList<String> listError = new ArrayList<String>();
        String statusObj = statusObject.trim().toLowerCase();
        String statusN = statusNew.trim().toLowerCase();
        if (statusObj.equals(statusN)) {
            listData.clear();
            listData.put(ConstantData.OBJECT, nameObject);
            listData.put(ConstantData.DATA, statusObject.trim());
            String errorMgs = OperationString.generateMesage(Message.STATUS_MODEL_OBJECT, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.OK, listError);
        }
        return new Either();
    }
}
