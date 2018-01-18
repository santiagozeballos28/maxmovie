package com.trueffect.sql.crud;

import com.trueffect.model.BondAssigned;
import com.trueffect.model.Salary;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class SalaryCrud {

    public Either updateStatus(Connection connection, ArrayList<ModelObject> updateAssignBonds, String status) {
        Statement query = null;
        try {
            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < updateAssignBonds.size(); i++) {
                BondAssigned bondAssigned = (BondAssigned) updateAssignBonds.get(i);
                sql = sql
                        = "UPDATE SALARY\n"
                        + "   SET end_date = current_date , "
                        + "       status = '" + status + "'"
                        + " WHERE id_person = " + bondAssigned.getIdPerson() + ";";
            }
            query.execute(sql);
            if (query != null) {
                query.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getAll(Connection connection) {

        try {
            Statement query = (Statement) connection.createStatement();
            String sql
                    = "SELECT id_employee, "
                    + "       net_salary, "
                    + "       bond, "
                    + "       liquid_salary, "
                    + "       end_date, "
                    + "       status\n"
                    + "  FROM salary;";
            ResultSet rs = query.executeQuery(sql);
            Either eitherRes = new Either();
            while (rs.next()) {

                Salary salary = new Salary(
                        rs.getInt("id_employee"),
                        rs.getDouble("net_salary"),
                        rs.getDouble("bond"),
                        rs.getDouble("liquid_salary"),
                        rs.getString("end_date"),
                        rs.getString("status"));
                eitherRes.addModeloObjet(salary);
            }
            if (query != null) {
                query.close();
            }
            eitherRes.setCode(CodeStatus.CREATED);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either insert(Connection connection, ArrayList<Salary> listSalary) {
        Statement query = null;
        try {

            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < listSalary.size(); i++) {
                Salary salary = listSalary.get(i);
                sql = sql
                        + "INSERT INTO salary("
                        + "id_employee, "
                        + "net_salary, "
                        + "bond, "
                        + "liquid_salary, "
                        + "end_date, "
                        + "status)"
                        + "VALUES ("
                        + salary.getIdEmployee() + ","
                        + salary.getNetSalary() + " ,"
                        + salary.getBond() + ","
                        + salary.getLiquidSalary() + ","
                        + "null,"
                        + "'Active');";
            }

            //ident
            query.execute(sql);
            if (query != null) {
                query.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

}
