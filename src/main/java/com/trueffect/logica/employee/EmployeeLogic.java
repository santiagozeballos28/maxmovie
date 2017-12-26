package com.trueffect.logica.employee;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.logica.permission.Permission;
import com.trueffect.logica.person.PersonValidationsDB;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.util.OperationString;
import com.trueffect.validation.EmployeeCreate;
import java.sql.Connection;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeLogic {
     private HashMap<String, String> listData;

    public EmployeeLogic() {
        listData = new HashMap<String, String>();
    }

    public Either createEmployee(int idUserWhoCreate, Employee employee, EmployeeCreate employeeCreate) {
         Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of permission 
            Permission permission =  new Permission();
            eitherRes = permission.checkUserPermission(connection, idUserWhoCreate);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(employee);
            eitherRes = employeeCreate.complyCondition(employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidationsDB.veriryDataInDataBase(connection, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.insertPerson(connection, idUserWhoCreate, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationDataBase.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }


     
}
