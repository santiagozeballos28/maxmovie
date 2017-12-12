package com.trueffect.sql.crud;

import com.trueffect.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * @author santiago.mamani
 */
public class PersonCrud {

    public static Person insertrenterUser(Connection connection, int idJob, Person renterUser) throws Exception {
        Person renterUserInserted = null;
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
                    = "INSERT INTO person(type_identifier, identifier, last_name, first_name, genre, birthday, date_create, user_create, date_modifier, user_modifier, status) \n"
                    + "VALUES ('" + typeIdentifier + "','" + identifier + "','" + lastName + "', '" + firstName + "','" + genre + "','" + birthay + "', current_date ,'" + idJob + "',null,null,'Active')";
           
           query.execute(sql);
            if (query != null) {
                query.close();
            }
            renterUserInserted = getPersonByTypeIdentifier(connection, typeIdentifier, identifier);
        } catch (Exception exception) {
            throw exception;
        }
        return renterUserInserted;
    }

    public static Person getPersonByTypeIdentifier(Connection connection, String typeIdentifier, String identifier) throws Exception {
        Person person = null;
        try {
            Statement consulta = (Statement) connection.createStatement();
            String sql = "SELECT id, type_identifier, identifier, last_name, first_name, genre, birthday\n"+
                         "  FROM person\n"+
                         " WHERE status= 'Active' AND type_identifier = '" + typeIdentifier + "' AND identifier = '" + identifier + "'";
            
            ResultSet rs = consulta.executeQuery(sql);
            if (rs.next()) {
                person = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
            }
        } catch (Exception exception) {
            throw exception;
        }
        return person;
    }

    public static Person getPersonByName(Connection connection, String lastName, String firstName) throws Exception {
        Person person = null;
        try {
            String sqlGet = "SELECT id, type_identifier, identifier, last_name, first_name, genre, birthday\n"+
                            "  FROM person\n"+
                            " WHERE person.last_name = '" + lastName+"'\n"+
                            "   AND person.first_name='" + firstName + "'";
            
            PreparedStatement st = connection.prepareStatement(sqlGet);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
            }
        } catch (Exception exception) {
            throw exception;
        }
        return person;
    }

    public static Person getPerson(Connection connection, int idPerson) throws Exception {
        Person person = null;
        try {
            String sql = "SELECT id,type_identifier,identifier, last_name, first_name, genre, birthday\n"+
                         "  FROM person\n"+
                         " WHERE status = 'Active' AND person.id = ?\n";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, idPerson);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
            }
        } catch (Exception exception) {
            throw exception;
        }
        return person;
    }

    public static Person deleteById(Connection connection, int idPerson, int idUserModifier) throws Exception {
        Person person = null;
        try {
            person = getPerson(connection, idPerson);
            String sql
                    = "UPDATE person\n"+
                      "   SET status='Inactive', date_modifier=  current_date , user_modifier= ?"+
                      " WHERE id = ?";
            
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, idUserModifier);
            st.setInt(2, idPerson);
            st.execute();
        } catch (Exception exception) {
            throw exception;
        }
        return person;
    }
}
