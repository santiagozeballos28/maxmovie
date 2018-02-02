package com.trueffect.sql.crud;

import com.trueffect.model.Person;
import com.trueffect.model.PersonDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData.GenrePerson;
import com.trueffect.tools.ConstantData.TypeIdentifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/*
 * @author santiago.mamani
 */
public class PersonCrud {

    public Either insertPerson(Connection connection, long idCreateUser, Person person) {
        Statement query = null;
        try {
            String typeIdentifier = person.getTypeIdentifier();
            String identifier = person.getIdentifier();
            String lastName = person.getLastName();
            String firstName = person.getFirstName();
            String genre = person.getGenre();
            String birthay = person.getBirthday();
            query = (Statement) connection.createStatement();
            //ident
            String sql
                    = "INSERT INTO PERSON("
                    + "type_identifier, "
                    + "identifier, "
                    + "last_name, "
                    + "first_name, "
                    + "genre, "
                    + "birthday, "
                    + "create_user, "
                    + "modifier_user, "
                    + "create_date, "
                    + "modifier_date, "
                    + "status) \n"
                    + "VALUES ('"
                    + typeIdentifier + "','"
                    + identifier + "','"
                    + lastName + "', '"
                    + firstName + "','"
                    + genre + "','"
                    + birthay + "', "
                    + idCreateUser + ","
                    + "null,"
                    + "current_timestamp ,"
                    + "null,"
                    + "'Active')";
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

    public Either getPersonByIdentifier(Connection connection, String typeIdentifier, String identifier) {
        try {
            Statement query = (Statement) connection.createStatement();
            String sql
                    = "SELECT person_id,"
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre,"
                    + "       birthday,"
                    + "       status\n"
                    + "  FROM PERSON "
                    + " WHERE type_identifier = '" + typeIdentifier + "' "
                    + "   AND identifier = '" + identifier + "'";
            ResultSet rs = query.executeQuery(sql);
            Person person = new Person();
            if (rs.next()) {
                person = new Person(
                        rs.getLong("person_id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("status"));
            }
            if (query != null) {
                query.close();
            }
            return new Either(CodeStatus.OK, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getPersonByName(Connection connection, String lastName, String firstName) {
        try {
            String sqlGet
                    = "SELECT person_id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday,"
                    + "       status\n"
                    + "  FROM PERSON "
                    + " WHERE last_name = '" + lastName + "' "
                    + "   AND first_name='" + firstName + "'";
            PreparedStatement st = connection.prepareStatement(sqlGet);
            ResultSet rs = st.executeQuery();
            Person person = new Person();
            if (rs.next()) {
                person = new Person(
                        rs.getLong("person_id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("status"));
            }
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.OK, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getRenterUser(Connection connection, long idPerson, String status) {
        try {
            String query
                    = "SELECT PERSON.person_id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday,"
                    + "       PERSON.status"
                    + "  FROM PERSON"
                    + " WHERE person_id= ? ";
            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            query = query
                    + "   AND person_id"
                    + "   NOT IN("
                    + "SELECT employee_data_id"
                    + "  FROM EMPLOYEE_DATA"
                    + " WHERE enable_rent = FALSE)";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idPerson);
            ResultSet rs = st.executeQuery();
            Person person = new Person();
            if (rs.next()) {
                person = new Person(
                        rs.getLong("person_id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("status"));
            }
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.OK, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either updateStatusPerson(Connection connection, long idPerson, long idUserModifier, String status) {
        try {
            String sql
                    = "UPDATE PERSON\n"
                    + "   SET status=?, "
                    + "       modifier_date =  current_timestamp ,"
                    + "       modifier_user= ?"
                    + " WHERE person_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setLong(2, idUserModifier);
            st.setLong(3, idPerson);
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

    public Either updatePerson(Connection connection, long idUserModifier, Person person) {
        try {
            String sql
                    = "UPDATE PERSON\n"
                    + "   SET ";
            // variable to store the person attributes
            String varSet = person.getTypeIdentifier();
            if (varSet != null) {
                sql = sql + "type_identifier= '" + varSet + "',";
            }
            varSet = person.getIdentifier();
            if (varSet != null) {
                sql = sql + "identifier= '" + varSet + "',";
            }
            varSet = person.getLastName();
            if (varSet != null) {
                sql = sql + "last_name= '" + varSet + "',";
            }
            varSet = person.getFirstName();
            if (varSet != null) {
                sql = sql + "first_name= '" + varSet + "',";
            }
            varSet = person.getGenre();
            if (varSet != null) {
                sql = sql + "genre= '" + varSet + "',";
            }
            varSet = person.getBirthday();
            if (varSet != null) {
                sql = sql + "birthday= '" + varSet + "',";
            }
            sql = sql
                    + "       modifier_date =  current_timestamp,"
                    + "       modifier_user = ?"
                    + " WHERE person_id = ?";
            long idPerson = person.getId();
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, idUserModifier);
            st.setLong(2, idPerson);
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

    public Either getPersonBy(
            Connection connection,
            String typeId,
            String identifier,
            String lastName,
            String firstName,
            String genre,
            String birthdayStart,
            String birthdayEnd) {
        Either eitherRes = new Either();
        try {
            String query
                    = " SELECT RENTER_USER.person_id, "
                    + "        RENTER_USER.type_identifier, "
                    + "        RENTER_USER.identifier,"
                    + "        RENTER_USER.last_name,"
                    + "        RENTER_USER.first_name,"
                    + "        RENTER_USER.genre,"
                    + "        RENTER_USER.birthday,"
                    + "        RENTER_USER.create_date,"
                    + "        PERSON.last_name AS last_name_user_create,"
                    + "        PERSON.first_name AS first_name_user_create"
                    + "   FROM "
                    + "(SELECT PERSON.person_id, "
                    + "        PERSON.type_identifier, "
                    + "        PERSON.identifier, "
                    + "        PERSON.last_name, "
                    + "        PERSON.first_name, "
                    + "        PERSON.genre,"
                    + "        PERSON.birthday, "
                    + "        PERSON.create_date, "
                    + "        PERSON.create_user,"
                    + "        PERSON.status"
                    + "   FROM PERSON "
                    + "  WHERE PERSON.person_id NOT IN"
                    + "(SELECT employee_data_id"
                    + "   FROM EMPLOYEE_DATA"
                    + "  WHERE enable_rent = false)) RENTER_USER,PERSON"
                    + "  WHERE RENTER_USER.status= 'Active' "
                    + "    AND RENTER_USER.create_user = PERSON.person_id";
            String conditionQuery = "";
            if (StringUtils.isNotBlank(typeId)) {
                conditionQuery = conditionQuery + " RENTER_USER.type_identifier = '" + typeId.trim().toUpperCase() + "' OR";
            }
            if (StringUtils.isNotBlank(identifier)) {
                conditionQuery = conditionQuery + " RENTER_USER.identifier= '" + identifier.trim().toUpperCase() + "' OR";
            }
            if (StringUtils.isNotBlank(lastName)) {
                conditionQuery = conditionQuery
                        + " RENTER_USER.last_name LIKE '%" + lastName + "%' OR"
                        + " RENTER_USER.last_name LIKE '%" + lastName.toLowerCase() + "%' OR";
            }
            if (StringUtils.isNotBlank(firstName)) {
                conditionQuery = conditionQuery
                        + " RENTER_USER.first_name LIKE '%" + firstName + "%' OR"
                        + " RENTER_USER.first_name LIKE '%" + firstName.toLowerCase() + "%' OR";
            }
            if (StringUtils.isNotBlank(genre)) {
                conditionQuery = conditionQuery + " RENTER_USER.genre= '" + genre.trim().toUpperCase() + "' OR";
            }
            if (StringUtils.isNotBlank(birthdayStart) && StringUtils.isNotBlank(birthdayEnd)) {
                conditionQuery = conditionQuery
                        + " ( RENTER_USER.birthday >= '" + birthdayStart.trim() + "' AND "
                        + " RENTER_USER.birthday <= '" + birthdayEnd.trim() + "') OR";
            }
            if (conditionQuery.length() > 0) {
                conditionQuery = conditionQuery.substring(0, conditionQuery.length() - 2);
                query = query + " AND (" + conditionQuery + ")";
            }
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            PersonDetail person = new PersonDetail();
            while (rs.next()) {
                String typeIdentifier = rs.getString("type_identifier").trim();
                String genrePerson = rs.getString("genre");
                TypeIdentifier typeIdenEnum = TypeIdentifier.valueOf(typeIdentifier);
                GenrePerson genreEnum = GenrePerson.valueOf(genrePerson);
                person = new PersonDetail(
                        rs.getLong("person_id"),
                        rs.getString("type_identifier"),
                        typeIdenEnum.getDescriptionIdentifier(),
                        rs.getString("identifier"),
                        rs.getString("last_name") + " " + rs.getString("first_name"),
                        rs.getString("genre"),
                        genreEnum.getNameGenre(),
                        rs.getString("birthday"),
                        rs.getString("create_date"),
                        rs.getString("last_name_user_create") + " " + rs.getString("first_name_user_create"));
                eitherRes.addModeloObjet(person);
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

    public Either getPerson(Connection connection, long idPerson, String status) {
        try {
            String query
                    = "SELECT PERSON.person_id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday,"
                    + "       status"
                    + "  FROM PERSON"
                    + " WHERE person_id= ? ";
            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idPerson);
            ResultSet rs = st.executeQuery();
            Person person = new Person();
            if (rs.next()) {
                person = new Person(
                        rs.getLong("person_id"),
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"),
                        rs.getString("status"));
            }
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.OK, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
