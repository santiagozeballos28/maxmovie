package com.trueffect.conection.db;

import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 * @author santiago.mamani
 */
public class DataBasePostgres {

    public static Connection connection;

    public DataBasePostgres() {
    }

    public static Connection getConection()  throws Either{
        try {
            Class.forName(DataConection.DRIVER);
            connection = DriverManager.getConnection(DataConection.SERVER, DataConection.USER, DataConection.PASSWORD);
            connection.setAutoCommit(false);
        } catch (Exception exception) {
            ArrayList<String> listError =  new ArrayList<String>();
            listError.add(exception.getMessage());
          throw new Either(CodeStatus.INTERNAL_SERVER_ERROR,listError);
        }
        return connection;
    }

    public static void close() throws Either {
        try {
            connection.close();
        } catch (Exception exception) {
            ArrayList<String> listError =  new ArrayList<String>();
            listError.add(exception.getMessage());
          throw new Either(CodeStatus.INTERNAL_SERVER_ERROR,listError);
        }
    }
}
