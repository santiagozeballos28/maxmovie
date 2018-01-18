package com.trueffect.validation;

import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ModelObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class MovieUpdate implements DataCondition {

    protected HashMap<String, String> listData;
    private MovieValidation movieValidation;
    private ArrayList<ModelObject> listGenre;

    public MovieUpdate() {
        listData = new HashMap<String, String>();
        movieValidation = new MovieValidation();
        listGenre = new ArrayList<ModelObject>();
    }

    public void setListGenre(ArrayList<ModelObject> listGenre) {
        this.listGenre = listGenre;
    }

    public Either complyCondition(ModelObject resource) {
        Movie movie = (Movie) resource;
        ArrayList<String> listError = new ArrayList<String>();
        //Validation name movie
        if (!movieValidation.isEmpty(movie.getName())) {
            movieValidation.verifySize(movie.getName(), ConstantData.NAME_MOVIE, ConstantData.MAX_NAME_MOVIE, listError);
            movieValidation.verifyName(movie.getName(), listError);
        }
        //Validation genre movie
        if (!movieValidation.isEmpty(movie.getGenreId())) {
            movieValidation.verifySize(movie.getGenreId(), ConstantData.NAME_GENRE_MOVIE, ConstantData.MAX_NAME_GENRE_MOVIE, listError);
            movieValidation.verifyGenre(movie.getGenreId(), listGenre, listError);
        }
        //Validation actors
        ArrayList<String> actors = movie.getActor();
        if (!movie.getActor().isEmpty()) {
            movieValidation.verifySizeNameActors(actors, listError);
            movieValidation.verifyNamesOfActors(actors, listError);
        }
        //Validation director
        if (!movieValidation.isEmpty(movie.getDirector())) {
            movieValidation.verifySize(movie.getDirector(), ConstantData.NAME_DIRECTOR_MOVIE, ConstantData.MAX_NAME_DIRECTOR_MOVIE, listError);
            movieValidation.verifyDirector(movie.getDirector(), listError);
        }
        //Validation year
        if (movie.getYear() != 0) {
            movieValidation.verifyYear(movie.getYear(), listError);
        }
        //Validation oscar nomination
        movieValidation.verifyOscarNomination(movie.getOscarNomination(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {

            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
