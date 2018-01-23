package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.ActorCrud;
import com.trueffect.sql.crud.GenreMovieCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.sql.crud.ParticipateCrud;
import com.trueffect.tools.ConstantData;
import java.sql.Connection;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class MovieReportLogic {

    private HashMap<String, String> listData;
    private Permission permission;
    private MovieCrud movieCrud;

    public MovieReportLogic() {
        String object = ConstantData.ObjectMovie.MovieReport.getDescription();
        listData = new HashMap<String, String>();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
    }

    public Either getInformationOfMovies(long idUserSearch) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = ConstantData.Crud.get.name();
            String status = ConstantData.Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserSearch, status, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            eitherRes = employeeCrud.getEmployeeBy(connection, typeId, identifier, lastName, firstName, genre, dateOfHire, jobName);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            OperationModel operationModel = new OperationModel();
            DataBasePostgres.connectionCommit(connection);

        } catch (Either e) {
            eitherRes = e;
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }
}
