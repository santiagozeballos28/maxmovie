package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.GenreMovie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Santiago
 */
public class GenreMovieCreate {

    protected HashMap<String, String> listData;
    private MovieValidation movieValidation;

    public GenreMovieCreate() {
        listData = new HashMap<String, String>();
        movieValidation = new MovieValidation();
    }

    public Either complyCondition(ModelObject resource) {
        Either eitherRes = verifyEmpty(resource);
        if (eitherRes.existError()) {
            return eitherRes;
        }
        return verifyData(resource);
    }

    protected Either verifyEmpty(ModelObject resource) {
        GenreMovie genreMovie = (GenreMovie) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        //Validation empty id genre
        if (StringUtils.isBlank(genreMovie.getIdGenre())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty genre movie
        if (StringUtils.isBlank(genreMovie.getNameGenre())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_GENRE_MOVIE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    protected Either verifyData(ModelObject resource) {
        GenreMovie genreMovie = (GenreMovie) resource;
        ArrayList<String> listError = new ArrayList<String>();
        //Validation of identifier genre movie
        movieValidation.verifyIdGenreMovie(genreMovie.getIdGenre(), listError);
        //Validation of id name movie size
        movieValidation.verifySize(genreMovie.getIdGenre(), ConstantData.IDENTIFIER, ConstantData.MAX_LENGTH_IDENTIFIER, listError);
        //Validation of name genre movie
        movieValidation.verifyGenre(genreMovie.getNameGenre(), listError);
        //Validation of name genre movie size
        movieValidation.verifySize(genreMovie.getNameGenre(), ConstantData.NAME_GENRE_MOVIE, ConstantData.MAX_NAME_GENRE_MOVIE, listError);
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
