package com.trueffect.logica;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.conection.db.OperationDataBase;
import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.model.Phone;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.EmployeeCrud;
import com.trueffect.sql.crud.JobCrud;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.StatusPerson;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import com.trueffect.validation.EmployeeCreate;
import com.trueffect.validation.EmployeeUpdate;
import com.trueffect.validation.PersonValidation;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeLogic {

    private HashMap<String, String> listData;
    private Permission permission;

    public EmployeeLogic() {
        listData = new HashMap<String, String>();
        permission = new Permission();
    }

    public Either createEmployee(int idUserCreate, Employee employee, EmployeeCreate employeeCreate) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Validation of permission 
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
            eitherRes = permission.checkUserPermission(connection, idModifyUser);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidation.verifyId(employee, idEmployee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            employee.setId(idEmployee);
            eitherRes = getEmployee(connection, idEmployee, "Active");
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = JobCrud.getJobOf(connection, idModifyUser);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationString.formatOfTheName(employee);
            OperationString.formatOfNameJob(employee);
            //update is a class to specifically validate the conditions to update a person
            EmployeeUpdate employeeUpdate
                    = new EmployeeUpdate(
                            ((Job) eitherRes.getFirstObject()).getNameJob(),
                            ConstantData.MINIMUM_AGE_EMPLOYEE);
            eitherRes = employeeUpdate.complyCondition(employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonValidationsDB.verifyDataUpdate(connection, idEmployee, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updatePerson(connection, idEmployee, idModifyUser, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getJob(connection, employee, idEmployee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Job job = (Job) eitherRes.getFirstObject();
            if (employee.haveDataJob()) {
                eitherRes = EmployeeCrud.updateDataJob(connection, idEmployee, job.getId(), employee);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
            }
            eitherRes = getPhonesUpdate(connection, idEmployee, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            // Update the phones, if there is data to update
            ArrayList<ModelObject> listPhoneUpdate = eitherRes.getListObject();
            if (!listPhoneUpdate.isEmpty()) {
                eitherRes = EmployeeCrud.updatePhone(connection, idEmployee, listPhoneUpdate);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
            }
            eitherRes = getPhonesInsert(connection, idEmployee, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> listPhoneInsert = eitherRes.getListObject();
            if (!listPhoneInsert.isEmpty()) {
                ArrayList<Integer> listPhonesI = getListPhonesInsert(listPhoneInsert);
                employee.setPhones(listPhonesI);
                eitherRes = EmployeeCrud.insertPhone(connection, employee);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
            }
            eitherRes = getEmployee(connection, idEmployee, "Active");
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationDataBase.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either getEmployee(Connection connection, int idEmployee, String active) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        Employee employee = new Employee();
        try {
            Either eitherEmployee = EmployeeCrud.getEmployee(connection, idEmployee, active);
            employee = (Employee) eitherEmployee.getFirstObject();
            Either eitherPhone = EmployeeCrud.getPhones(connection, idEmployee);
            ArrayList<Integer> phones = ((Employee) eitherPhone.getFirstObject()).getPhones();
            employee.setPhones(phones);
            eitherRes = new Either(CodeStatus.CREATED, employee);
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        if (employee.isEmpty()) {
            listData.clear();
            listData.put("{object}", "Employee");
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.NOT_FOUND, listError);
        }
        return eitherRes;
    }

    private Either getJob(Connection connection, Employee employee, int idEmployee) {
        if (StringUtils.isNotBlank(employee.getJob())) {
            return JobCrud.getJobOfName(connection, employee.getJob());
        } else {
            return JobCrud.getJobOf(connection, idEmployee);
        }
    }

    private Either getPhonesUpdate(Connection connection, int idEmployee, Employee employee) {
        //list of phones that are in the database
        ArrayList<ModelObject> phonesDataBase = new ArrayList<ModelObject>();
        Either eitherPhone = new Either();
        try {
            eitherPhone = EmployeeCrud.getDetailOfPhones(connection, idEmployee);
            phonesDataBase = eitherPhone.getListObject();
        } catch (Exception e) {
            return eitherPhone;
        }
        //Lis of input phones
        ArrayList<Integer> phonesInput = employee.getPhones();
        //Lis to update phones
        ArrayList<ModelObject> phoneOfUdate = new ArrayList<ModelObject>();
        for (int i = 0; i < phonesDataBase.size(); i++) {

            Phone phoneDB = (Phone) phonesDataBase.get(i);
            int numberPhoneDB = phoneDB.getNumberPhone();
            boolean findPhone = false;
            int j = 0;
            while (j < phonesInput.size() && !findPhone) {
                int numberPhoneI = phonesInput.get(j);
                if (numberPhoneDB == numberPhoneI) {
                    findPhone = true;
                    if (!phoneDB.isActive()) {
                        phoneDB.setStatus("Active");
                        phoneOfUdate.add(phoneDB);
                    }
                }
                j++;
            }
            if (!findPhone) {
                if (phoneDB.isActive()) {
                    phoneDB.setStatus("Inactive");
                    phoneOfUdate.add(phoneDB);
                }
            }
        }
        Either eitherRes = new Either();
        eitherRes.setListObject(phoneOfUdate);
        return eitherRes;
    }

    private Either getPhonesInsert(Connection connection, int idEmployee, Employee employee) {
        //list of phones that are in the database
        ArrayList<ModelObject> phonesDataBase = new ArrayList<ModelObject>();
        Either eitherPhone = new Either();
        try {
            eitherPhone = EmployeeCrud.getDetailOfPhones(connection, idEmployee);
            phonesDataBase = eitherPhone.getListObject();
        } catch (Exception e) {
            return eitherPhone;
        }
        //Lis of input phones
        ArrayList<Integer> phonesInput = employee.getPhones();
        //Lis to insert phones
        ArrayList<ModelObject> phonesToInsert = new ArrayList<ModelObject>();
        for (int i = 0; i < phonesInput.size(); i++) {
            int numberPhoneI = phonesInput.get(i);
            int j = 0;
            boolean findPhone = false;
            while (j < phonesDataBase.size() && !findPhone) {
                Phone phoneDB = (Phone) phonesDataBase.get(j);
                int numberPhoneDB = phoneDB.getNumberPhone();
                if (numberPhoneI == numberPhoneDB) {
                    findPhone = true;
                }
                j++;
            }
            if (!findPhone) {
                phonesToInsert.add(new Phone(numberPhoneI));
            }
        }
        Either eitherRes = new Either();
        eitherRes.setListObject(phonesToInsert);
        return eitherRes;
    }

    private ArrayList<Integer> getListPhonesInsert(ArrayList<ModelObject> listPhoneInsert) {
        ArrayList<Integer> resPhones = new ArrayList<Integer>();
        for (int i = 0; i < listPhoneInsert.size(); i++) {
            int numberPhone = ((Phone) listPhoneInsert.get(i)).getNumberPhone();
            resPhones.add(numberPhone);
        }
        return resPhones;
    }

    public Either get(int idUserSearch, String typeId, String identifier, String lastName, String firstName, String genre, String dateOfHire, String job) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            if (StringUtils.isNotBlank(lastName)) {
                lastName = OperationString.generateLastName(lastName);
            }
            if (StringUtils.isNotBlank(firstName)) {
                firstName = OperationString.generateFirstName(firstName);
            }
            if (StringUtils.isNotBlank(job)) {
                job = OperationString.generateNameJob(job);
            }

            //eitherRes = PersonCrud.getEmployeeBy(connection, typeId, identifier, lastName, firstName, genre);
            eitherRes = EmployeeCrud.getEmployeeBy(connection, typeId, identifier, lastName, firstName, genre, dateOfHire, job);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationModel operationModel = new OperationModel();
            eitherRes = operationModel.addDescription(eitherRes);
            OperationDataBase.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
            OperationDataBase.connectionRollback(connection, eitherRes);
        } finally {
            OperationDataBase.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either updateStatus(int idEmployee, int idUserModify, String status) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserModify);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }

            eitherRes = getEmployee(connection, idEmployee, "");
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            //Validation Status(Active, Inactive)
            eitherRes = verifyStatus(connection, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updateStatusPerson(connection, idEmployee, idUserModify, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = EmployeeCrud.getEmployee(connection, idEmployee, "");
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

    private Either verifyStatus(Connection connection, String status) {
        ArrayList<String> listError = new ArrayList<String>();
        try {
            StatusPerson statusPerson = StatusPerson.valueOf(status);
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
