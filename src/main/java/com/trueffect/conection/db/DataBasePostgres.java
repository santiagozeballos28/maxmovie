package com.trueffect.conection.db;

import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class DataBasePostgres {

    public DataBasePostgres() {
    }

    public static Connection getConection() throws Either {
        Connection connection = null;
        try {
            Class.forName(DataConection.DRIVER);
            connection = DriverManager.getConnection(DataConection.SERVER, DataConection.USER, DataConection.PASSWORD);
            connection.setAutoCommit(false);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            throw new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        return connection;
    }

    public static void connectionClose(Connection connection, Either either) {
        try {
            connection.close();
        } catch (SQLException ex) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(ex.getMessage());
        }
    }

    public static void connectionCommit(Connection connection) throws Either {
        ArrayList<String> listError = new ArrayList<String>();
        try {
            connection.commit();
        } catch (SQLException ex) {
            listError.add(ex.getMessage());
            throw new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public static void connectionRollback(Connection connection, Either either) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(ex.getMessage());
        }
    }
}
