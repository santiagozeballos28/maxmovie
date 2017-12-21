package com.trueffect.conection.db;

import com.trueffect.tools.CodeStatus;
import com.trueffect.response.ErrorResponse;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author santiago.mamani
 */
public class DataBasePostgres {

    public static Connection connection;

    public DataBasePostgres() {
    }

    public static Connection getConection() {
        try {
            Class.forName(DataConection.DRIVER);
            connection = DriverManager.getConnection(DataConection.SERVER, DataConection.USER, DataConection.PASSWORD);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return connection;
    }

    public static void close() throws Exception {
        try {
            connection.close();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
