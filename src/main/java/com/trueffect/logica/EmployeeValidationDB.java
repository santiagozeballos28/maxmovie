package com.trueffect.logica;

import com.trueffect.messages.Message;
import com.trueffect.model.Employee;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.EmployeeCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.Crud;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeValidationDB extends PersonValidationsDB {

    private String nameCrud;

    public EmployeeValidationDB(String nameCrud) {
        this.nameCrud = nameCrud;
    }

    public Either veriryDataInDataBase(Connection connection, Employee employeeNew) {
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherPerson = super.veriryDataInDataBase(connection, employeeNew);
        listError.addAll(eitherPerson.getListError());
        Either eitherPhone = verifyPhone(connection, employeeNew);
        listError.addAll(eitherPhone.getListError());
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    public Either verifyPhone(Connection connection, Employee employee) {
        Either eitherPhone = EmployeeCrud.getPhonesByNumeber(connection, employee.getPhones(), null);
        ArrayList<ModelObject> listPhone = eitherPhone.getListObject();
        if (listPhone.isEmpty()) {
            System.out.println("Es VACIO lA lista DE telfonos");
            return new Either();
        }
        String errorMgs = "";
        EmployeeLogic employeeLogic = new EmployeeLogic();
        OperationModel operationModel = new OperationModel();
        ArrayList<String> listError = new ArrayList<String>();
        Crud crud = Crud.valueOf(nameCrud);
        switch (crud) {
            case create:
                System.out.println("Switch create");
                listData.clear();
                ArrayList<Integer> listNumbersPhones = employeeLogic.getListNumberPhones(listPhone);
                listData.put(ConstantData.TYPE_DATA, ConstantData.PHONE);
                listData.put(ConstantData.DATA, listNumbersPhones.toString());
                errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.BAD_REQUEST, listError);
            case update:
                 System.out.println("Switch update");
                return employeeLogic.verifyPhonesDuplicates(employee.getId(), listPhone);
        }
        return new Either();
    }

    public Either verifyDataUpdate(Connection connection, Employee employeeNew) {
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherPerson = super.verifyDataUpdate(connection, employeeNew);
        listError.addAll(eitherPerson.getListError());
        Either eitherPhone = verifyPhone(connection, employeeNew);
        listError.addAll(eitherPhone.getListError());
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
