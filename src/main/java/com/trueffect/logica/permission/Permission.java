/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.logica.permission;

import com.trueffect.messages.Message;
import com.trueffect.model.Job;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
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

    public Permission() {
        listData = new HashMap<String, String>();
    }
        public Either checkUserPermission(Connection connection, int idUserModify) {
        Either eitherJob = JobCrud.getJobOf(connection, idUserModify);
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        if (eitherJob.haveModelObject()) {
            String nameJob = ((Job) eitherJob.getFirstObject()).getNameJob();
            try {
                ConstantData.EmployeeWithPermissionModify employee = ConstantData.EmployeeWithPermissionModify.valueOf(nameJob);
                return new Either();
            } catch (Exception e) {
                listData.clear();
                listData.put("{typeData}", "Person");
                String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.FORBIDDEN, listError);
            }
        } else {
            listError.add(Message.NOT_FOUND_USER_MODIFY);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }
}
