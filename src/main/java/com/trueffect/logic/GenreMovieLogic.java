package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.GenreMovie;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.GenreMovieCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.OperationString;
import com.trueffect.validation.GenreMovieCreate;
import com.trueffect.validation.GenreMovieUpdate;
import com.trueffect.validation.MovieCreate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class GenreMovieLogic {

    private HashMap<String, String> listData;
    private Permission permission;
    private MovieCrud movieCrud;
    private GenreMovieCrud genreMovieCrud;
    private MovieValidationDB movieValidationDB;

    public GenreMovieLogic() {
        String object = ConstantData.ObjectMovie.GenreMovie.getDescription();
        listData = new HashMap<String, String>();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
        genreMovieCrud = new GenreMovieCrud();
        movieValidationDB = new MovieValidationDB();
    }

    public Either createGenreMovie(long idCreateUser, GenreMovie genreMovie, MovieCreate movieCreate) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String create = ConstantData.Crud.create.name();
            String active = ConstantData.Status.Active.name();
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
            GenreMovieCreate genreMovieCreate = new GenreMovieCreate();
            eitherRes = genreMovieCreate.complyCondition(genreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            GenreMovieValidationDB genreMovieValidationDB = new GenreMovieValidationDB();
            eitherRes = genreMovieValidationDB.veriryDataInDataBase(connection, genreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //The name is converted correctly
            String idGenreMovie = genreMovie.getIdGenre().trim().toUpperCase();
            genreMovie.setIdGenre(idGenreMovie);
            String nameGenreMovie = genreMovie.getNameGenre();
            nameGenreMovie = OperationString.generateName(nameGenreMovie);
            nameGenreMovie = OperationString.addApostrophe(nameGenreMovie);
            genreMovie.setNameGenre(nameGenreMovie);
            //Insert Genre Movie          
            eitherRes = genreMovieCrud.insert(connection, genreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = genreMovieCrud.getGenreById(connection, idGenreMovie);
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

    public Either updateGenreMovie(String idGenreMovie, long idModifyUser, GenreMovie genreMovie) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String update = ConstantData.Crud.update.name();
            String statusActive = ConstantData.Status.Active.name();
            String nameObject = ConstantData.ObjectMovie.GenreMovie.getDescription();
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
            eitherRes = operationModel.verifyIdString(idGenreMovie, genreMovie.getIdGenre(), nameObject);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = getGenreMovie(connection, idGenreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            GenreMovieUpdate genreMovieUpdate = new GenreMovieUpdate();
            eitherRes = genreMovieUpdate.complyCondition(genreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            GenreMovieValidationDB genreMovieValidationDB = new GenreMovieValidationDB();
            eitherRes = genreMovieValidationDB.veriryUpdateInDataBase(connection, genreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //The name is converted correctly
            String idGenreMovieNew = genreMovie.getIdGenre().trim().toUpperCase();
            genreMovie.setIdGenre(idGenreMovieNew);
            String nameGenreMovie = genreMovie.getNameGenre();
            nameGenreMovie = OperationString.generateName(nameGenreMovie);
            nameGenreMovie = OperationString.addApostrophe(nameGenreMovie);
            genreMovie.setNameGenre(nameGenreMovie);
            eitherRes = genreMovieCrud.update(connection, genreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            idGenreMovie = idGenreMovie.trim().toUpperCase();
            eitherRes = genreMovieCrud.getGenreById(connection, idGenreMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either exception) {
            eitherRes = exception;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either getGenreMovie(Connection connection, String idGenreMovie) {
        String genreMovieDescription = ConstantData.ObjectMovie.GenreMovie.getDescription();
        Either eitherRes = new Either();
        GenreMovie genreMovie = new GenreMovie();
        ArrayList<String> listError = new ArrayList<String>();
        try {
            String idGenreMovieAux = idGenreMovie.trim().toUpperCase();
            eitherRes = genreMovieCrud.getGenreById(connection, idGenreMovieAux);
            genreMovie = (GenreMovie) eitherRes.getFirstObject();
        } catch (Exception exception) {
            listError.add(exception.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        if (genreMovie.isEmpty()) {
            listData.clear();
            listData.put(ConstantData.OBJECT, genreMovieDescription);
            String errorMgs = OperationString.generateMesage(Message.NOT_FOUND, listData);
            listError.add(errorMgs);
            return new Either(CodeStatus.NOT_FOUND, listError);
        }
        return eitherRes;
    }

}
