package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.JobName;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class Permission {

    private HashMap<String, String> listData;
    private String nameObject;

    public Permission() {
        listData = new HashMap<String, String>();
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public Either checkUserPermission(Connection connection, long idUserModify, String operation) {
        Either eitherJob = JobCrud.getJobOf(connection, idUserModify);
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        if (eitherJob.haveModelObject()) {
            String nameJob = ((Job) eitherJob.getFirstObject()).getNameJob();
            JobName employee = JobName.valueOf(nameJob);
            switch (employee) {
                case ADMIN:
                    return new Either();
                case MGR:
                    return new Either();
                default:
                    listData.clear();
                    listData.put(ConstantData.OPERATION, operation);
                    listData.put(ConstantData.TYPE_DATA, nameObject);
                    String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
                    listError.add(errorMgs);
                    return new Either(CodeStatus.FORBIDDEN, listError);
            }
        } else {
            listData.clear();
            listData.put(ConstantData.OPERATION, operation);
            String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION_OPERATION, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }

    public Either getPerson(Connection connection, long idPerson, String status, String operation) {
        Either eitherRes = new Either();
        Person person = new Person();
        ArrayList<String> listError = new ArrayList<String>();
        try {
            eitherRes = PersonCrud.getPerson(connection, idPerson, status);
            person = (Person) eitherRes.getFirstObject();
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        if (person.isEmpty()) {
            listData.clear();
            listData.put(ConstantData.OPERATION, operation);
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND_USER_REQUEST, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.NOT_FOUND, listError);
        }
        return eitherRes;
    }
}
