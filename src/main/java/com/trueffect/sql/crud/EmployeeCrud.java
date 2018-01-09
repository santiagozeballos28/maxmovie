package com.trueffect.sql.crud;

import com.trueffect.model.DataJob;
import com.trueffect.model.Employee;
import com.trueffect.model.EmployeeDetail;
import com.trueffect.model.Phone;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.JobName;
import com.trueffect.tools.ConstantData.TypeIdentifier;
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

    public static Either insertDataJob(Connection connection, DataJob dataJob) {
        Statement query = null;
        try {
            int idEmployee = dataJob.getEmployeeId();
            int idJob = dataJob.getJobId();
            String dateOfHire = dataJob.getDateOfHire();
            String address = dataJob.getAddress();
            query = (Statement) connection.createStatement();
            String sql = "";
            sql = sql
                    + "INSERT INTO DATA_JOB("
                    + "person_id,"
                    + "date_of_hire,"
                    + "address,"
                    + "job_id,"
                    + "enable_rent) "
                    + "VALUES("
                    + idEmployee + ",'"
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

    public static Either insertPhone(Connection connection, int idEmployee, ArrayList<Integer> listPhones) {
        Statement query = null;
        try {
            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < listPhones.size(); i++) {
                sql = sql
                        + "INSERT INTO PHONE("
                        + "number_phone,"
                        + "person_id,"
                        + "status) "
                        + "VALUES("
                        + listPhones.get(i) + ","
                        + idEmployee + ","
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
                    = "SELECT PERSON.person_id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name,  "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday, "
                    + "       date_of_hire, "
                    + "       address, "
                    + "       job_name\n"
                    + "  FROM PERSON, DATA_JOB, JOB\n"
                    + " WHERE PERSON.type_identifier='" + typeIdentifier + "' "
                    + "   AND PERSON.identifier='" + identifier + "'"
                    + "   AND PERSON.person_id = DATA_JOB.person_id"
                    + "   AND DATA_JOB.job_id = JOB.job_id";
            ResultSet rs = query.executeQuery(sql);
            Employee employee = new Employee();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("person_id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        rs.getString("job_name")
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
                    + " WHERE person_id = ? AND status = 'Active'";
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
                    = "SELECT person_id, "
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
                        rs.getInt("person_id"),
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
                    = "SELECT PERSON.person_id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday, "
                    + "       date_of_hire,"
                    + "       address,"
                    + "       job_name\n"
                    + "  FROM PERSON, DATA_JOB, JOB\n"
                    + " WHERE PERSON.person_id = ? "
                    + "   AND PERSON.person_id = DATA_JOB.person_id "
                    + "   AND DATA_JOB.job_id = JOB.job_id";

            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, idEmployee);
            ResultSet rs = st.executeQuery();
            Employee employee = new Employee();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("person_id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        rs.getString("job_name"));
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

    public static Either updateDataJob(Connection connection, int idJob, Employee employee) {
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

            sql = sql + "     job_id = ?"
                    + " WHERE person_id = ?";
            int idEmployee = employee.getId();
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
                        + " WHERE person_id = " + idEmployee
                        + "   AND number_phone= " + phone.getNumberPhone() + ";";
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
                    = " SELECT EMPLOYEE.person_id, "
                    + "        EMPLOYEE.type_identifier, "
                    + "        EMPLOYEE.identifier, "
                    + "        EMPLOYEE.last_name, "
                    + "        EMPLOYEE.first_name, "
                    + "        EMPLOYEE.genre, "
                    + "        EMPLOYEE.birthday, "
                    + "        EMPLOYEE.create_date, "
                    + "        EMPLOYEE.date_of_hire, "
                    + "        EMPLOYEE.address , "
                    + "        EMPLOYEE.job_name, "
                    + "        PERSON.last_name AS last_name_user_create, "
                    + "        PERSON.first_name AS first_name_user_create "
                    + "   FROM "
                    + "(SELECT PERSON.person_id, "
                    + "        type_identifier, "
                    + "        identifier, "
                    + "        last_name, "
                    + "        first_name, "
                    + "        genre, "
                    + "        birthday, "
                    + "        create_date, "
                    + "        date_of_hire,"
                    + "        address , "
                    + "        job_name, "
                    + "        create_user "
                    + "   FROM PERSON, DATA_JOB, JOB "
                    + "  WHERE status = 'Active' "
                    + "    AND PERSON.person_id=DATA_JOB.person_id "
                    + "    AND DATA_JOB.job_id = JOB.job_id "
                    + "    AND DATA_JOB.job_id = JOB.job_id ";

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
                query = query + " AND job_name= '" + nameJob + "'";
            }
            if (StringUtils.isNotBlank(dateOfHire)) {
                query = query + " AND  date_of_hire = '" + dateOfHire + "'";
            }
            query = query + ") EMPLOYEE, PERSON "
                    + " WHERE EMPLOYEE.create_user = PERSON.person_id";

            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String typeIdentifier = rs.getString("type_identifier").trim();
                String gentePerson = rs.getString("genre");
                String jobEmployee = rs.getString("job_name");
                TypeIdentifier typeIdenEnum = TypeIdentifier.valueOf(typeIdentifier);
                GenrePerson genreEnum = GenrePerson.valueOf(gentePerson);
                JobName jobNameEnum = JobName.valueOf(jobEmployee);
                EmployeeDetail employeeDetail = new EmployeeDetail(
                        rs.getInt("person_id"),
                        rs.getString("type_identifier"),
                        typeIdenEnum.getDescriptionIdentifier(),
                        rs.getString("identifier"),
                        rs.getString("last_name") + " " + rs.getString("first_name"),
                        rs.getString("genre"),
                        genreEnum.getNameGenre(),
                        rs.getString("birthday"),
                        rs.getString("create_date"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        jobNameEnum.getDescriptionJobName(),
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
