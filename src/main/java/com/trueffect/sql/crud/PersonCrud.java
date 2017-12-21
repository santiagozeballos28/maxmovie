package com.trueffect.sql.crud;

import com.trueffect.model.Person;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
            return new Either(CodeStatus.CREATED, eitherPerson.getModelObject());
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
            return new Either(CodeStatus.CREATED, resPerson.getModelObject());
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
