package com.trueffect.logica;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.DataJob;
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
import com.trueffect.tools.ConstantData.Crud;
import com.trueffect.tools.ConstantData.ObjectMovie;
import com.trueffect.tools.ConstantData.Status;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import com.trueffect.validation.EmployeeCreate;
import com.trueffect.validation.EmployeeUpdate;
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
        String object = ObjectMovie.Employee.name();
        listData = new HashMap<String, String>();
        permission = new Permission();
        permission.setNameObject(object);
    }

    public Either createEmployee(long idUserCreate, boolean enabledRenterUser, Employee employee, EmployeeCreate employeeCreate) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String create = Crud.create.name();
            EmployeeValidationDB employeeValidationDB = new EmployeeValidationDB(create);
            //Validation of permission           
            eitherRes = permission.checkUserPermission(connection, idUserCreate, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //the names are converted into a valid format (example, sanTiAgo = Santiago)
            OperationString.formatOfTheName(employee);
            //the name job is converted into a valid format (example, CshR = CSHR)
            OperationString.formatOfNameJob(employee);
            eitherRes = employeeCreate.complyCondition(employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = employeeValidationDB.veriryDataInDataBase(connection, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            String identifier = employee.getIdentifier().toUpperCase();
            String typeIdentifier = employee.getTypeIdentifier().toUpperCase();
            String genre = employee.getGenre().toUpperCase();
            employee.setIdentifier(identifier);
            employee.setTypeIdentifier(typeIdentifier);
            employee.setGenre(genre);
            //Insert Person
            eitherRes = PersonCrud.insertPerson(connection, idUserCreate, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Either eitherInserted = PersonCrud.getPersonByIdentifier(connection, typeIdentifier, identifier);
            long idEmployee = ((Person) eitherInserted.getFirstObject()).getId();
            employee.setId(idEmployee);

            eitherRes = JobCrud.getJobOfName(connection, employee.getJob());
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            long idJob = ((Job) eitherRes.getFirstObject()).getId();
            //Create object DataJob
            DataJob dataJob = new DataJob(idEmployee, idJob, employee.getDateOfHire(), employee.getAddress());
            //Insert data job
            eitherRes = EmployeeCrud.insertDataJob(connection, dataJob, enabledRenterUser);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Insert phones
            ArrayList<Integer> listPhones = employee.getPhones();
            eitherRes = EmployeeCrud.insertPhone(connection, idUserCreate, idEmployee, listPhones);
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
            eitherRes = EmployeeCrud.getPhones(connection, idEmployee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<Integer> phones = ((Employee) eitherRes.getFirstObject()).getPhones();
            employeeInserted.setPhones(phones);
            eitherRes = new Either(CodeStatus.CREATED, employeeInserted);
            //Commit
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either getEmployee(String typeId, String identifier) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            eitherRes = EmployeeCrud.getEmployeeByIdentifier(connection, typeId, identifier);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either update(Employee employee, long idEmployee, long idModifyUser) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            OperationModel operationModel = new OperationModel();
            String statusActive = Status.Active.name();
            String update = Crud.update.name();
            String nameEmployee = ObjectMovie.Employee.name();
            EmployeeValidationDB employeeValidationDB = new EmployeeValidationDB(update);
            //Validation of data
            eitherRes = permission.checkUserPermission(connection, idModifyUser, update);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = operationModel.verifyId(idEmployee, employee.getId(), nameEmployee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //employee.setId(idEmployee);
            eitherRes = getEmployee(connection, idEmployee, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = JobCrud.getJobOf(connection, idModifyUser);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //the names are converted into a valid format (example, sanTiAgo = Santiago)
            OperationString.formatOfTheName(employee);
            //the name job is converted into a valid format (example, cusTOm caRe = customCare)
            OperationString.formatOfNameJob(employee);
            //update is a class to specifically validate the conditions to update a person
            EmployeeUpdate employeeUpdate
                    = new EmployeeUpdate(
                            ((Job) eitherRes.getFirstObject()).getNameJob(),
                            ConstantData.MIN_AGE_EMPLOYEE);
            eitherRes = employeeUpdate.complyCondition(employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //add aphostrophe "'".
            OperationString.addApostrophe(employee);
            eitherRes = employeeValidationDB.verifyDataUpdate(connection, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            String identifier = OperationString.toUpperCase(employee.getIdentifier());
            String typeIdentifier = OperationString.toUpperCase(employee.getTypeIdentifier());
            String genre = OperationString.toUpperCase(employee.getGenre());
            employee.setIdentifier(identifier);
            employee.setTypeIdentifier(typeIdentifier);
            employee.setGenre(genre);
            eitherRes = PersonCrud.updatePerson(connection, idModifyUser, employee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            System.out.println("Despues de insertar employee");
            String nameJob = employee.getJob();
            eitherRes = getJob(connection, nameJob, idEmployee);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            System.out.println("get Job");
            Job job = (Job) eitherRes.getFirstObject();
            if (employee.haveDataJob()) {
                eitherRes = EmployeeCrud.updateDataJob(connection, job.getId(), employee);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
            }
            System.out.println(" update data crud");
            ArrayList<Integer> phonesInput = employee.getPhones();
            eitherRes = getPhonesUpdate(connection, idEmployee, phonesInput);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            // Update the phones, if there is data to update
            ArrayList<ModelObject> listPhoneUpdate = eitherRes.getListObject();
            if (!listPhoneUpdate.isEmpty()) {
                eitherRes = EmployeeCrud.updatePhone(connection, idModifyUser, idEmployee, listPhoneUpdate);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
            }
            System.out.println(" update PHONE");
            eitherRes = getPhonesInsert(connection, idEmployee, phonesInput);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            System.out.println(" getPhonesInsert(connection, idEmployee, phonesInput);");
            ArrayList<ModelObject> listPhoneInsert = eitherRes.getListObject();
            if (!listPhoneInsert.isEmpty()) {
                ArrayList<Integer> listPhonesI = getListNumberPhones(listPhoneInsert);
                eitherRes = EmployeeCrud.insertPhone(connection, idModifyUser, idEmployee, listPhonesI);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
            }
            System.out.println(" insert PHONE");
            //estoy aqui
            eitherRes = getEmployee(connection, idEmployee, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either getEmployee(Connection connection, long idEmployee, String status) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        Employee employee = new Employee();
        try {
            Either eitherEmployee = EmployeeCrud.getEmployee(connection, idEmployee, status);
            employee = (Employee) eitherEmployee.getFirstObject();
            if (employee.isEmpty()) {
                String object = ObjectMovie.Employee.name();
                listData.clear();
                listData.put(ConstantData.OBJECT, object);
                String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.NOT_FOUND, listError);
            }
            Either eitherPhone = EmployeeCrud.getPhones(connection, idEmployee);
            ArrayList<Integer> phones = ((Employee) eitherPhone.getFirstObject()).getPhones();
            employee.setPhones(phones);
            eitherRes = new Either(CodeStatus.CREATED, employee);
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        return eitherRes;
    }

    /*
     *when you want to update the job you search for the name you want to add,
     *if you do not update the office you will find your current job.
     */
    private Either getJob(Connection connection, String nameJob, long idEmployee) {
        if (StringUtils.isNotBlank(nameJob)) {
            return JobCrud.getJobOfName(connection, nameJob);
        } else {
            return JobCrud.getJobOf(connection, idEmployee);
        }
    }

    private Either getPhonesUpdate(Connection connection, long idEmployee, ArrayList<Integer> phonesInput) {
        //list of phones that are in the database
        ArrayList<ModelObject> phonesDataBase = new ArrayList<ModelObject>();
        Either eitherPhone = new Either();
        try {
            eitherPhone = EmployeeCrud.getDetailOfPhones(connection, idEmployee);
            phonesDataBase = eitherPhone.getListObject();
        } catch (Exception e) {
            return eitherPhone;
        }
        String statusActive = Status.Active.name();
        String statusInactive = Status.Inactive.name();
        //Lis to update phones
        ArrayList<ModelObject> phoneOfUdate = new ArrayList<ModelObject>();
        for (ModelObject modelObject : phonesDataBase) {
            Phone phoneDB = (Phone) modelObject;
            int numberPhoneDB = phoneDB.getNumberPhone();
            boolean findPhone = false;
            int j = 0;
            while (j < phonesInput.size() && !findPhone) {
                int numberPhoneI = phonesInput.get(j);
                if (numberPhoneDB == numberPhoneI) {
                    findPhone = true;
                    if (!phoneDB.isActive()) {
                        phoneDB.setStatus(statusActive);
                        phoneOfUdate.add(phoneDB);
                    }
                }
                j++;
            }
            if (!findPhone) {
                if (phoneDB.isActive()) {
                    phoneDB.setStatus(statusInactive);
                    phoneOfUdate.add(phoneDB);
                }
            }
        }
        Either eitherRes = new Either();
        eitherRes.setListObject(phoneOfUdate);
        return eitherRes;
    }

    private Either getPhonesInsert(Connection connection, long idEmployee, ArrayList<Integer> phonesInput) {
        //list of phones that are in the database
        ArrayList<ModelObject> phonesDataBase = new ArrayList<ModelObject>();
        Either eitherPhone = new Either();
        try {
            eitherPhone = EmployeeCrud.getDetailOfPhones(connection, idEmployee);
            phonesDataBase = eitherPhone.getListObject();
        } catch (Exception e) {
            return eitherPhone;
        }
        //Lis to insert phones
        ArrayList<ModelObject> phonesToInsert = new ArrayList<ModelObject>();
        for (Integer numberPhoneI : phonesInput) {

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

    public ArrayList<Integer> getListNumberPhones(ArrayList<ModelObject> listPhone) {
        ArrayList<Integer> resPhones = new ArrayList<Integer>();
        for (int i = 0; i < listPhone.size(); i++) {
            int numberPhone = ((Phone) listPhone.get(i)).getNumberPhone();
            resPhones.add(numberPhone);
        }
        return resPhones;
    }

    public Either get(long idUserSearch, String typeId, String identifier, String lastName, String firstName, String genre, String dateOfHire, String job) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = Crud.get.name();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
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
            String jobName = "";
            if (StringUtils.isNotBlank(job)) {
                jobName = job.toUpperCase();
            }
            eitherRes = EmployeeCrud.getEmployeeBy(connection, typeId, identifier, lastName, firstName, genre, dateOfHire, jobName);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationModel operationModel = new OperationModel();
            DataBasePostgres.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either updateStatus(long idEmployee, long idUserModify, String status) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String delete = Crud.delete.name();
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserModify, delete);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            eitherRes = getEmployee(connection, idEmployee, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation Status(Active, Inactive)
            OperationModel operationModel = new OperationModel();
            eitherRes = operationModel.verifyStatus(connection, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = PersonCrud.updateStatusPerson(connection, idEmployee, idUserModify, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getEmployee(connection, idEmployee, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    public Either verifyPhonesDuplicates(long id, ArrayList<ModelObject> listPhone) {
        ArrayList<Integer> phonesDuplicates = new ArrayList<Integer>();
        ArrayList<String> listError = new ArrayList<String>();
        for (ModelObject modelObject : listPhone) {
            Phone phone = (Phone) modelObject;
            if (id != phone.getIdPerson()) {
                phonesDuplicates.add(phone.getNumberPhone());
            }
        }
        if (!phonesDuplicates.isEmpty()) {
            listData.put(ConstantData.TYPE_DATA, ConstantData.PHONE);
            listData.put(ConstantData.DATA, phonesDuplicates.toString());
            String errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
