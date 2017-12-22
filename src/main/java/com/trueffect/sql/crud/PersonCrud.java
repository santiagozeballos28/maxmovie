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

    public static Either insertRenterUser(Connection connection, int idJob, Person renterUser) {
        Statement query = null;
        try {
            String typeIdentifier = renterUser.getTypeIdentifier();
            String identifier = renterUser.getIdentifier();
            String lastName = renterUser.getLastName();
            String firstName = renterUser.getFirstName();
            String genre = renterUser.getGenre();
            String birthay = renterUser.getBirthday();
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
            return getPersonByIdentifier(connection, typeIdentifier, identifier);
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

    public static Either getPerson(Connection connection, int idPerson) {
        try {
            String sql
                    = "SELECT id,"
                    + "       type_identifier,"
                    + "       identifier, "
                    + "       last_name, "
                    + "       first_name,"
                    + "       genre,"
                    + "       birthday\n"
                    + "  FROM PERSON "
                    + " WHERE status = 'Active' "
                    + "   AND person.id = ?";

            PreparedStatement st = connection.prepareStatement(sql);
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
            Either eitherPerson = getPerson(connection, idPerson);
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
            return new Either(CodeStatus.CREATED, eitherPerson.getFirstObject());
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static Either updateRenterUser(Connection connection, int idPerson, int idUserModifier, Person person) {
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
            Either resPerson = getPerson(connection, idPerson);
            return new Either(CodeStatus.CREATED, resPerson.getFirstObject());
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
                    = " SELECT LIST_PERSON.id, "
                    + "        LIST_PERSON.type_identifier, "
                    + "        LIST_PERSON.identifier, "
                    + "        LIST_PERSON.last_name, "
                    + "        LIST_PERSON.first_name, "
                    + "        LIST_PERSON.genre, "
                    + "        LIST_PERSON.birthday, "
                    + "        LIST_PERSON.date_create, "
                    + "        PERSON.last_name AS last_name_user_create, "
                    + "        PERSON.first_name AS first_name_user_create \n"
                    + "   FROM "
                    + "(SELECT id, "
                    + "        type_identifier, "
                    + "        identifier, "
                    + "        last_name, "
                    + "        first_name, "
                    + "        genre, "
                    + "        birthday,"
                    + "        date_create, "
                    + "        user_create\n"
                    + "   FROM PERSON\n"
                    + "  WHERE ";

            if (StringUtils.isNotBlank(typeId)) {
                query = query + "type_identifier= '" + typeId + "' AND ";
            }
            if (StringUtils.isNotBlank(identifier)) {
                query = query + "identifier= '" + identifier + "' AND ";
            }
            if (StringUtils.isNotBlank(lastName)) {
                query = query + "last_name LIKE '%" + lastName + "%' AND ";
            }
            if (StringUtils.isNotBlank(firstName)) {
                query = query + "first_name LIKE '%" + firstName + "%' AND ";
            }
            if (StringUtils.isNotBlank(genre)) {
                query = query + "genre= '" + genre + "' AND ";
            }

            query = query + "  status= 'Active' )"
                    + "        LIST_PERSON,PERSON\n"
                    + "  WHERE LIST_PERSON.user_create=PERSON.id  "
                    + "        AND LIST_PERSON.id\n"
                    + " NOT IN ("
                    + " SELECT id_person\n"
                    + "    FROM DATA_JOB)";
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
