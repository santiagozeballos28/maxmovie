package com.trueffect.sql.crud;

import com.trueffect.model.GenreMovie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author santiago.mamani
 */
public class GenreMovieCrud {

    public Either getGenre(Connection connection, String idGenreMovie) {
        Either either = new Either();
        try {
            String sql
                    = "SELECT genre_movie_id, "
                    + "       genre_name\n"
                    + "  FROM genre_movie\n"
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
                    + "  FROM genre_movie\n";
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
}
