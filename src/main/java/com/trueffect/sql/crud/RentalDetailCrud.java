package com.trueffect.sql.crud;

import com.trueffect.model.SaleDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class RentalDetailCrud {

    public Either insert(Connection connection, long idMasterDetail, ArrayList<SaleDetail> rentailDetails) {

        try {
            String sql = "";
            Statement query = null;
            for (SaleDetail saleDetail : rentailDetails) {
                int rentalAmount = saleDetail.getAmount();
                double rentalPrice = saleDetail.getPrice();
                long idCopyMovie = saleDetail.getCopyMovieId();
                query = (Statement) connection.createStatement();
                sql
                        = "INSERT INTO RENTAL_DETAIL("
                        + "rental_amount, "
                        + "return_amount, "
                        + "return_date, "
                        + "rental_price, "
                        + "rental_amount_official, "
                        + "employee_receive, "
                        + "master_detail_id, "
                        + "note, "
                        + "copy_movie_id)\n"
                        + "VALUES ("
                        + rentalAmount + ","
                        + "null,"
                        + "null,"
                        + rentalPrice + ","
                        + rentalAmount + ","
                        + "null,"
                        + idMasterDetail + ","
                        + "null,"
                        + idCopyMovie + "); ";
                ResultSet rs = query.executeQuery(sql);
            }
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
