package com.trueffect.validation;


import com.trueffect.model.GenreMovie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Santiago
 */
public class GenreMovieUpdate {

    protected HashMap<String, String> listData;
    private MovieValidation movieValidation;

    public GenreMovieUpdate() {
        listData = new HashMap<String, String>();
        movieValidation = new MovieValidation();
    }

    public Either complyCondition(ModelObject resource) {
        GenreMovie genreMovie = (GenreMovie) resource;
        ArrayList<String> listError = new ArrayList<String>();
        if (StringUtils.isNotBlank(genreMovie.getIdGenre())) {
            movieValidation.verifyIdGenreMovie(genreMovie.getIdGenre(), listError);
            movieValidation.verifySize(genreMovie.getIdGenre(), ConstantData.IDENTIFIER, ConstantData.MAX_LENGTH_IDENTIFIER, listError);
        }
        if (StringUtils.isNotBlank(genreMovie.getNameGenre())) {
            movieValidation.verifyGenre(genreMovie.getNameGenre(), listError);
            movieValidation.verifySize(genreMovie.getNameGenre(), ConstantData.NAME_GENRE_MOVIE, ConstantData.MAX_NAME_GENRE_MOVIE, listError);
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
