package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.Actor;
import com.trueffect.model.Identifier;
import com.trueffect.model.Movie;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.ActorCrud;
import com.trueffect.sql.crud.CopyCrud;
import com.trueffect.sql.crud.GenreMovieCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.sql.crud.ParticipateCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import com.trueffect.validation.MovieCreate;
import com.trueffect.validation.MovieUpdate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class MovieLogic {

    private HashMap<String, String> listData;
    private Permission permission;
    private MovieCrud movieCrud;
    private ActorCrud actorCrud;
    private ParticipateCrud participateCrud;
    private GenreMovieCrud genreMovieCrud;
    private MovieValidationDB movieValidationDB;

    public MovieLogic() {
        String object = ConstantData.ObjectMovie.Movie.getDescription();
        listData = new HashMap<String, String>();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
        actorCrud = new ActorCrud();
        participateCrud = new ParticipateCrud();
        genreMovieCrud = new GenreMovieCrud();
        movieValidationDB = new MovieValidationDB();
    }

    public Either createMovie(int idCreateUser, Movie movie, MovieCreate movieCreate) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String create = ConstantData.Crud.create.name();
            String active = ConstantData.Status.Active.name();
            MovieValidationDB employeeValidationDB = new MovieValidationDB();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idCreateUser, active, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation of permission           
            eitherRes = permission.checkUserPermission(connection, idCreateUser, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = genreMovieCrud.getAllGenre(connection);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> listIdsGenre = eitherRes.getListObject();
            movieCreate.setListIdsGenre(listIdsGenre);
            eitherRes = movieCreate.complyCondition(movie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = employeeValidationDB.veriryDataInDataBase(connection, movie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //The name is converted correctly
            covertedCorrectlyName(movie);
            covertedCorrectlyActor(movie);
            covertedCorrectlyDirector(movie);
            String genreMovie = movie.getGenreId().toUpperCase();
            movie.setGenreId(genreMovie);
            //Insert Movie           
            eitherRes = movieCrud.insertMovie(connection, idCreateUser, movie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            long idMovie = ((Identifier) eitherRes.getFirstObject()).getId();
            // start insert actors
            eitherRes = actorCrud.getAllActors(connection, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> actorsDB = eitherRes.getListObject();
            ArrayList<String> actorMovie = movie.getActor();
            ArrayList<Identifier> identifiersActorsDB = getIdentifierActors(actorMovie, actorsDB);
            //Insert Movie. actorMovie have removed actors what is in data base
            eitherRes = actorCrud.insertActors(connection, idCreateUser, actorMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> idActors = eitherRes.getListObject();
            idActors.addAll(identifiersActorsDB);
            //Insert Participate
            eitherRes = participateCrud.insertActorsAndMovie(connection, idCreateUser, idMovie, idActors);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //end insert actor
            eitherRes = movieCrud.getMovie(connection, idMovie, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Movie movieCreated = (Movie) eitherRes.getFirstObject();
            eitherRes = participateCrud.getIdsActorOf(connection, idMovie, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> identifiersActors = eitherRes.getListObject();
            eitherRes = actorCrud.getActorByIds(connection, identifiersActors, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<String> actors = getActorsOf(eitherRes.getListObject());
            movieCreated.setActor(actors);
            eitherRes = new Either(CodeStatus.CREATED, movieCreated);
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private ArrayList<Identifier> getIdentifierActors(ArrayList<String> actor, ArrayList<ModelObject> actorsDB) {
        ArrayList<Identifier> identifierActors = new ArrayList<Identifier>();
        for (ModelObject modelObject : actorsDB) {
            Actor actorDB = (Actor) modelObject;
            boolean find = false;
            int i = 0;
            while (i < actor.size() && !find) {
                if (actorDB.getName().equals(actor.get(i))) {
                    find = true;
                    Identifier identifierActor = new Identifier(actorDB.getId());
                    identifierActors.add(identifierActor);
                    actor.remove(i);
                }
                i++;
            }
        }
        return identifierActors;
    }

    public Either update(Movie movie, long idMovie, long idModifyUser) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String update = ConstantData.Crud.update.name();
            String statusActive = ConstantData.Status.Active.name();
            String movieName = ConstantData.ObjectMovie.Movie.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idModifyUser, statusActive, update);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation of data
            eitherRes = permission.checkUserPermission(connection, idModifyUser, update);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationModel operationModel = new OperationModel();
            eitherRes = operationModel.verifyId(idMovie, movie.getId(), movieName);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getMovie(connection, idMovie, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //update is a class to specifically validate the conditions to update a movie
            MovieUpdate movieUpdate = new MovieUpdate();
            eitherRes = genreMovieCrud.getAllGenre(connection);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> listIdsGenre = eitherRes.getListObject();
            movieUpdate.setListGenre(listIdsGenre);
            eitherRes = movieUpdate.complyCondition(movie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            if (StringUtils.isNotBlank(movie.getName())) {
                eitherRes = movieValidationDB.veriryDataInDataBase(connection, movie);
                if (eitherRes.existError()) {
                    throw eitherRes;
                }
                covertedCorrectlyName(movie);
            }
            if (StringUtils.isNotBlank(movie.getDirector())) {
                covertedCorrectlyDirector(movie);
            }
            if (!movie.getActor().isEmpty()) {
                covertedCorrectlyActor(movie);
            }
            String genreMovie = movie.getGenreId();
            if (StringUtils.isNotBlank(genreMovie)) {
                movie.setGenreId(genreMovie.toUpperCase());
            }
            eitherRes = movieCrud.updateMovie(connection, idModifyUser, movie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = participateCrud.getIdsActorOf(connection, idMovie, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //obtenemos los actores
            ArrayList<ModelObject> idsActorDB = eitherRes.getListObject();
            eitherRes = actorCrud.getActorByIds(connection, idsActorDB, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> actorsDB = eitherRes.getListObject();
            ArrayList<ModelObject> idsToUpdateStatus = getIdsToUpdateStatus(actorsDB, movie.getActor());
            String statusInactive = ConstantData.Status.Inactive.name();
            eitherRes = participateCrud.updateStatus(connection, idModifyUser, idMovie, idsToUpdateStatus, statusInactive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<String> actorsToInsert = getActorsToInsert(movie.getActor(), actorsDB);
            // start insert actors
            eitherRes = actorCrud.getAllActors(connection, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> allActorsDB = eitherRes.getListObject();
            ArrayList<Identifier> identifiersActorsDB = getIdentifierActors(actorsToInsert, allActorsDB);
            //Insert Movie. actorMovie have removed actors what is in data base
            eitherRes = actorCrud.insertActors(connection, idModifyUser, actorsToInsert);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> idActors = eitherRes.getListObject();
            idActors.addAll(identifiersActorsDB);
            //Insert Participate
            eitherRes = participateCrud.insertActorsAndMovie(connection, idModifyUser, idMovie, idActors);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //end insert actor
            eitherRes = movieCrud.getMovie(connection, idMovie, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Movie movieUpdated = (Movie) eitherRes.getFirstObject();
            eitherRes = participateCrud.getIdsActorOf(connection, idMovie, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> identifiersActors = eitherRes.getListObject();
            eitherRes = actorCrud.getActorByIds(connection, identifiersActors, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<String> actors = getActorsOf(eitherRes.getListObject());
            movieUpdated.setActor(actors);
            eitherRes = new Either(CodeStatus.CREATED, movieUpdated);
            DataBasePostgres.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either getMovie(Connection connection, long idMovie, String status) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        Movie movie = new Movie();
        try {
            Either eitherMovie = movieCrud.getMovie(connection, idMovie, status);
            movie = (Movie) eitherMovie.getFirstObject();
            if (movie.isEmpty()) {
                String object = ConstantData.ObjectMovie.Movie.getDescription();
                listData.clear();
                listData.put(ConstantData.OBJECT, object);
                String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
                listError.add(errorMgs);
                return new Either(CodeStatus.NOT_FOUND, listError);
            }
            eitherRes.setCode(CodeStatus.OK);
            eitherRes.addModeloObjet(movie);
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        return eitherRes;
    }

    public Either updateStatus(long idMovie, long idModifyUser, String status) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String delete = ConstantData.Crud.delete.name();
            String statusActive = ConstantData.Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idModifyUser, statusActive, delete);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idModifyUser, delete);
            if (eitherRes.existError()) {
                //return eitherRes;
                throw eitherRes;
            }
            eitherRes = getMovie(connection, idMovie, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Movie movie = (Movie) eitherRes.getFirstObject();
            //Validation Status(Active, Inactive)
            OperationModel operationModel = new OperationModel();
            eitherRes = operationModel.verifyStatus(connection, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            String nameObject = ConstantData.ObjectMovie.Movie.getDescription();
            eitherRes = operationModel.verifyStatusModelObject(nameObject, movie.getStatus(), status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            status = StringUtils.capitalize(status.trim().toLowerCase());
            eitherRes = movieCrud.updateStatusMovie(connection, idModifyUser, idMovie, status);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = movieCrud.getMovie(connection, idMovie, null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            Movie movieUpdatedStatus = (Movie) eitherRes.getFirstObject();
            eitherRes = participateCrud.getIdsActorOf(connection, idMovie, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> identifiersActors = eitherRes.getListObject();

            eitherRes = actorCrud.getActorByIds(connection, identifiersActors, statusActive);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<String> actors = getActorsOf(eitherRes.getListObject());
            movieUpdatedStatus.setActor(actors);
            eitherRes = new Either(CodeStatus.CREATED, movieUpdatedStatus);
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private ArrayList<String> getActorsOf(ArrayList<ModelObject> listObject) {
        ArrayList<String> resActors = new ArrayList<String>();
        for (ModelObject modelObject : listObject) {
            Actor actor = (Actor) modelObject;
            resActors.add(actor.getName());
        }
        return resActors;
    }

    public Either insertCopy(long idMovie, long idCreateUser, int amountCopy) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String create = ConstantData.Crud.create.name();
            String active = ConstantData.Status.Active.name();
            MovieValidationDB employeeValidationDB = new MovieValidationDB();
            //checks if the employee exists
            permission.setNameObject(ConstantData.ObjectMovie.Copy.name());
            eitherRes = permission.getPerson(connection, idCreateUser, active, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation of permission           
            eitherRes = permission.checkUserPermission(connection, idCreateUser, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getMovie(connection, idMovie, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = employeeValidationDB.veriryCopyDuplicate(connection, idMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Insert Copy
            CopyCrud copyCrud = new CopyCrud();
            eitherRes = copyCrud.insertCopy(connection, idCreateUser, idMovie, amountCopy);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            long idCopyMovie = ((Identifier) eitherRes.getFirstObject()).getId();
            eitherRes = copyCrud.getReportCopyMovie(connection, idCopyMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private void covertedCorrectlyName(Movie movie) {
        String nameMovie = OperationString.removeSpace(movie.getName());
        nameMovie = OperationString.addApostrophe(nameMovie);
        movie.setName(nameMovie);
    }

    private void covertedCorrectlyActor(Movie movie) {
        ArrayList<String> nameActors = movie.getActor();
        ArrayList<String> nameActorsCorrectly = new ArrayList<String>();
        for (String nameActor : nameActors) {
            String actor = OperationString.removeSpace(nameActor);
            actor = OperationString.addApostrophe(actor);
            nameActorsCorrectly.add(actor);
        }
        movie.setActor(nameActorsCorrectly);
    }

    private void covertedCorrectlyDirector(Movie movie) {
        String nameDirector = OperationString.removeSpace(movie.getDirector());
        nameDirector = OperationString.addApostrophe(nameDirector);
        movie.setDirector(nameDirector);
    }

    private ArrayList<ModelObject> getIdsToUpdateStatus(ArrayList<ModelObject> actorsDB, ArrayList<String> actors) {
        ArrayList<ModelObject> idsActorUpdate = new ArrayList<ModelObject>();
        for (ModelObject actorModelObject : actorsDB) {
            Actor actorDB = (Actor) actorModelObject;
            boolean findActorNew = false;
            int i = 0;
            while (i < actors.size() && !findActorNew) {
                if (actorDB.getName().equals(actors.get(i))) {
                    findActorNew = true;
                }
                i++;
            }
            if (!findActorNew) {
                idsActorUpdate.add(new Identifier(actorDB.getId()));
            }
        }
        return idsActorUpdate;
    }

    private ArrayList<String> getActorsToInsert(ArrayList<String> actors, ArrayList<ModelObject> actorsDB) {
        ArrayList<String> actorsInsert = new ArrayList<String>();
        for (String actorName : actors) {
            boolean findActorDB = false;
            int i = 0;
            while (i < actorsDB.size() && !findActorDB) {
                Actor actorDB = (Actor) actorsDB.get(i);
                if (actorName.equals(actorDB.getName())) {
                    findActorDB = true;
                }
                i++;
            }
            if (!findActorDB) {
                actorsInsert.add(actorName);
            }
        }
        return actorsInsert;
    }
}
