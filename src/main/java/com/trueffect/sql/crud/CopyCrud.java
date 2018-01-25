package com.trueffect.sql.crud;

import com.trueffect.model.CopyMovie;
import com.trueffect.model.Identifier;
import com.trueffect.model.ReportCopyMovie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class CopyCrud {

    public Either insertCopy(Connection connection, long idCreateUser, long idMovie, int amountCopy) {

        try {
            //ident
            String query
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
                    + idMovie + ") returning copy_movie_id;";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherIdCopyMovie = new Either();
            if (rs.next()) {
                Identifier idCopyMovie = new Identifier(rs.getLong("copy_movie_id"));
                eitherIdCopyMovie.addModeloObjet(idCopyMovie);
            }
            if (st != null) {
                st.close();
            }
            eitherIdCopyMovie.setCode(CodeStatus.CREATED);
            return eitherIdCopyMovie;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getReportCopyMovie(Connection connection, long idCopyMovie) {
        try {
            String query
                    = "SELECT copy_movie_id, "
                    + "       movie_name, "
                    + "       amount_initial, "
                    + "       amount_current\n"
                    + "  FROM COPY_MOVIE,MOVIE\n"
                    + " WHERE COPY_MOVIE.copy_movie_id = ?"
                    + "   AND COPY_MOVIE.movie_id = MOVIE.movie_id";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idCopyMovie);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            if (rs.next()) {
                ReportCopyMovie reportCopyMovie = new ReportCopyMovie(
                        rs.getLong("copy_movie_id"),
                        rs.getString("movie_name"),
                        rs.getInt("amount_initial"),
                        rs.getInt("amount_current"));
                eitherRes.addModeloObjet(reportCopyMovie);
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

    public Either getCopyMovie(Connection connection, ArrayList<Long> idsMovie) {
        String idsString = idsMovie.toString();
        idsString = StringUtils.replace(idsString, "[", "(");
        idsString = StringUtils.replace(idsString, "]", ")");
        try {
            String query
                    = "SELECT copy_movie_id, "
                    + "       amount_initial, "
                    + "       amount_current,"
                    + "       create_date,"
                    + "       movie_id\n"
                    + "  FROM copy_movie\n"
                    + " WHERE movie_id IN " + idsString
                    + " ORDER BY create_date asc";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            while (rs.next()) {
                CopyMovie copyMovie = new CopyMovie(
                        rs.getLong("copy_movie_id"),
                        rs.getInt("amount_initial"),
                        rs.getInt("amount_current"),
                        rs.getString("create_date"),
                        rs.getLong("movie_id"));
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

    public Either getAmountCurrent(Connection connection, ArrayList<Long> idsMovie) {
        String idsString = idsMovie.toString();
        idsString = StringUtils.replace(idsString, "[", "(");
        idsString = StringUtils.replace(idsString, "]", ")");
        try {
            String query
                    = "SELECT movie_id, "
                    + "   SUM (amount_current ) total_current\n"
                    + "  FROM copy_movie\n"
                    + " WHERE movie_id IN " + idsString + "\n"
                    + " GROUP BY movie_id";
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            Either eitherRes = new Either();
            CopyMovie copyMovie = new CopyMovie();
            while (rs.next()) {
                copyMovie = new CopyMovie(
                        rs.getLong("movie_id"),
                        rs.getInt("total_current")
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

    public Either updateAmountCurrent(Connection connection, long idModifierUser, ArrayList<ModelObject> copiesMovie) {

        try {
            String sql = "";
            Statement query = null;
            for (ModelObject modelObject : copiesMovie) {
                CopyMovie copyMovie = (CopyMovie) modelObject;
                long idCopyMovie = copyMovie.getCopyMovieId();
                int amountCurrent = copyMovie.getAmountCurrent();
                query = (Statement) connection.createStatement();
                sql
                        = "UPDATE COPY_MOVIE\n"
                        + "   SET amount_current = " + amountCurrent + ","
                        + "       modifier_user = " + idModifierUser + ","
                        + "       modifier_date=current_timestamp\n"
                        + " WHERE copy_movie_id = " + idCopyMovie;
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

    public Either getAmountCurrentAll(Connection connection) {
        try {
            String query
                    = " SELECT movie_id, "
                    + "    SUM (amount_current )"
                    + "        amount_available \n"
                    + "   FROM COPY_MOVIE\n"
                    + "  GROUP BY movie_id";
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
