/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.transaction;

import com.trueffect.conection.db.DatabasePostgres;
import com.trueffect.response.ErrorResponse;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author santiago.mamani
 */
public class Transaction {
    public static ModelObject insertObject(int id, ModelObject object) throws Exception{
         Connection connection = DatabasePostgres.getConection();   
         ModelObject res= null;
         String errorSql="";
         try {
             System.out.println("TRANSACCION");
            connection.setAutoCommit(false);
            res = object.insertResourse(id, connection);
            connection.commit();
        } catch (Exception e) {
            if(connection!=null)
             {
                try {
                    connection.rollback();
                 } catch (SQLException exSql) {
                    errorSql = exSql.getMessage();
                 }
                 try {                 
                    connection.close();
                 } catch (SQLException exSql) {
                     errorSql = errorSql +"\n" + exSql.getMessage();
                 }  
             }
            new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage()+"\n"+ errorSql);
            
        }
        
         
         return res;
         
    }
}
