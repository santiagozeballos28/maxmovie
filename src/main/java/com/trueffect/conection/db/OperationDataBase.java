package com.trueffect.conection.db;

import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class OperationDataBase {

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
