package com.trueffect.sql.crud;

import com.trueffect.model.ReportSale;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData.OperationSale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author santiago.mamani
 */
public class ReportSaleCrud {

    public Either getAmountPriceSubTotaRental(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT RENTAL_AMOUNT.movie_id,"
                    + "       amount_sub_total ,"
                    + "       price_sub_total\n"
                    + "  FROM (SELECT COPY_MOVIE.movie_id, "
                    + "           SUM (rental_amount) amount_sub_total \n"
                    + "          FROM  RENTAL_DETAIL,COPY_MOVIE\n"
                    + "         WHERE master_detail_id = ?"
                    + "           AND RENTAL_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "         GROUP BY COPY_MOVIE.movie_id)RENTAL_AMOUNT, \n"
                    + "       (SELECT COPY_MOVIE.movie_id, "
                    + "           SUM (rental_price) price_sub_total\n"
                    + "          FROM  RENTAL_DETAIL,COPY_MOVIE\n"
                    + "         WHERE master_detail_id = ?"
                    + "           AND RENTAL_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "         GROUP BY COPY_MOVIE.movie_id ) RENTAL_PRICE\n"
                    + " WHERE RENTAL_AMOUNT.movie_id = RENTAL_PRICE.movie_id ";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idMasterDetail);
            ResultSet rs = st.executeQuery();

            Either eitherRes = new Either();
            ReportSale reportSale = new ReportSale();
            while (rs.next()) {
                reportSale = new ReportSale(
                        rs.getLong("movie_id"),
                        rs.getInt("amount_sub_total"),
                        rs.getDouble("price_sub_total"));
                eitherRes.addModeloObjet(reportSale);
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

    public Either getAmountPriceSubTotalBuy(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT BUY_AMOUNT.movie_id,"
                    + "       amount_sub_total ,"
                    + "       price_sub_total\n"
                    + "  FROM (SELECT COPY_MOVIE.movie_id, "
                    + "           SUM (buy_amount) amount_sub_total \n"
                    + "          FROM  BUY_DETAIL,COPY_MOVIE\n"
                    + "         WHERE master_detail_id = ? "
                    + "           AND BUY_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "         GROUP BY COPY_MOVIE.movie_id)BUY_AMOUNT, \n"
                    + "       (SELECT COPY_MOVIE.movie_id, "
                    + "           SUM (buy_price) price_sub_total\n"
                    + "          FROM BUY_DETAIL,COPY_MOVIE\n"
                    + "         WHERE master_detail_id = ?"
                    + "           AND BUY_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "         GROUP BY COPY_MOVIE.movie_id ) BUY_PRICE\n"
                    + " WHERE BUY_AMOUNT.movie_id = BUY_PRICE.movie_id ";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idMasterDetail);
            ResultSet rs = st.executeQuery();

            Either eitherRes = new Either();
            ReportSale reportSale = new ReportSale();
            while (rs.next()) {
                reportSale = new ReportSale(
                        rs.getLong("movie_id"),
                        rs.getInt("amount_sub_total"),
                        rs.getDouble("price_sub_total"));
                eitherRes.addModeloObjet(reportSale);
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

    public Either getMoviePriceSaleRental(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT DISTINCT MOVIE.movie_id, "
                    + "       movie_name, "
                    + "       price,"
                    + "       price_name\n"
                    + "  FROM RENTAL_DETAIL,COPY_MOVIE, PRICE, MOVIE\n"
                    + " WHERE RENTAL_DETAIL.master_detail_id = ?"
                    + "   AND RENTAL_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id"
                    + "   AND COPY_MOVIE.movie_id = MOVIE.movie_id"
                    + "   AND RENTAL_DETAIL.price_id = PRICE.price_id ";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idMasterDetail);
            ResultSet rs = st.executeQuery();

            Either eitherRes = new Either();
            ReportSale reportSale = new ReportSale();
            while (rs.next()) {
                reportSale = new ReportSale(
                        rs.getLong("movie_id"),
                        rs.getString("movie_name"),
                        rs.getDouble("price"),
                        rs.getString("price_name"));
                eitherRes.addModeloObjet(reportSale);
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

    public Either getMoviePriceSaleBuy(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT DISTINCT MOVIE.movie_id, "
                    + "       movie_name, "
                    + "       price,"
                    + "       price_name\n"
                    + "  FROM BUY_DETAIL,COPY_MOVIE, PRICE, MOVIE\n"
                    + " WHERE BUY_DETAIL.master_detail_id = ? "
                    + "   AND BUY_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id "
                    + "   AND COPY_MOVIE.movie_id = MOVIE.movie_id "
                    + "   AND BUY_DETAIL.price_id = PRICE.price_id ";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idMasterDetail);
            ResultSet rs = st.executeQuery();

            Either eitherRes = new Either();
            ReportSale reportSale = new ReportSale();
            while (rs.next()) {
                reportSale = new ReportSale(
                        rs.getLong("movie_id"),
                        rs.getString("movie_name"),
                        rs.getDouble("price"),
                        rs.getString("price_name"));
                eitherRes.addModeloObjet(reportSale);
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

    public Either getReportRental(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT DISTINCT MOVIE.movie_id,MOVIE.movie_name, price, amount_sub_total,price_sub_total,price_name\n"
                    + "  FROM (SELECT RENTAL_AMOUNT.movie_id,\n"
                    + "               amount_sub_total ,\n"
                    + "               price_sub_total\n"
                    + "          FROM (SELECT COPY_MOVIE.movie_id, \n"
                    + "                   SUM (rental_amount) amount_sub_total \n"
                    + "                  FROM RENTAL_DETAIL,COPY_MOVIE\n"
                    + "                 WHERE master_detail_id = " + idMasterDetail
                    + "                   AND RENTAL_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "                 GROUP BY COPY_MOVIE.movie_id)RENTAL_AMOUNT, \n"
                    + "               (SELECT COPY_MOVIE.movie_id,\n"
                    + "                   SUM (rental_price) price_sub_total\n"
                    + "                  FROM  RENTAL_DETAIL,COPY_MOVIE\n"
                    + "                 WHERE master_detail_id = " + idMasterDetail
                    + "                   AND RENTAL_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "                 GROUP BY COPY_MOVIE.movie_id ) RENTAL_PRICE\n"
                    + "         WHERE RENTAL_AMOUNT.movie_id = RENTAL_PRICE.movie_id) DETAIL_SUB_TOTAL, MOVIE, PRICE, COPY_MOVIE , RENTAL_DETAIL\n"
                    + " WHERE DETAIL_SUB_TOTAL.movie_id = MOVIE.movie_id\n"
                    + "   AND MOVIE.movie_id = COPY_MOVIE.movie_id\n"
                    + "   AND RENTAL_DETAIL.price_id = PRICE.price_id";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            ReportSale reportSale = new ReportSale();
            while (rs.next()) {
                reportSale = new ReportSale(
                        rs.getLong("movie_id"),
                        rs.getString("movie_name"),
                        rs.getDouble("price"),
                        rs.getInt("amount_sub_total"),
                        rs.getDouble("price_sub_total"),
                        rs.getString("price_name"));
                eitherRes.addModeloObjet(reportSale);
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

    public Either getReportBuy(Connection connection, long idMasterDetail) {

        try {
            String query
                    = "SELECT DISTINCT MOVIE.movie_id,MOVIE.movie_name, price, amount_sub_total,price_sub_total,price_name\n"
                    + "  FROM (SELECT BUY_AMOUNT.movie_id,\n"
                    + "               amount_sub_total ,\n"
                    + "               price_sub_total\n"
                    + "          FROM (SELECT COPY_MOVIE.movie_id, \n"
                    + "                   SUM (buy_amount) amount_sub_total \n"
                    + "                  FROM BUY_DETAIL,COPY_MOVIE\n"
                    + "                 WHERE master_detail_id = "+idMasterDetail
                    + "                   AND BUY_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "                 GROUP BY COPY_MOVIE.movie_id)BUY_AMOUNT, \n"
                    + "               (SELECT COPY_MOVIE.movie_id,\n"
                    + "                   SUM (buy_price) price_sub_total\n"
                    + "                  FROM  BUY_DETAIL,COPY_MOVIE\n"
                    + "                 WHERE master_detail_id = "+idMasterDetail
                    + "                   AND BUY_DETAIL.copy_movie_id = COPY_MOVIE.copy_movie_id\n"
                    + "                 GROUP BY COPY_MOVIE.movie_id ) BUY_PRICE\n"
                    + "         WHERE BUY_AMOUNT.movie_id = BUY_PRICE.movie_id) DETAIL_SUB_TOTAL, MOVIE, PRICE, COPY_MOVIE , BUY_DETAIL\n"
                    + " WHERE DETAIL_SUB_TOTAL.movie_id = MOVIE.movie_id\n"
                    + "   AND MOVIE.movie_id = COPY_MOVIE.movie_id\n"
                    + "   AND BUY_DETAIL.price_id = PRICE.price_id";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            ReportSale reportSale = new ReportSale();
            while (rs.next()) {
                reportSale = new ReportSale(
                        rs.getLong("movie_id"),
                        rs.getString("movie_name"),
                        rs.getDouble("price"),
                        rs.getInt("amount_sub_total"),
                        rs.getDouble("price_sub_total"),
                        rs.getString("price_name"));
                eitherRes.addModeloObjet(reportSale);
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
