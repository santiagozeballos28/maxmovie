package com.trueffect.sql.crud;

import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.tools.CodeStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * @author santiago.mamani
 */
public class PersonCrud {

    public static Person insertrenterUser(Connection connection, int idJob, Person renterUser) throws Exception {
        Person renterUserInserted = new Person();
        Statement query = null;
        try {
            String typeIdentifier = renterUser.getTypeIdentifier().trim();
            String identifier = renterUser.getIdentifier().trim();
            String lastName = renterUser.getLastName().trim();
            String firstName = renterUser.getFirstName().trim();
            String genre = renterUser.getGenre().trim();
            String birthay = renterUser.getBirthday().trim();
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
            renterUserInserted = getPersonByIdentifier(connection, typeIdentifier, identifier);
        } catch (Exception exception) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return renterUserInserted;
    }

    public static Person getPersonByIdentifier(Connection connection, String typeIdentifier, String identifier) throws Exception {
        Person person = new Person();
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
                    + " WHERE status= 'Active' "
                    + "   AND type_identifier = '" + typeIdentifier + "' "
                    + "   AND identifier = '" + identifier + "'";

            ResultSet rs = query.executeQuery(sql);

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
        } catch (Exception exception) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return person;
    }

    public static Person getPersonByName(Connection connection, String lastName, String firstName) throws Exception {
        Person person = new Person();
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
        } catch (Exception exception) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return person;
    }

    public static Person getPerson(Connection connection, int idPerson) throws Exception {
        Person person = new Person();
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
        } catch (Exception exception) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return person;
    }

    public static Person deleteById(Connection connection, int idPerson, int idUserModifier) throws Exception {
        Person person = new Person();
        System.out.println("ENTRO A CRUDPERSON DELETE");
        try {
            person = getPerson(connection, idPerson);
            String sql
                    = "UPDATE PERSON\n"
                    + "   SET status='Inactive', "
                    + "       date_modifier =  current_date ,"
                    + "       user_modifier= ?"
                    + " WHERE id = ?";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, idUserModifier);
            st.setInt(2, idPerson);
            st.execute();
            if (st != null) {
                st.close();
            }
        } catch (Exception exception) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return person;
    }

    public static Person updateRenterUser(Connection connection, int idPerson, int idUserModifier, Person person) throws Exception {
        Person resPerson = new Person();
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
            resPerson = getPerson(connection, idPerson);
        } catch (Exception exception) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
        return resPerson;
    }
}
