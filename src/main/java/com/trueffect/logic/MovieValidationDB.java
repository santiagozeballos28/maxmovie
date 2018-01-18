package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.sql.crud.PersonCrud;
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
    }

    public Either veriryDataInDataBase(Connection connection, Movie movieNew) {
        String errorMgs = "";
        ArrayList<String> listError = new ArrayList<String>();
        Either eitherMovie = movieCrud.getMovieByName(connection, movieNew.getName());
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

    public Either verifyDataUpdate(Connection connection, Movie movieNew) {
        return null;
    }

    public Either veriryCopyDuplicate(Connection connection, long idMovie) {
        return new Either();
    }
}
