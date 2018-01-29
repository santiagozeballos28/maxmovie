package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.MovieCrud;
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
public class MovieValidationDB {

    protected HashMap<String, String> listData;
    private MovieCrud movieCrud;

    public MovieValidationDB() {
        listData = new HashMap<String, String>();
        movieCrud = new MovieCrud();
    }

    public Either veriryDataInDataBase(Connection connection, Movie movieNew) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        String nameMovie = OperationString.removeSpace(movieNew.getName());
        nameMovie = OperationString.addApostrophe(nameMovie);
        Either eitherMovie = movieCrud.getMovieByName(connection, nameMovie, null);
        if (eitherMovie.haveModelObject()) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_MOVIE);
            listData.put(ConstantData.DATA, movieNew.getName());
            errorMgs = OperationString.generateMesage(Message.DUPLICATE, listData);
            listError.add(errorMgs);
        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    public Either veriryCopyDuplicate(Connection connection, long idMovie) {
        return new Either();
    }
}
