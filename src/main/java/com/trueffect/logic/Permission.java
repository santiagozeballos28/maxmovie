package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.EmployeeCrud;
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
        JobCrud jobCrud = new JobCrud();
        Either eitherJob = jobCrud.getJobOf(connection, idUserModify);
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
            listData.put(ConstantData.TYPE_DATA, nameObject);
            String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
    }

    public Either checkUserPermissionCustomerCare(Connection connection, long idUser, String operation) {
        JobCrud jobCrud = new JobCrud();
        Either eitherJob = jobCrud.getJobOf(connection, idUser);
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        if (eitherJob.haveModelObject()) {
            String nameJob = ((Job) eitherJob.getFirstObject()).getNameJob();
            JobName employee = JobName.valueOf(nameJob);
            switch (employee) {
                case CC:
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
            listData.put(ConstantData.TYPE_DATA, nameObject);
            String errorMgs = OperationString.generateMesage(Message.NOT_HAVE_PERMISSION, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.FORBIDDEN, listError);
        }
    }

    public Either getPerson(Connection connection, long idPerson, String status, String operation) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        if (idPerson == 0) {
            listData.clear();
            listData.put(ConstantData.OPERATION, operation);
            String errorMgs = OperationString.generateMesage(Message.MANDATORY_IDENTIFY, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.FORBIDDEN, listError);
        }
        Person person = new Person();
        try {
            PersonCrud personCrud = new PersonCrud();
            eitherRes = personCrud.getPerson(connection, idPerson, status);
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

    public Either getEmployee(Connection connection, long idEmployee, String status) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        EmployeeCrud employeeCrud = new EmployeeCrud();
        Employee employee = new Employee();
        try {
            Either eitherEmployee = employeeCrud.getEmployee(connection, idEmployee, status);
            employee = (Employee) eitherEmployee.getFirstObject();
            if (employee.isEmpty()) {
                String object = ConstantData.ObjectMovie.Employee.name();
                listData.clear();
                listData.put(ConstantData.OBJECT, object);
                String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.NOT_FOUND, listError);
            }
            Either eitherPhone = employeeCrud.getPhones(connection, idEmployee);
            ArrayList<Long> phones = ((Employee) eitherPhone.getFirstObject()).getPhones();
            employee.setPhones(phones);
            eitherRes = new Either(CodeStatus.OK, employee);
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        return eitherRes;
    }

    public Either getRenterUser(Connection connection, long idRenterUser, String status) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        PersonCrud personCrud = new PersonCrud();
        Person person = new Person();
        try {
            Either eitherPerson = personCrud.getRenterUser(connection, idRenterUser, status);
            person = (Person) eitherPerson.getFirstObject();
            if (person.isEmpty()) {
                String object = ConstantData.ObjectMovie.RennterUser.name();
                listData.clear();
                listData.put(ConstantData.OBJECT, object);
                String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.NOT_FOUND, listError);
            }
            eitherRes = new Either(CodeStatus.OK, person);
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        return eitherRes;
    }
}
