package com.trueffect.sql.crud;

import com.trueffect.response.CorrectResponse;
import com.trueffect.response.ErrorResponse;
import com.trueffect.response.MapperResponse;
import com.trueffect.conection.db.DatabasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.Person;


import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

/*
 * @author santiago.mamani
 */
public class PersonCrud {

    public static Person insertrenterUser(Connection connection,int idJob, Person renterUser) throws Exception {
      Person renterUserInserted=null; 
             Statement query = null;
            try {
                String typeIdentifier = renterUser.getTypeIdentifier();
                String identifier = renterUser.getIdentifier();
                String lastName = renterUser.getLastName();
                String firstName = renterUser.getFirstName();
                String genre = renterUser.getGenre();
                String birthay = renterUser.getBirthday();
                query = (Statement) connection.createStatement();
                String sql=
                "INSERT INTO person(\n" +
                " type_identifier, identifier, last_name, first_name, genre, \n" +
                " birthday, date_create, user_create, date_modifier, user_modifier, \n" +
                " status)\n" +
                " VALUES ('"+typeIdentifier+"','"+identifier+"','"+lastName+"', '"
                        +firstName+"','"+ genre+"','"+birthay+"','2017-12-08','"+idJob+"',null,null,'Active')";
      
                query.execute(sql);
                String msg = "The user " + firstName + " " + lastName + " has been successfully inserted";
                renterUserInserted = getByTypeIdentifier(typeIdentifier, identifier);
              } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }finally{
                 try {
                   if(query!=null) query.close();                
                                   
                 } catch (SQLException ex) {
                    throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, ex.getMessage());    
                  }
         }  
            
            return renterUserInserted;
    }

    public static Person getRenterUser(int id) throws Exception{
       Person renterUser = null;
        try {
            DatabasePostgres.getConection();
            try {
                renterUser = getRenterUserById(id);
                } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.NOT_FOUND, e.getMessage());
            }
            DatabasePostgres.close();
        } catch (Exception e) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return renterUser;
    }

    public static Person getRenterUserById(int id) throws Exception {

        Person renterUser = null;
        String sql =  "SELECT id,type_identifier,identifier, last_name, first_name,genre,birthday\n" +
                      "FROM person\n" +
                      " WHERE person.id = ?\n" ;

        PreparedStatement st = DatabasePostgres.connection.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
           renterUser = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
        } else {
            throw new ErrorResponse(CodeStatus.NOT_FOUND, Message.NOT_FOUND);
        }
        return renterUser;
    }
    public static List<Person> getAllRenterUser() throws Exception {
        ArrayList<Person> persons= new ArrayList<Person>();
        Person renterUser = null;
        String sql = "SELECT type_identifier, identifier, last_name, first_name, genre, \n" +
                     " birthday\n" +
                     " FROM person" ;
        try {
 DatabasePostgres.getConection();
        PreparedStatement st = DatabasePostgres.connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        
        if (rs.next()) {
           renterUser = new Person(rs.getInt("id"), 
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"));
           persons.add(renterUser);
            while (rs.next()) {
           renterUser = new Person(rs.getInt("id"), 
                        rs.getString("type_identifier"),
                        rs.getString("identifier"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("genre"),
                        rs.getString("birthday"));     
           persons.add(renterUser);     
            }
        } else {
            throw new ErrorResponse(CodeStatus.NOT_FOUND, Message.NOT_FOUND);
        }
        } catch (Exception e) {
            //response = mapper.toResponse(new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
         DatabasePostgres.close();
        return persons;
    }

   

    public static Person updateRenterUser(int id_user_create, Person aThis) {
       return null;
    }
    
    public static Person getPersonByTypeIdentifier(String typeIdentifier,String  identifier) throws Exception{     
        Person renterUser = null;
           try {
            DatabasePostgres.getConection();
            try {
                renterUser = getByTypeIdentifier(typeIdentifier, identifier);
                } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.NOT_FOUND, e.getMessage());
            }
            DatabasePostgres.close();
        } catch (Exception e) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return renterUser;
    }
      private static Person getByTypeIdentifier(String type_identifier,String  identifier) throws Exception{
          Person  renterUserPerson = null;
            try {     
                 Statement consulta = (Statement) DatabasePostgres.connection.createStatement();
                 String sql = "SELECT id, type_identifier, identifier, last_name, first_name, genre, \n" +
                              "  birthday\n" +
                              "  FROM person\n" +
                              "  WHERE status= 'Active' AND type_identifier = '"+type_identifier+"' AND identifier = '"+identifier+"';" ;

                  ResultSet rs = consulta.executeQuery(sql);
              if (rs.next()) {
                   renterUserPerson = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
            } else {
                     throw new ErrorResponse(CodeStatus.NOT_FOUND, Message.NOT_FOUND);
                   }     
            } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.NOT_FOUND, e.getMessage());
            }
        return renterUserPerson;
    }
      public static int getIdOf(String lastName,String firstName) throws Exception{
       int idPerson=0;
        try {
            DatabasePostgres.getConection();
            try {
                
                String sqlGet="SELECT id\n" +
                              " FROM person\n" +
                              " WHERE person.last_name = '"+lastName+"' AND person.first_name='"+firstName+"';";
               // renterUser = getRenterUserById(id);
                } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.NOT_FOUND, e.getMessage());
            }
            DatabasePostgres.close();
        } catch (Exception e) {
            throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return idPerson;
    }
      
}
