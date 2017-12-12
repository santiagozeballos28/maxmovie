package com.trueffect.conection.db;

import com.trueffect.tools.CodeStatus;
import com.trueffect.response.ErrorResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author santiago.mamani
 */
public class DatabasePostgres {

    public static Connection connection;

    public DatabasePostgres() {
    }

    public static Connection getConection() throws Exception {
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
        } catch (SQLException ex) {
            new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
