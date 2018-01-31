package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.model.GenreMovie;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.GenreMovieCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class GenreMovieValidationDB {

    protected HashMap<String, String> listData;
    private GenreMovieCrud genreMovieCrud;

    public GenreMovieValidationDB() {
        listData = new HashMap<String, String>();
        genreMovieCrud = new GenreMovieCrud();
    }

    public Either veriryDataInDataBase(Connection connection, GenreMovie genreMovie) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        String idGenreMovie = genreMovie.getIdGenre().trim().toUpperCase();
        String nameGenreMovie = OperationString.generateName(genreMovie.getNameGenre());
        nameGenreMovie = OperationString.addApostrophe(nameGenreMovie);
        Either eitherGenreMovie = genreMovieCrud.getGenreById(connection, idGenreMovie);
        if (eitherGenreMovie.haveModelObject()) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.DATA, genreMovie.getIdGenre().trim());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        eitherGenreMovie = genreMovieCrud.getGenreMovieByName(connection, nameGenreMovie);
        if (eitherGenreMovie.haveModelObject()) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_GENRE_MOVIE);
            listData.put(ConstantData.DATA, genreMovie.getNameGenre().trim());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    public Either veriryUpdateInDataBase(Connection connection, GenreMovie genreMovie) {
        ArrayList<String> listError = new ArrayList<String>();
        String idGenreMovieNew = genreMovie.getIdGenre().trim().toUpperCase();
        String nameGenreMovie = OperationString.generateName(genreMovie.getNameGenre());
        nameGenreMovie = OperationString.addApostrophe(nameGenreMovie);
        Either eitherGenreMovie = genreMovieCrud.getGenreMovieByName(connection, nameGenreMovie);
        if (eitherGenreMovie.haveModelObject()) {
            GenreMovie genreMovieDB = (GenreMovie) eitherGenreMovie.getFirstObject();
            String idGenreMovieDB = genreMovieDB.getIdGenre().trim();
            if (!idGenreMovieNew.equals(idGenreMovieDB)) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.NAME);
                listData.put(ConstantData.DATA, genreMovie.getNameGenre().trim());
                String errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
                listError.add(errorMgs);
            }
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
