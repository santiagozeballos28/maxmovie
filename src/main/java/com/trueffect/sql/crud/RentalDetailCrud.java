package com.trueffect.sql.crud;

import com.trueffect.model.AmountSaleMovie;
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
                long idCopyMovie = saleDetail.getIdCopyMovie();
                String idPrice = saleDetail.getIdPrice();
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
                        + "copy_movie_id,"
                        + "price_id)\n"
                        + "VALUES ("
                        + rentalAmount + ","
                        + "null,"
                        + "null,"
                        + rentalPrice + ","
                        + rentalAmount + ","
                        + "null,"
                        + idMasterDetail + ","
                        + "null,"
                        + idCopyMovie + ",'"
                        + idPrice + "'); ";
                query.execute(sql);
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

    public Either getReportReantalAmount(Connection connection) {

        try {
            String sql = "";
            Statement query = null;
            query = (Statement) connection.createStatement();
            sql
                    = "SELECT COPY_MOVIE.movie_id, "
                    + "   SUM (rental_amount) rental_amount \n"
                    + "  FROM (SELECT RENTAL_DETAIL.copy_movie_id , "
                    + "           SUM (rental_amount_official) rental_amount \n"
                    + "          FROM RENTAL_DETAIL\n"
                    + "         GROUP BY  RENTAL_DETAIL.copy_movie_id) AMOUNT_RENTAL,COPY_MOVIE\n"
                    + " WHERE AMOUNT_RENTAL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + " GROUP BY COPY_MOVIE.movie_id ";
            ResultSet rs = query.executeQuery(sql);
            Either eitherRes = new Either();
            AmountSaleMovie amountSaleMovie = new AmountSaleMovie();
            while (rs.next()) {
                amountSaleMovie = new AmountSaleMovie(
                        rs.getLong("movie_id"),
                        rs.getInt("rental_amount"));
                eitherRes.addModeloObjet(amountSaleMovie);
            }
            if (query != null) {
                query.close();
            }
            eitherRes.setCode(CodeStatus.OK);
            return eitherRes;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
