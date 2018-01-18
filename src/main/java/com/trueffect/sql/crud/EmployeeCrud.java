package com.trueffect.sql.crud;

import com.trueffect.model.Bond;
import com.trueffect.model.BondAssigned;
import com.trueffect.model.DataJob;
import com.trueffect.model.Employee;
import com.trueffect.model.EmployeeDetail;
import com.trueffect.model.Phone;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
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

    public Either insertDataJob(Connection connection, long idCreateUser, DataJob dataJob, boolean enabledRenterUser) {
        Statement query = null;
        try {
            long idEmployee = dataJob.getEmployeeId();
            long idJob = dataJob.getJobId();
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
                    + "enable_rent,"
                    + "create_user,"
                    + "create_date,"
                    + "modifier_user,"
                    + "modifier_date,"
                    + "status) "
                    + "VALUES("
                    + idEmployee + ", '"
                    + dateOfHire + "', '"
                    + address + "', "
                    + idJob + ", "
                    + enabledRenterUser + ","
                    + idCreateUser + ","
                    + "current_timestamp,"
                    + "null,"
                    + "null,"
                    + "'Active');";
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

    public Either insertPhone(Connection connection, long idCreateUser, long idEmployee, ArrayList<Integer> listPhones) {
        Statement query = null;
        try {
            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < listPhones.size(); i++) {
                sql = sql
                        + "INSERT INTO PHONE("
                        + "number_phone,"
                        + "person_id,"
                        + "status,"
                        + "create_user,"
                        + "modifier_user,"
                        + "create_date,"
                        + "modifier_date) "
                        + "VALUES("
                        + listPhones.get(i) + ","
                        + idEmployee + ","
                        + "'Active',"
                        + idCreateUser + ","
                        + "null,"
                        + "current_timestamp,"
                        + "null); ";
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

    public Either getEmployeeByIdentifier(Connection connection, String typeIdentifier, String identifier) {
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
                    + "   AND DATA_JOB.job_id = JOB.job_id"
                    + "   AND DATA_JOB.status ='Active'";
            ResultSet rs = query.executeQuery(sql);
            Employee employee = new Employee();
            if (rs.next()) {
                employee = new Employee(
                        rs.getLong("person_id"),
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
            return new Either(CodeStatus.OK, employee);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getPhones(Connection connection, long idPerson) {
        try {
            String query
                    = "SELECT number_phone"
                    + "  FROM PHONE"
                    + " WHERE person_id = ? AND status = 'Active'";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idPerson);
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
            return new Either(CodeStatus.OK, employee);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getDetailOfPhones(Connection connection, long idEmployee) {
        try {
            String query
                    = "SELECT person_id, "
                    + "       number_phone, "
                    + "       status"
                    + "  FROM phone"
                    + " WHERE person_id = ?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idEmployee);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            while (rs.next()) {
                eitherRes.addModeloObjet(new Phone(
                        rs.getLong("person_id"),
                        rs.getInt("number_phone"),
                        rs.getString("status")));
            }
            eitherRes.setCode(CodeStatus.OK);
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

    public Either getEmployee(Connection connection, long idEmployee, String status) {
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
                    + "   AND DATA_JOB.status = 'Active'"
                    + "   AND PERSON.person_id = DATA_JOB.person_id "
                    + "   AND DATA_JOB.job_id = JOB.job_id";

            if (StringUtils.isNotBlank(status)) {
                query = query + " AND PERSON.status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idEmployee);
            ResultSet rs = st.executeQuery();
            Employee employee = new Employee();
            if (rs.next()) {
                employee = new Employee(
                        rs.getLong("person_id"),
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
            return new Either(CodeStatus.OK, employee);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either updateStatusDataJob(Connection connection, long idModifierUser, long idEmployee, long idJob, String status) {
        try {
            String sql
                    = "UPDATE DATA_JOB\n"
                    + "   SET modifier_user = " + idModifierUser + ","
                    + "       modifier_date = current_timestamp" + ","
                    + "       status = '" + status + "'"
                    + " WHERE person_id = ? AND job_id = ?";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, idEmployee);
            st.setLong(2, idJob);
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

    public Either updatePhone(Connection connection, long idModifierUser, long idEmployee, ArrayList<ModelObject> listPhone) {
        try {
            Statement query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < listPhone.size(); i++) {
                Phone phone = (Phone) listPhone.get(i);
                sql = sql
                        + "UPDATE PHONE"
                        + "   SET status= '" + phone.getStatus() + "',"
                        + "       modifier_user = " + idModifierUser + ","
                        + "       modifier_date = current_timestamp"
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

    public Either getEmployeeBy(
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
                    + "        PERSON.type_identifier, "
                    + "        PERSON.identifier, "
                    + "        PERSON.last_name, "
                    + "        PERSON.first_name, "
                    + "        PERSON.genre, "
                    + "        PERSON.birthday, "
                    + "        PERSON.create_date, "
                    + "        DATA_JOB.date_of_hire,"
                    + "        DATA_JOB.address , "
                    + "        JOB.job_name, "
                    + "        PERSON.create_user "
                    + "   FROM PERSON, DATA_JOB, JOB "
                    + "  WHERE PERSON.status = 'Active' "
                    + "    AND DATA_JOB.status= 'Active'"
                    + "    AND PERSON.person_id=DATA_JOB.person_id "
                    + "    AND DATA_JOB.job_id = JOB.job_id "
                    + "    AND DATA_JOB.job_id = JOB.job_id ";
            String conditionQuery = "";
            if (StringUtils.isNotBlank(typeId)) {
                conditionQuery = conditionQuery + " type_identifier = '" + typeId.trim().toUpperCase() + "' OR";
            }
            if (StringUtils.isNotBlank(identifier)) {
                conditionQuery = conditionQuery + " identifier= '" + identifier.trim().toUpperCase() + "' OR";
            }
            if (StringUtils.isNotBlank(lastName)) {
                conditionQuery = conditionQuery
                        + " last_name LIKE '%" + lastName.trim() + "%' OR"
                        + " last_name LIKE '%" + lastName.trim().toLowerCase() + "%' OR";
            }
            if (StringUtils.isNotBlank(firstName)) {
                conditionQuery = conditionQuery
                        + " first_name LIKE '%" + firstName.trim() + "%' OR"
                        + " first_name LIKE '%" + firstName.trim().toLowerCase() + "%' OR";
            }
            if (StringUtils.isNotBlank(genre)) {
                conditionQuery = conditionQuery + " genre= '" + genre.trim().toUpperCase() + "' OR";
            }

            if (StringUtils.isNotBlank(nameJob)) {
                conditionQuery = conditionQuery + " job_name= '" + nameJob.trim().toUpperCase() + "' OR";
            }
            if (StringUtils.isNotBlank(dateOfHire)) {
                conditionQuery = conditionQuery + " date_of_hire = '" + dateOfHire.trim() + "' OR";
            }
            if (conditionQuery.length() > 0) {
                conditionQuery = conditionQuery.substring(0, conditionQuery.length() - 2);
                query = query + " AND ( " + conditionQuery + " )";
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
                        rs.getLong("person_id"),
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
            eitherRes.setCode(CodeStatus.OK);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getPhonesByNumeber(Connection connection, ArrayList<Integer> phones, String status) {
        try {
            String query
                    = "SELECT person_id,"
                    + "       number_phone, "
                    + "       status\n"
                    + "  FROM phone\n"
                    + " where (";
            for (Integer phone : phones) {
                query = query + "number_phone = " + phone + " OR ";
            }
            query = query.substring(0, query.length() - 4);
            query = query + ") ";

            if (StringUtils.isNotBlank(status)) {
                query = query + "AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            while (rs.next()) {
                Phone phoneRes = new Phone(
                        rs.getLong("person_id"),
                        rs.getInt("number_phone"),
                        rs.getString("status"));
                eitherRes.addModeloObjet(phoneRes);
            }
            if (st != null) {
                st.close();
            }
            eitherRes.setCode(CodeStatus.OK);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getDataJob(Connection connection, long idEmployee, String status) {
        try {
            String query
                    = "SELECT person_id,"
                    + "       job_id, "
                    + "       date_of_hire, "
                    + "       address,"
                    + "       status"
                    + "  FROM DATA_JOB"
                    + " WHERE DATA_JOB.person_id = " + idEmployee;

            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            DataJob dataJob = new DataJob();
            while (rs.next()) {
                dataJob = new DataJob(
                        rs.getLong("person_id"),
                        rs.getLong("job_id"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"),
                        rs.getString("status"));
                eitherRes.addModeloObjet(dataJob);
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
     public  Either getAllDataJob(Connection connection, String status) {
        try {
            String query
                    = "SELECT id_person,"
                    + "       date_of_hire, "
                    + "       address"
                    + "  FROM PERSON , DATA_JOB"
                    + " WHERE PERSON.id = DATA_JOB.id_person";

            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            DataJob dataJob = new DataJob();
            while (rs.next()) {
                dataJob = new DataJob(
                        rs.getLong("id_person"),
                        rs.getString("date_of_hire"),
                        rs.getString("address"));
                eitherRes.addModeloObjet(dataJob);
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
     public static Either getBond(Connection connection) {
        try {
            String query
                    = "SELECT id,"
                    + "       quantity, "
                    + "       seniority"
                    + "  FROM bond";

            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            Bond bond = new Bond();
            while (rs.next()) {
                bond = new Bond(
                        rs.getInt("id"),
                        rs.getDouble("quantity"),
                        rs.getInt("seniority"));
                eitherRes.addModeloObjet(bond);
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

public Either getBondAssigned(Connection connection) {
        try {
            String query
                    = "SELECT id_person, "
                    + "       id_bond, "
                    + "       start_date, "
                    + "       end_date, "
                    + "       status\n"
                    + "  FROM bond_assigned;";

            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            BondAssigned bondAssigned = new BondAssigned();
            while (rs.next()) {
                bondAssigned = new BondAssigned(
                        rs.getLong("id_person"),
                        rs.getLong("id_bond"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("status"));
                eitherRes.addModeloObjet(bondAssigned);
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
