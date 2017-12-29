package com.trueffect.sql.crud;

import com.trueffect.model.Employee;
import com.trueffect.model.EmployeeDetail;
import com.trueffect.model.Job;
import com.trueffect.model.Phone;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
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
                    + " WHERE id_person=? AND status = 'Active'";
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

    public static Either getDetailOfPhones(Connection connection, int idEmployee) {
        try {
            String query
                    = "SELECT id_person, "
                    + "       number_phone, "
                    + "       status"
                    + "  FROM phone"
                    + " WHERE id_person = ?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, idEmployee);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            while (rs.next()) {
                eitherRes.addModeloObjet(new Phone(
                        rs.getInt("id_person"),
                        rs.getInt("number_phone"),
                        rs.getString("status")));
            }
            eitherRes.setCode(CodeStatus.CREATED);
            if (st != null) {
                st.close();
            }
            return eitherRes;
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

    public static Either updateDataJob(Connection connection, int idEmployee, int idJob, Employee employee) {
        try {
            String sql
                    = "UPDATE DATA_JOB\n"
                    + "   SET ";
            // variable to store the person attributes
            String varSet = employee.getDateOfHire();
            if (StringUtils.isNotBlank(varSet)) {
                sql = sql + "date_of_hire= '" + varSet + "',";
            }
            varSet = employee.getAddress();
            if (StringUtils.isNotBlank(varSet)) {
                sql = sql + "address= '" + varSet + "',";
            }

            sql = sql + "     id_job= ?"
                    + " WHERE id_person = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, idJob);
            st.setInt(2, idEmployee);
            st.execute();
            if (st != null) {
                st.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static Either updatePhone(Connection connection, int idEmployee, ArrayList<ModelObject> listPhone) {
        try {
            Statement query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < listPhone.size(); i++) {
                Phone phone = (Phone) listPhone.get(i);
                sql = sql
                        + "UPDATE PHONE"
                        + "   SET status= '" + phone.getStatus() + "'"
                        + " WHERE number_phone= " + phone.getNumberPhone() + ";";
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

    public static Either getEmployeeBy(
            Connection connection,
            String typeId,
            String identifier,
            String lastName,
            String firstName,
            String genre,
            String dateOfHire,
            String nameJob) {
        Either eitherRes = new Either();
        try {
            String query
                    = " SELECT EMPLOYEE.id, "
                    + "        EMPLOYEE.type_identifier, "
                    + "        EMPLOYEE.identifier, "
                    + "        EMPLOYEE.last_name, "
                    + "        EMPLOYEE.first_name, "
                    + "        EMPLOYEE.genre, "
                    + "        EMPLOYEE.birthday, "
                    + "        EMPLOYEE.date_create, "
                    + "        EMPLOYEE.date_of_hire, "
                    + "        EMPLOYEE.address , "
                    + "        EMPLOYEE.name_job, "
                    + "        PERSON.last_name AS last_name_user_create, "
                    + "        PERSON.first_name AS first_name_user_create "
                    + "   FROM "
                    + "(SELECT PERSON.id, "
                    + "        type_identifier, "
                    + "        identifier, "
                    + "        last_name, "
                    + "        first_name, "
                    + "        genre, "
                    + "        birthday, "
                    + "        date_create, "
                    + "        date_of_hire,"
                    + "        address , "
                    + "        name_job, "
                    + "        user_create "
                    + "   FROM PERSON, DATA_JOB, JOB "
                    + "  WHERE status = 'Active' "
                    + "    AND PERSON.id=DATA_JOB.id_person "
                    + "    AND DATA_JOB.id_job = JOB.id "
                    + "    AND DATA_JOB.id_job = JOB.id ";

            if (StringUtils.isNotBlank(typeId)) {
                query = query + " AND type_identifier = '" + typeId + "'";
            }
            if (StringUtils.isNotBlank(identifier)) {
                query = query + " AND identifier= '" + identifier + "' ";
            }
            if (StringUtils.isNotBlank(lastName)) {
                query = query + " AND last_name LIKE '%" + lastName + "%'";
            }
            if (StringUtils.isNotBlank(firstName)) {
                query = query + " AND first_name LIKE '%" + firstName + "%'";
            }
            if (StringUtils.isNotBlank(genre)) {
                query = query + " AND genre= '" + genre + "'";
            }

            if (StringUtils.isNotBlank(nameJob)) {
                query = query + " AND name_job= '" + nameJob + "'";
            }
            if (StringUtils.isNotBlank(dateOfHire)) {
                query = query + " AND  date_of_hire = '" + dateOfHire + "'";
            }
            query = query + ") EMPLOYEE, PERSON "
                    + " WHERE EMPLOYEE.user_create = PERSON.id";

            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            EmployeeDetail employeeDetail = new EmployeeDetail();
            while (rs.next()) {
                employeeDetail = new EmployeeDetail(
                        rs.getInt("id"),
                        rs.getString("type_identifier"),
                        "",
                        rs.getString("identifier"),
                        rs.getString("last_name") + " " + rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("date_create"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        rs.getString("name_job"),
                        rs.getString("last_name_user_create") + " " + rs.getString("first_name_user_create"));
                eitherRes.addModeloObjet(employeeDetail);
            }
            if (st != null) {
                st.close();
            }
            eitherRes.setCode(CodeStatus.CREATED);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
