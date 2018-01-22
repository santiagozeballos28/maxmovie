/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.BuyDetail;
import com.trueffect.model.CopyMovie;
import com.trueffect.model.Identifier;
import com.trueffect.model.Movie;
import com.trueffect.model.Price;
import com.trueffect.model.RentalDetail;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.CopyCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.sql.crud.PriceCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.OperationSale;
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
    private ArrayList<ModelObject> prices;
    private Permission permission;
    private MovieCrud movieCrud;
    private CopyCrud copyCrud;
    private PriceCrud priceCrud;

    public SaleLogic() {
        String object = ConstantData.ObjectMovie.Sale.name();
        listData = new HashMap<String, String>();
        prices = new ArrayList<ModelObject>();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
        copyCrud = new CopyCrud();
        priceCrud = new PriceCrud();
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
            eitherRes = saleCreate.complyCondition();
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = copyCrud.getCopyMovie(connection, idsMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> copiesMovie = eitherRes.getListObject();
            eitherRes = priceCrud.getPrice(connection);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            prices = eitherRes.getListObject();
            //splits sales in rental detail and buy detail
            ArrayList<ModelObject> rentalDetails = new ArrayList<ModelObject>();
            ArrayList<ModelObject> buyDetails = new ArrayList<ModelObject>();
            splitSales(sales, copiesMovie, rentalDetails, buyDetails);

            ArrayList<ModelObject> rentalDetailsAll = assignCopyAndPriceRental(copiesMovie, rentalDetails);
            ArrayList<ModelObject> buyDetailsAll = assignCopyAndPriceBuy(copiesMovie, buyDetails);
            // eitherRes = getCopies(connection, sales);
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

    private void splitSales(
            ArrayList<Sale> sales,
            ArrayList<ModelObject> copiesMovie,
            ArrayList<ModelObject> rentalDetails,
            ArrayList<ModelObject> buyDetails) {

//        double priceRental = price(operationRental, false);
//        double priceBuy = price(operationBuy, false);
//        double priceBuyPremier = price(operationBuy, true);
        for (Sale sale : sales) {

            boolean terminate = false;
            while (!terminate) {
                int posCopy = getCopyMovieOf(sale.getIdMovie(), copiesMovie);
                CopyMovie copyMovie = (CopyMovie) copiesMovie.remove(posCopy);
                int copyCurrent = copyMovie.getAmountCurrent() - sale.getAomunt();
                if (copyCurrent >= 0) {
                    terminate = true;
                    addSaleDetail(rentalDetails, buyDetails, sale, copyMovie);
                    copyMovie.setAmountCurrent(copyCurrent);
                    copiesMovie.add(posCopy, copyMovie);//update copy
                } else {
                    rentalDetails.add(new RentalDetail(copyMovie.getMovieId(), copyMovie.getAmountCurrent()));
                    copyMovie.setAmountCurrent(0);//means that from that copy all the movie are sold out
                    copiesMovie.add(posCopy, copyMovie);
                }

            }

        }
    }

    private double price(String priceId, boolean premier) {
        double priceRes = 0.0;

        boolean find = false;
        int i = 0;
        while (i < prices.size() && !find) {
            Price price = (Price) prices.get(i);
            if (price.getId().equals(priceId)) {
                find = true;
                priceRes = price.getPriceNormal();
                if (premier) {
                    priceRes = price.getPricePremier();
                }
            }
            i++;
        }
        return priceRes;
    }

    private ArrayList<ModelObject> assignCopyAndPriceRental(ArrayList<ModelObject> copiesMovie, ArrayList<ModelObject> rentalDetails) {
        ArrayList<ModelObject> resRentalDetail = new ArrayList<ModelObject>();
        for (ModelObject modelObject : rentalDetails) {
            RentalDetail rentalDetail = (RentalDetail) modelObject;
            boolean terminate = false;
            while (!terminate) {
                CopyMovie copyMovie = getCopyMovieOf(1, copiesMovie);
                int copyCurrent = catn - copu
                     
            }

        }
    }

    private ArrayList<ModelObject> assignCopyAndPriceBuy(ArrayList<ModelObject> copiesMovie, ArrayList<ModelObject> buyDetails) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int getCopyMovieOf(long idMovie, ArrayList<ModelObject> copiesMovie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addSaleDetail(
            ArrayList<ModelObject> rentalDetails,
            ArrayList<ModelObject> buyDetails,
            CopyMovie copyMovie,
            String operation,
            int amount) {
        String operationRental = OperationSale.R.name();
        String operationBuy = OperationSale.B.name();
        double priceRental = price(operationRental, false);
        double priceBuy = price(operationBuy, false);
        double priceBuyPremier = price(operationBuy, true);

        if (operation.equals(operationRental)) {
            double priceRentalSubTotal = amount*priceRental;
            //rentalDetails.add(new RentalDetail(copyMovie.getCopyMovieId(), amount,priceRentalSubTotal));
        } else {
            String dateCurrent = DateOperation.getDataCurrent();
            if (DateOperation.areSameMonthAndYear(dateCurrent, copyMovie.getCreateDate())) {
                buyDetails.add(new BuyDetail(copyMovie.getMovieId(),amount);
            } else {
                buyDetails.add(new BuyDetail(copyMovie.getMovieId(), amount));
            }
        }
    }
}
