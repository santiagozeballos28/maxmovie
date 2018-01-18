package com.trueffect.sql.crud;

import com.trueffect.model.Identifier;
import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
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
public class MovieCrud {

    public Either insertMovie(Connection connection, long idCreateUser, Movie movie) {
        Statement query = null;
        try {
            String name = movie.getName();
            String director = movie.getDirector();
            int year = movie.getYear();
            int oscarNomination = movie.getOscarNomination();
            String genreMovieId = movie.getGenreId();
            Either eitherIdentifier = new Either();
            query = (Statement) connection.createStatement();
            //ident
            String sql
                    = "INSERT INTO MOVIE(\n"
                    + "movie_name, "
                    + "director_name,"
                    + "movie_year, "
                    + "oscar_nomination, "
                    + "genre_movie_id, "
                    + "create_user, "
                    + "create_date, "
                    + "modifier_user, "
                    + "modifier_date, "
                    + "status)\n"
                    + "VALUES ('"
                    + name + "',' "
                    + director + "', "
                    + year + ", "
                    + oscarNomination + ", '"
                    + genreMovieId + "',"
                    + idCreateUser + ","
                    + "current_timestamp, "
                    + "null,"
                    + "null,"
                    + "'Active') returning movie_id;";

            ResultSet rs = query.executeQuery(sql);
            if (rs.next()) {
                eitherIdentifier.addModeloObjet(new Identifier(rs.getLong("movie_id")));
            }

            if (query != null) {
                query.close();
            }
            eitherIdentifier.setCode(CodeStatus.CREATED);
            return eitherIdentifier;
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getMovie(Connection connection, long idMovie, String status) {
        try {
            String query
                    = "SELECT movie_id, "
                    + "       movie_name, "
                    + "       director_name, "
                    + "       movie_year, "
                    + "       oscar_nomination, "
                    + "       genre_movie_id,"
                    + "       status\n"
                    + "  FROM movie\n"
                    + " WHERE movie_id=? ";
            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1, idMovie);
            ResultSet rs = st.executeQuery();
            Movie movie = new Movie();
            if (rs.next()) {
                movie = new Movie(
                        rs.getLong("movie_id"),
                        rs.getString("movie_name"),
                        rs.getString("genre_movie_id"),
                        rs.getString("director_name"),
                        rs.getInt("movie_year"),
                        rs.getInt("oscar_nomination"),
                        rs.getString("status")
                );
            }
            if (st != null) {
                st.close();
            }
            return new Either(CodeStatus.OK, movie);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either updateMovie(Connection connection, long idModifyUser, Movie movie) {
        try {
            String sql
                    = "UPDATE MOVIE\n"
                    + "   SET ";
            String name = movie.getName();
            if (StringUtils.isNotBlank(name)) {
                sql = sql + "movie_name = '" + name + "', ";
            }
            String director = movie.getDirector();
            if (StringUtils.isNotBlank(director)) {
                sql = sql + "director_name = '" + director + "', ";
            }
            int year = movie.getYear();
            if (year != 0) {
                sql = sql + "movie_year = " + year + ", ";
            }
            int oscarNomination = movie.getOscarNomination();
            if (oscarNomination != 0) {
                sql = sql + "oscar_nomination = " + oscarNomination + ", ";
            }
            String genreId = movie.getGenreId();
            if (StringUtils.isNotBlank(genreId)) {
                sql = sql + "genre_movie_id = '" + genreId + "', ";
            }
            sql = sql
                    + "       modifier_date =  current_timestamp,"
                    + "       modifier_user = ?"
                    + " WHERE movie_id = ?";
            long idMovie = movie.getId();
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, idModifyUser);
            st.setLong(2, idMovie);
            st.execute();
            if (st != null) {
                st.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either updateStatusMovie(Connection connection, long idModifyUser, long idMovie, String status) {
        try {
            String sql
                    = "UPDATE MOVIE\n"
                    + "   SET status=?, "
                    + "       modifier_date =  current_timestamp ,"
                    + "       modifier_user= ?"
                    + " WHERE movie_id = ?";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setLong(2, idModifyUser);
            st.setLong(3, idMovie);
            st.execute();
            if (st != null) {
                st.close();
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }

    public Either getMovieByName(Connection connection, String name, String status) {
        try {
            String query
                    = "SELECT movie_id,"
                    + "       movie_name, "
                    + "       director_name, "
                    + "       movie_year, "
                    + "       oscar_nomination, "
                    + "       genre_movie_id\n"
                    + "       status\n"
                    + "  FROM MOVIE\n"
                    + " WHERE movie_name = ?";
            if (StringUtils.isNotBlank(status)) {
                query = query + " AND status = '" + status + "'";
            }
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Movie movie = new Movie();
            if (rs.next()) {
                movie = new Movie(
                        rs.getLong("movie_id"),
                        rs.getString("movie_name"),
                        rs.getString("genre_movie_id"),
                        rs.getString("director_name"),
                        rs.getInt("movie_year"),
                        rs.getInt("oscar_nomination"),
                        rs.getString("status")
                );
            }
            if (st != null) {
                st.close();
            }

            return new Either(CodeStatus.OK, movie);
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
