package com.trueffect.sql.crud;

import com.trueffect.model.CopyMovie;
import com.trueffect.model.SaleDetail;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class BuyDetailCrud {

    public Either insert(Connection connection, long idMasterDetail, ArrayList<SaleDetail> buyDetails) {

        try {

            String sql = "";
            Statement query = null;
            for (SaleDetail saleDetail : buyDetails) {
                int buyAmount = saleDetail.getAmount();
                double buyPrice = saleDetail.getPrice();
                long idCopyMovie = saleDetail.getCopyMovieId();
                query = (Statement) connection.createStatement();
                sql
                        = "INSERT INTO BUY_DETAIL("
                        + "buy_amount, "
                        + "buy_price, "
                        + "master_detail_id, "
                        + "copy_movie_id)\n"
                        + "VALUES ("
                        + buyAmount + ","
                        + buyPrice + ","
                        + idMasterDetail + ","
                        + idCopyMovie + ");";
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

    public Either getAmountOfficialAll(Connection connection) {
        try {
            String query
                    = " SELECT COPY_MOVIE.movie_id, "
                    + "        SUM( rental_amount_official) "
                    + "        rental_amount \n"
                    + "   FROM COPY_MOVIE, RENTAL_DETAIL\n"
                    + "  WHERE COPY_MOVIE.copy_movie_id=RENTAL_DETAIL.copy_movie_id\n"
                    + "  GROUP BY COPY_MOVIE.movie_id";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            CopyMovie copyMovie = new CopyMovie();
            while (rs.next()) {
                copyMovie = new CopyMovie(
                        rs.getLong("movie_id"),
                        rs.getInt("amount_available")
                );
                eitherRes.addModeloObjet(copyMovie);
            }
            if (st != null) {
                st.close();
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
