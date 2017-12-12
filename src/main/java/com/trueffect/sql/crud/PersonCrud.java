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
                String typeIdentifier = renterUser.getTypeIdentifier().trim();
                System.out.println("TYPE_IDENTIFIER: " + typeIdentifier + " Sise: "+typeIdentifier.length());
                String identifier = renterUser.getIdentifier().trim();
                System.out.println("IDENTIFIER: " + identifier + " Sise: "+identifier.length());
                String lastName = renterUser.getLastName().trim();
                String firstName = renterUser.getFirstName().trim();
                String genre = renterUser.getGenre().trim();
                String birthay = renterUser.getBirthday().trim();
                query = (Statement) connection.createStatement();
                String sql=
                "INSERT INTO person(\n" +
                " type_identifier, identifier, last_name, first_name, genre, \n" +
                " birthday, date_create, user_create, date_modifier, user_modifier, \n" +
                " status)\n" +
                " VALUES ('"+typeIdentifier+"','"+identifier+"','"+lastName+"', '"
                        +firstName+"','"+ genre+"','"+birthay+"','2017-12-08','"+idJob+"',null,null,'Active')";
      
                query.execute(sql);
                //ResultSet rs = query.executeQuery(sql);
                 String msg = "The user " + firstName + " " + lastName + " has been successfully inserted";
                 renterUserInserted = getByTypeIdentifier(connection,typeIdentifier, identifier);
              } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage()+" Isert1");
            }finally{
                 try {
                   if(query!=null) query.close();                
                                   
                 } catch (SQLException ex) {
                    throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, ex.getMessage() +" Isert2");    
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
    
    public static Person getPersonByTypeIdentifier(String typeIdentifier,String  identifier,   Connection connection) throws Exception{     
        Person renterUser = null;
            try {
                 renterUser = getByTypeIdentifier(connection,typeIdentifier, identifier);
                } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.NOT_FOUND, e.getMessage());
            }
        
        
        return renterUser;
    }
      private static Person getByTypeIdentifier(Connection connection,String type_identifier,String  identifier) throws Exception{
          Person  renterUserPerson = null;
            try {     
                 Statement consulta = (Statement) connection.createStatement();
                 String sql = "SELECT id, type_identifier, identifier, last_name, first_name, genre, \n" +
                              "  birthday\n" +
                              "  FROM person\n" +
                              "  WHERE status= 'Active' AND type_identifier = '"+type_identifier+"' AND identifier = '"+identifier+"';" ;

                  ResultSet rs = consulta.executeQuery(sql);
                  if(rs.next()){
                  renterUserPerson = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
                  }
                
                  
                       
            } catch (Exception e) {
                System.out.println("NOT_found_segundo");
                throw new ErrorResponse(CodeStatus.NOT_FOUND,e.getMessage());
            }
        return renterUserPerson;
    }
      public static boolean notExistPerson(Connection connection,String lastName,String firstName) throws Exception{
       boolean res = false;
            try {
                String sqlGet="SELECT id\n" +
                              " FROM person\n" +
                              " WHERE person.last_name = '"+lastName+"' AND person.first_name='"+firstName+"';";
                PreparedStatement st = connection.prepareStatement(sqlGet);
                ResultSet rs = st.executeQuery();
                if(rs.next()){
                  throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, Message.THE_NAMES_ALREADY_EXIST);
                }else res = true;
               } catch (Exception e) {
                      throw e;
                }
        return res;
    }
// public static boolean isActived(Connection connection, int id) throws Exception {
//            boolean res= false;
//             
//             try {
//                String sql="SELECT id\n" +
//                              "FROM person\n" +
//                              "WHERE status = 'Active' AND id =?;";
//                PreparedStatement st = connection.prepareStatement(sql);
//                st.setInt(1, id);
//                ResultSet rs = st.executeQuery();
//                if(rs.next()){
//                  res =true;
//                }else{
//                throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, Message.NOT_RESOURCE);
//                }
//                 } catch (Exception e) {
//                      throw e;
//                }     
//            return res; 
//    }
      
       public static Person getPerson(Connection connection, int id) throws Exception {
         Person renterUser = null;
         String sql =  "SELECT id,type_identifier,identifier, last_name, first_name,genre,birthday\n" +
                      "FROM person\n" +
                      " WHERE status = 'Active' AND person.id = ?\n" ;

         PreparedStatement st = connection.prepareStatement(sql);
         st.setInt(1, id);
         ResultSet rs = st.executeQuery();
         if (rs.next()) {
           renterUser = new Person(rs.getInt("id"), rs.getString("type_identifier"), rs.getString("identifier"), rs.getString("last_name"), rs.getString("first_name"), rs.getString("genre"), rs.getString("birthday"));
        } else {
            throw new ErrorResponse(CodeStatus.NOT_FOUND, Message.NOT_FOUND);
        }
        return renterUser; 
    }
    public static Person deleteById(Connection connection, int id, int idUserModifier) throws Exception {
      Person renterUserInserted=null; 
             
            try {
                String sql=
                "UPDATE person\n" +
                "SET status='Inactive', date_modifier=  current_date , user_modifier= ?" +
                "WHERE id = ?;";                 
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1, idUserModifier);
                st.setInt(2, id);
                st.execute();    
              } catch (Exception e) {
                throw new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }         
            return renterUserInserted; 
    }
      
}
