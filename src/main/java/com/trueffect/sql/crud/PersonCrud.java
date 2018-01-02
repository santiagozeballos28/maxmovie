package com.trueffect.sql.crud;

import com.trueffect.model.Person;
import com.trueffect.model.PersonDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;

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

    public static Either insertPerson(Connection connection, int idJob, Person person) {
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
                    + "date_create, "
                    + "user_create, "
                    + "date_modifier, "
                    + "user_modifier, "
                    + "status) \n"
                    + "VALUES ('"
                    + typeIdentifier + "','"
                    + identifier + "','"
                    + lastName + "', '"
                    + firstName + "','"
                    + genre + "','"
                    + birthay + "', "
                    + "current_date ,'"
                    + idJob + "',"
                    + "null,"
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

    public static Either getPersonByName(Connection connection, String lastName, String firstName) {
        try {
            String sqlGet
                    = "SELECT id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday\n"
                    + "  FROM PERSON "
                    + " WHERE last_name = '" + lastName + "' "
                    + "   AND first_name='" + firstName + "'";

            PreparedStatement st = connection.prepareStatement(sqlGet);
            ResultSet rs = st.executeQuery();
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
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.CREATED, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static Either getPerson(Connection connection, int idPerson, String status) {
        try {
            String query
                    = "SELECT id, "
                    + "       type_identifier, "
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name, "
                    + "       genre, "
                    + "       birthday"
                    + "  FROM PERSON"
                    + " WHERE id= ? ";
            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            query = query
                    + "   AND id"
                    + "   NOT IN("
                    + "SELECT id_person"
                    + "  FROM DATA_JOB)";
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, idPerson);
            ResultSet rs = st.executeQuery();
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
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.CREATED, person);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static Either updateStatusPerson(Connection connection, int idPerson, int idUserModifier, String status) {

        try {
            String sql
                    = "UPDATE PERSON\n"
                    + "   SET status=?, "
                    + "       date_modifier =  current_date ,"
                    + "       user_modifier= ?"
                    + " WHERE id = ?";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, idUserModifier);
            st.setInt(3, idPerson);
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

    public static Either updatePerson(Connection connection, int idPerson, int idUserModifier, Person person) {
        try {
            System.out.println("entre UPDATE PERSON");
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
                    + "       date_modifier=  current_date,"
                    + "       user_modifier= ?"
                    + " WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, idUserModifier);
            st.setInt(2, idPerson);
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

    public static Either getPersonBy(
            Connection connection,
            String typeId,
            String identifier,
            String lastName,
            String firstName,
            String genre) {
        Either eitherRes = new Either();
        try {
            String query
                    = " SELECT RENTER_USER.id, "
                    + "        RENTER_USER.type_identifier, "
                    + "        RENTER_USER.identifier,"
                    + "        RENTER_USER.last_name,"
                    + "        RENTER_USER.first_name,"
                    + "        RENTER_USER.genre,"
                    + "        RENTER_USER.birthday,"
                    + "        RENTER_USER.date_create,"
                    + "        PERSON.last_name AS last_name_user_create,"
                    + "        PERSON.first_name AS first_name_user_create"
                    + "   FROM "
                    + "(SELECT id, "
                    + "        type_identifier, "
                    + "        identifier, "
                    + "        last_name, "
                    + "        first_name, "
                    + "        genre,"
                    + "        birthday, "
                    + "        date_create, "
                    + "        user_create,"
                    + "        status"
                    + "   FROM person "
                    + "  WHERE id NOT IN"
                    + "(SELECT id_person"
                    + "   FROM DATA_JOB"
                    + "  WHERE enable_rent = false)) RENTER_USER,PERSON"
                    + "  WHERE RENTER_USER.status= 'Active' "
                    + "    AND RENTER_USER.user_create = PERSON.id";

            if (StringUtils.isNotBlank(typeId)) {
                query = query + " AND RENTER_USER.type_identifier = '" + typeId + "'";
            }
            if (StringUtils.isNotBlank(identifier)) {
                query = query + " AND RENTER_USER.identifier= '" + identifier + "' ";
            }
            if (StringUtils.isNotBlank(lastName)) {
                query = query + " AND RENTER_USER.last_name LIKE '%" + lastName + "%'";
            }
            if (StringUtils.isNotBlank(firstName)) {
                query = query + " AND RENTER_USER.first_name LIKE '%" + firstName + "%'";
            }
            if (StringUtils.isNotBlank(genre)) {
                query = query + " AND RENTER_USER.genre= '" + genre + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            PersonDetail person = new PersonDetail();
            while (rs.next()) {
                person = new PersonDetail(
                        rs.getInt("id"),
                        rs.getString("type_identifier"),
                        "",
                        rs.getString("identifier"),
                        rs.getString("last_name") + " " + rs.getString("first_name"),
                        rs.getString("genre"),
                        "",
                        rs.getString("birthday"),
                        rs.getString("date_create"),
                        rs.getString("last_name_user_create") + " " + rs.getString("first_name_user_create"));
                eitherRes.addModeloObjet(person);
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
