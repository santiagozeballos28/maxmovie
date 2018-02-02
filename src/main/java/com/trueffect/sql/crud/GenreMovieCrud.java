package com.trueffect.sql.crud;

import com.trueffect.model.GenreMovie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class GenreMovieCrud {

    public Either getGenreById(Connection connection, String idGenreMovie) {
        Either either = new Either();
        try {
            String sql
                    = "SELECT genre_movie_id, "
                    + "       genre_name\n"
                    + "  FROM GENRE_MOVIE\n"
                    + " WHERE genre_movie_id = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, idGenreMovie);
            ResultSet rs = st.executeQuery();
            GenreMovie genreMovie = new GenreMovie();
            if (rs.next()) {
                genreMovie = new GenreMovie(
                        rs.getString("genre_movie_id"),
                        rs.getString("genre_name"));
            }
            if (st != null) {
                st.close();
            }
            either.setCode(CodeStatus.OK);
            either.addModeloObjet(genreMovie);
        } catch (Exception exception) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(exception.getMessage());
        }
        return either;
    }

    public Either getAllGenre(Connection connection) {
        Either either = new Either();
        try {
            String sql
                    = "SELECT genre_movie_id, "
                    + "       genre_name\n"
                    + "  FROM GENRE_MOVIE\n";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                GenreMovie genreMovie = new GenreMovie(
                        rs.getString("genre_movie_id"),
                        rs.getString("genre_name"));
                either.addModeloObjet(genreMovie);
            }
            if (st != null) {
                st.close();
            }
            either.setCode(CodeStatus.OK);
        } catch (Exception exception) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(exception.getMessage());
        }
        return either;
    }

    public Either insert(Connection connection, GenreMovie genreMovie) {
        Either either = new Either();
        try {
            String idGenreMovie = genreMovie.getIdGenre();
            String nameGenreMovie = genreMovie.getNameGenre();
            String sql
                    = "INSERT INTO GENRE_MOVIE("
                    + "genre_movie_id, "
                    + "genre_name)\n"
                    + "VALUES ('"
                    + idGenreMovie + "','"
                    + nameGenreMovie + "');";
            PreparedStatement st = connection.prepareStatement(sql);
            st.execute();
            if (st != null) {
                st.close();
            }
            return new Either();
        } catch (Exception exception) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(exception.getMessage());
        }
        return either;
    }

    public Either getGenreMovieByName(Connection connection, String nameGenreMovie) {
        Either either = new Either();
        try {
            String sql
                    = "SELECT genre_movie_id, "
                    + "       genre_name\n"
                    + "  FROM GENRE_MOVIE\n"
                    + " WHERE genre_name = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, nameGenreMovie);
            ResultSet rs = st.executeQuery();
            GenreMovie genreMovie = new GenreMovie();
            if (rs.next()) {
                genreMovie = new GenreMovie(
                        rs.getString("genre_movie_id"),
                        rs.getString("genre_name"));
            }
            if (st != null) {
                st.close();
            }
            either.setCode(CodeStatus.OK);
            either.addModeloObjet(genreMovie);
        } catch (Exception exception) {
            either.setCode(CodeStatus.INTERNAL_SERVER_ERROR);
            either.addError(exception.getMessage());
        }
        return either;
    }

    public Either update(Connection connection, GenreMovie genreMovie) {
        try {
            String idGenreMovie = genreMovie.getIdGenre();
            String nameGenreMovie = genreMovie.getNameGenre();
            if (StringUtils.isNotBlank(nameGenreMovie)) {
                String sql
                        = "UPDATE GENRE_MOVIE\n"
                        + "   SET genre_name='" + nameGenreMovie + "'"
                        + " WHERE genre_movie_id = '" + idGenreMovie + "'";
                PreparedStatement st = connection.prepareStatement(sql);
                st.execute();
                if (st != null) {
                    st.close();
                }
            }
            return new Either();
        } catch (Exception exception) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
    }
}
