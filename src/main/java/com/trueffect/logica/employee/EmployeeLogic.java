package com.trueffect.logica.employee;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.logica.permission.Permission;
import com.trueffect.logica.person.PersonValidationsDB;
import com.trueffect.model.Employee;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.EmployeeCrud;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import com.trueffect.validation.EmployeeCreate;
import com.trueffect.validation.PersonValidation;
import com.trueffect.validation.RenterUserUpdate;
import java.sql.Connection;
import java.util.ArrayList;
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
    
    public Either createEmployee(int idUserCreate, Employee employee, EmployeeCreate employeeCreate) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of permission 
            Permission permission = new Permission();
            eitherRes = permission.checkUserPermission(connection, idUserCreate);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(employee);
            OperationString.formatOfNameJob(employee);
            eitherRes = employeeCreate.complyCondition(employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidationsDB.veriryDataInDataBase(connection, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Isert Person
            eitherRes = PersonCrud.insertPerson(connection, idUserCreate, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            String typeIdentifier = employee.getTypeIdentifier();
            String identifier = employee.getIdentifier();
            Either eitherInserted = PersonCrud.getPersonByIdentifier(connection, typeIdentifier, identifier);
            int idPerson = ((Person) eitherInserted.getFirstObject()).getId();
            employee.setId(idPerson);
            //Isert data job
            eitherRes = EmployeeCrud.insertDataJob(connection, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Isert phones
            eitherRes = EmployeeCrud.insertPhone(connection, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            // Get the employees without the phones
            eitherRes = EmployeeCrud.getEmployeeByIdentifier(connection, typeIdentifier, identifier);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Employee employeeInserted = (Employee) eitherRes.getFirstObject();
            // Get phones
            eitherRes = EmployeeCrud.getPhones(connection, idPerson);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<Integer> phones = ((Employee) eitherRes.getFirstObject()).getPhones();
            employeeInserted.setPhones(phones);
            eitherRes = new Either(CodeStatus.CREATED, employeeInserted);
            //Commit
            OperationDataBase.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }
    
    public Either getEmployee(String typeId, String identifier) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of permission 

            //eitherRes = PersonCrud.insertPerson(connection, idUserWhoCreate, employee);
            eitherRes = EmployeeCrud.getEmployeeByIdentifier(connection, typeId, identifier);
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
    
    public Either update(Employee employee, int idEmployee, int idModifyUser) {
         Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of data
            Permission permission = new Permission();
            eitherRes = permission.checkUserPermission(connection, idModifyUser);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidation.verifyId(employee, idEmployee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
//            eitherRes = getEmployee(connection, idEmployee, "Active");
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
//            eitherRes = JobCrud.getJobOf(connection, idModifyUser);
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
//            //update is a class to specifically validate the conditions to update a person
//            RenterUserUpdate rentUserUpdate = new RenterUserUpdate(
//                    ((Job) eitherRes.getFirstObject()).getNameJob(),
//                    ConstantData.MINIMUM_AGE_RENTER);
//            OperationString.formatOfTheName(person);
//            eitherRes = rentUserUpdate.complyCondition(person);
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
//            eitherRes = PersonValidationsDB.verifyDataUpdate(connection, idRenter, person);
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
//            eitherRes = PersonCrud.updateRenterUser(connection, idRenter, idUserModify, person);
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
//            eitherRes = PersonCrud.getPerson(connection, idRenter, "Active");
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
            OperationDataBase.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }
}
