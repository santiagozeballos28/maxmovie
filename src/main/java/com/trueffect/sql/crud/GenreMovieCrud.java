/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.sql.crud;

import com.trueffect.model.GenreMovie;
import com.trueffect.model.Job;
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
            //st.setInt(1, idUser);
            st.setString(1,idGenreMovie);
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
}