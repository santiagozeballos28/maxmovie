/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.Identifier;
import com.trueffect.model.Movie;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import com.trueffect.validation.SaleCreate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class SaleLogic {

    private HashMap<String, String> listData;
    private Permission permission;
    private MovieCrud movieCrud;

    public SaleLogic() {
        String object = ConstantData.ObjectMovie.Sale.name();
        listData = new HashMap<String, String>();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
    }

    public Either registerSale(long idCreateUser, int idRenterUser, ArrayList<Sale> sales) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String create = ConstantData.Crud.create.name();
            String active = ConstantData.Status.Active.name();
            MovieValidationDB employeeValidationDB = new MovieValidationDB();
            //checks if the employee exists
            permission.setNameObject(ConstantData.ObjectMovie.Sale.name());
            eitherRes = permission.getEmployee(connection, idCreateUser, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = permission.getRenterUser(connection, idRenterUser, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation of permission           
            eitherRes = permission.checkUserPermissionCustomerCare(connection, idCreateUser, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }

            eitherRes = checkMovies(connection, sales);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<Long> idsMovie = getIdsMovie(sales);

            //Get movies           
            eitherRes = movieCrud.getMovie(connection, idsMovie, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> movies = eitherRes.getListObject();
            SaleCreate saleCreate = new SaleCreate(movies, sales);
            eitherRes =saleCreate.complyCondition();
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            
            
//            eitherRes = getMovie(connection, idMovie, active);
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
//
//            eitherRes = getMovie(connection, idMovie, active);
//            if (eitherRes.existError()) {
//                throw eitherRes;
//            }
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
            DataBasePostgres.connectionRollback(connection, eitherRes);
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private Either checkMovies(Connection connection, ArrayList<Sale> sales) {
        Either eitherRes = new Either();
        ArrayList<String> listError = new ArrayList<String>();
        String object = ConstantData.ObjectMovie.Movie.name();
        int amountIdsEmptys = 0;
        for (Sale sale : sales) {
            long idMovie = sale.getIdMovie();
            if (idMovie == 0) {
                amountIdsEmptys++;
            } else {
                String active = ConstantData.Status.Active.name();
                Either eitherMovie = movieCrud.getMovie(connection, idMovie, active);
                Movie movie = (Movie) eitherMovie.getFirstObject();
                if (movie.isEmpty()) {
                    listData.clear();
                    listData.put(ConstantData.OBJECT, object);
                    listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
                    listData.put(ConstantData.DATA, idMovie + "");
                    String errorMgs = OperationString.generateMesage(Message.NOT_FOUND_WITH_ID, listData);
                    listError.add(errorMgs);

                }
            }
        }
        if (amountIdsEmptys > 0) {
            listData.clear();
            listData.put(ConstantData.DATA, amountIdsEmptys + "");
            listData.put(ConstantData.TYPE_DATA, ConstantData.IDENTIFIER);
            listData.put(ConstantData.OBJECT, object);
            String errorMgs = OperationString.generateMesage(Message.AOMUNT_EMPTY_IDS, listData);
            listError.add(errorMgs);

        }
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private ArrayList<Long> getIdsMovie(ArrayList<Sale> sales) {
        ArrayList<Long> idsMovie = new ArrayList<Long>();
        for (Sale sale : sales) {
            idsMovie.add(sale.getIdMovie());
        }
        return idsMovie;
    }
}
