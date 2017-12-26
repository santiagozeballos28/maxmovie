package com.trueffect.sql.crud;

import com.trueffect.model.Employee;
import com.trueffect.model.Job;
import com.trueffect.model.Person;
import com.trueffect.response.Either;
import static com.trueffect.sql.crud.PersonCrud.getPersonByIdentifier;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeCrud {

    public static Either insertEmployee(Connection connection, int idUserCreate, Employee employee) {
        Statement query = null;
        try {
            String dateOfHire = employee.getDateOfHire();
            String address = employee.getAddress();
            String job = employee.getJob();
            ArrayList<Integer> phones = employee.getPhones();

            Either eitherPerson = PersonCrud.insertPerson(connection, idUserCreate, employee);
            int idPerson = ((Person) eitherPerson.getFirstObject()).getId();
            Either eitherJob = JobCrud.getJobOfName(connection, job);
            int idJob = ((Job) eitherJob.getFirstObject()).getId();
            query = (Statement) connection.createStatement();
            String sql = "";
            for (int i = 0; i < phones.size(); i++) {
                sql = sql
                        + "INSERT INTO PHONE("
                        + "number_phone,"
                        + "id_person) "
                        + "VALUES("
                        + phones.get(i) + ","
                        + idPerson + ");";
            }
            sql = sql
                    + "INSERT INTO DATA_JOB("
                    + "id_person,"
                    + "date_of_hire,"
                    + "address,"
                    + "id_job) "
                    + "VALUES("
                    + idPerson + ",'"
                    + dateOfHire + "','"
                    + address + "',"
                    + idJob+");";

            query.execute(sql);
            if (query != null) {
                query.close();
            }
            return getPersonByIdentifier(connection,employee.getTypeIdentifier(),employee.getIdentifier());
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
       public static Either getPersonByIdentifier(Connection connection, String typeIdentifier, String identifier) {
        try {
            Statement query = (Statement) connection.createStatement();
            String sql
                    = "SELECT id,"
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre,"
                    + "       birthday\n"
                    + "  FROM PERSON "
                    + " WHERE type_identifier = '" + typeIdentifier + "' "
                    + "   AND identifier = '" + identifier + "'";
            ResultSet rs = query.executeQuery(sql);
            Person person = new Person();
            if (rs.next()) {
                person = new Person(
                        rs.getInt("id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"));
            }
            if (query != null) {
                query.close();
            }
            return new Either(CodeStatus.CREATED, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

}
