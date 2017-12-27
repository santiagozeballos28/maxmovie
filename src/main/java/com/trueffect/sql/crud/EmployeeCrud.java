package com.trueffect.sql.crud;

import com.trueffect.model.Employee;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeCrud {

    public static Either insertDataJob(Connection connection, Employee employee) {
        Statement query = null;
        try {
            String dateOfHire = employee.getDateOfHire();
            String address = employee.getAddress();
            String job = employee.getJob();
            Either eitherJob = JobCrud.getJobOfName(connection, job);//is Valid?
            int idPerson = employee.getId();
            int idJob = ((Job) eitherJob.getFirstObject()).getId();
            query = (Statement) connection.createStatement();
            String sql = "";
            sql = sql
                    + "INSERT INTO DATA_JOB("
                    + "id_person,"
                    + "date_of_hire,"
                    + "address,"
                    + "id_job,"
                    + "enable_rent) "
                    + "VALUES("
                    + idPerson + ",'"
                    + dateOfHire + "','"
                    + address + "',"
                    + idJob + ","
                    + "FALSE);";
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

    public static Either insertPhone(Connection connection, Employee employee) {
        Statement query = null;
        try {
            int idPerson = employee.getId();
            ArrayList<Integer> phones = employee.getPhones();
            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < phones.size(); i++) {
                sql = sql
                        + "INSERT INTO PHONE("
                        + "number_phone,"
                        + "id_person,"
                        + "status) "
                        + "VALUES("
                        + phones.get(i) + ","
                        + idPerson + ","
                        + "'Active');";
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

    public static Either getEmployeeByIdentifier(Connection connection, String typeIdentifier, String identifier) {
        try {
            Statement query = (Statement) connection.createStatement();
            String sql
                    = "SELECT PERSON.id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name,  "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday, "
                    + "       date_of_hire, "
                    + "       address, "
                    + "       name_job\n"
                    + "  FROM PERSON, DATA_JOB, JOB\n"
                    + " WHERE PERSON.type_identifier='" + typeIdentifier + "' "
                    + "   AND PERSON.identifier='" + identifier + "'"
                    + "   AND PERSON.id = DATA_JOB.id_person"
                    + "   AND DATA_JOB.id_job = JOB.id";
            ResultSet rs = query.executeQuery(sql);
            Employee employee = new Employee();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        rs.getString("name_job")
                );
            }
            if (query != null) {
                query.close();
            }
            return new Either(CodeStatus.CREATED, employee);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static Either getPhones(Connection connection, int idPerson) {
        try {
            String query
                    = "SELECT number_phone"
                    + "  FROM PHONE"
                    + " WHERE id_person=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, idPerson);
            ResultSet rs = st.executeQuery();
            ArrayList<Integer> phones = new ArrayList<Integer>();
            while (rs.next()) {
                phones.add(rs.getInt("number_phone"));
            }
            Employee employee = new Employee();
            employee.setPhones(phones);
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.CREATED, employee);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static Either getEmployee(Connection connection, int idEmployee, String status) {
        try {
            String query
                    = "SELECT PERSON.id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday, "
                    + "       date_of_hire,"
                    + "       address,"
                    + "       name_job\n"
                    + "  FROM PERSON, DATA_JOB, JOB\n"
                    + " WHERE PERSON.id = ? "
                    + "   AND PERSON.id = DATA_JOB.id_person "
                    + "   AND DATA_JOB.id_job = JOB.id";

            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, idEmployee);
            ResultSet rs = st.executeQuery();
            Employee employee = new Employee();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        rs.getString("name_job"));
            }
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.CREATED, employee);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

}
