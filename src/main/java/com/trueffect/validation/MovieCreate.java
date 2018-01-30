package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.DataCondition;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class MovieCreate implements DataCondition {

    protected HashMap<String, String> listData;
    private MovieValidation movieValidation;
    private ArrayList<ModelObject> listIdsGenre;

    public MovieCreate() {
        listData = new HashMap<String, String>();
        movieValidation = new MovieValidation();
        listIdsGenre = new ArrayList<ModelObject>();
    }

    public void setListIdsGenre(ArrayList<ModelObject> listIdsGenre) {
        this.listIdsGenre = listIdsGenre;
    }

    @Override
    public Either complyCondition(ModelObject resource) {
        Either eitherRes = verifyEmpty(resource);
        if (eitherRes.existError()) {
            return eitherRes;
        }
        return verifyData(resource);
    }

    protected Either verifyEmpty(ModelObject resource) {
        Movie movie = (Movie) resource;
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        //Validation empty name movie
        if (StringUtils.isBlank(movie.getName())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_MOVIE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty genre movie
        if (StringUtils.isBlank(movie.getGenreId())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_GENRE_MOVIE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty director
        if (StringUtils.isBlank(movie.getDirector())) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_DIRECTOR_MOVIE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
          //Validation empty year
        if (movie.getYear() == null) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.YEAR);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty oscar nomination
        if (movie.getOscarNomination() == null) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.OSCAR_NOMINATION_MOVIE);
            errorMessages = OperationString.generateMesage(Message.EMPTY_DATA, listData);
            listError.add(errorMessages);
        }
        //Validation empty actors
        if (movie.getActor().isEmpty()) {
            listData.clear();
            listData.put(ConstantData.TYPE_DATA, ConstantData.NAME_ACTOR_MOVIE);
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
        Movie movie = (Movie) resource;
        ArrayList<String> listError = new ArrayList<String>();
        //Validation of name movie size
        movieValidation.verifySize(movie.getName(), ConstantData.NAME_MOVIE, ConstantData.MAX_NAME_MOVIE, listError);
        //Validation of genre movie size
        movieValidation.verifySize(movie.getGenreId(), ConstantData.NAME_GENRE_MOVIE, ConstantData.MAX_NAME_GENRE_MOVIE, listError);
        //Validation of the actor's size
        movieValidation.verifySizeNameActors(movie.getActor(), listError);
        //Validation of the director size
        movieValidation.verifySize(movie.getDirector(), ConstantData.NAME_DIRECTOR_MOVIE, ConstantData.MAX_NAME_DIRECTOR_MOVIE, listError);
        //Validation of name movie
        movieValidation.verifyName(movie.getName(), listError);
        //Validation of genre movie
        movieValidation.verifyGenre(movie.getGenreId(), listIdsGenre, listError);
        //Validation of the actor
        movieValidation.verifyNamesOfActors(movie.getActor(), listError);
        //Validation of the director
        movieValidation.verifyDirector(movie.getDirector(), listError);
        //Validation of year
        movieValidation.verifyYear(movie.getYear(), listError);
        //Validation of oscar nomination
        movieValidation.verifyOscarNomination(movie.getOscarNomination(), listError);
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }
}
