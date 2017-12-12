
package com.trueffect.logica;

import com.trueffect.conection.db.DatabasePostgres;
import com.trueffect.model.Person;
import com.trueffect.response.ErrorResponse;
import com.trueffect.sql.crud.PersonCrud;
import com.trueffect.util.DataCondition;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author santiago.mamani
 */
public class PersonLogic {
   public Person createPerson(int id, Person renterUser, DataCondition conditiondata) throws Exception{
         Person res= null;
         String errorMgs="";
         boolean errorExist=false;
         int codeError=0;
          Connection connection = DatabasePostgres.getConection();
          conditiondata.complyCondition(id ,renterUser, connection);
       try {
            connection.setAutoCommit(false);
            PersonCrud.notExistPerson(connection,renterUser.getLastName(), renterUser.getFirstName());
            res =  PersonCrud.insertrenterUser(connection, id, renterUser);
            connection.commit();
        } catch (ErrorResponse e) {
            errorExist = true;
            codeError = e.getCode();
            errorMgs = e.getMessage();
            if(connection!=null)
              {
              try {
                 connection.rollback();
                 } catch (SQLException exSql) {
                    errorMgs = errorMgs + exSql.getMessage();
                 }  
             }    
          }
          finally{
            try {
                 if(connection!=null) 
                     connection.close();
             } catch (SQLException ex) {
                 errorMgs = errorMgs +"\n"+ex.getMessage();
             }
         }
          if(errorExist)
          throw new ErrorResponse(codeError, errorMgs);
       
   return res;
   }
      public Person deleteById(int idPerson, int idUserModify) throws Exception{
         Person res= null;
         String errorMgs="";
         boolean errorExist=false;
         Connection connection = DatabasePostgres.getConection();
           try {
            connection.setAutoCommit(false);
            res =  PersonCrud.getPerson(connection, idPerson);
            PersonCrud.deleteById(connection, idPerson,idUserModify);
            connection.commit();
        } catch (Exception e) {
            errorExist = true;
            errorMgs = e.getMessage();
            if(connection!=null)
              {
              try {
                 connection.rollback();
                 } catch (SQLException exSql) {
                    errorMgs = errorMgs + exSql.getMessage();
                 }
             }    
        }
          finally{
            try {
                 if(connection!=null) 
                     connection.close();
             } catch (SQLException ex) {
                 errorMgs = errorMgs +"\n"+ex.getMessage();
             }
         }
          if(errorExist)
          throw new ErrorResponse(200, errorMgs);
       
   return res;
   }
}
