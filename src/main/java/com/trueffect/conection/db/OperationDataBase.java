package com.trueffect.conection.db;

import com.trueffect.response.ErrorResponse;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ErrorContainer;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author santiago.mamani
 */
public class OperationDataBase {

    public static void connectionClose(Connection connection, ErrorContainer errorContainer) {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                errorContainer.addError(new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
            }
        }
    }

    public static void connectionRollback(Connection connection, ErrorContainer errorContainer) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                errorContainer.addError(new ErrorResponse(CodeStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
            }
        }

    }
}
