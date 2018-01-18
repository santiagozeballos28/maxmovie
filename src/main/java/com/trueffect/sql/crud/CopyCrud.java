package com.trueffect.sql.crud;

import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class CopyCrud {

    public Either insertCopy(Connection connection, long idCreateUser, long idMovie, int amountCopy) {
        Statement query = null;
        try {
            query = (Statement) connection.createStatement();
            //ident
            String sql
                    = "INSERT INTO COPY_MOVIE(\n"
                    + "amount_initial, "
                    + "amount_current, "
                    + "create_user, "
                    + "create_date, "
                    + "modifier_user, "
                    + "modifier_date, "
                    + "movie_id)\n"
                    + "VALUES ("
                    + amountCopy + ","
                    + amountCopy + ","
                    + idCreateUser + ","
                    + "current_timestamp,"
                    + "null,"
                    + "null,"
                    + idMovie + ");";
            query.execute(sql);
            if (query != null) {
                query.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
