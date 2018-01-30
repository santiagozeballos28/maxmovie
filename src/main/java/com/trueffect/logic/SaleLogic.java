package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.messages.Message;
import com.trueffect.model.BuyDetail;
import com.trueffect.model.CopyMovie;
import com.trueffect.model.DataJob;
import com.trueffect.model.Identifier;
import com.trueffect.model.MasterDetail;
import com.trueffect.model.MasterDetailSaile;
import com.trueffect.model.Movie;
import com.trueffect.model.Price;
import com.trueffect.model.RentReturn;
import com.trueffect.model.RentalDetail;
import com.trueffect.model.Sale;
import com.trueffect.model.SaleDetail;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.BuyDetailCrud;
import com.trueffect.sql.crud.CopyCrud;
import com.trueffect.sql.crud.EmployeeCrud;
import com.trueffect.sql.crud.MasterDetailCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.sql.crud.PriceCrud;
import com.trueffect.sql.crud.RentalDetailCrud;
import com.trueffect.sql.crud.ReportSaleCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.OperationSale;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import com.trueffect.validation.RentReturnCreate;
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
    private ArrayList<ModelObject> movies;
    private SaleValidationDB saleValidationDB;
    private Permission permission;
    private MovieCrud movieCrud;
    private CopyCrud copyCrud;
    private PriceCrud priceCrud;
    private MasterDetailCrud masterDetailCrud;
    private RentalDetailCrud rentalDetailCrud;
    private BuyDetailCrud buyDetailCrud;
    private ReportSaleCrud reportSaleCrud;

    public SaleLogic() {
        String object = ConstantData.ObjectMovie.Sale.name();
        listData = new HashMap<String, String>();
        prices = new ArrayList<ModelObject>();
        movies = new ArrayList<ModelObject>();
        saleValidationDB = new SaleValidationDB();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
        copyCrud = new CopyCrud();
        priceCrud = new PriceCrud();
        masterDetailCrud = new MasterDetailCrud();
        rentalDetailCrud = new RentalDetailCrud();
        buyDetailCrud = new BuyDetailCrud();
        reportSaleCrud = new ReportSaleCrud();
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
            ArrayList<Long> idsMovieSale = getIdsMovie(sales);
            //Get movies           
            eitherRes = movieCrud.getMovie(connection, idsMovieSale, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            SaleCreate saleCreate = new SaleCreate(movies, sales);
            eitherRes = saleCreate.complyCondition();
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = saleValidationDB.verifyCopiesMovie(connection, sales);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = copyCrud.getCopyMovie(connection, idsMovieSale);
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
            ArrayList<SaleDetail> rentalDetails = new ArrayList<SaleDetail>();
            ArrayList<SaleDetail> buyDetails = new ArrayList<SaleDetail>();
            splitSales(sales, copiesMovie, rentalDetails, buyDetails);
            int amountTotal = getSubTotalAmount(rentalDetails) + getSubTotalAmount(buyDetails);
            double priceTotal = getSubTotalPrice(rentalDetails) + getSubTotalPrice(buyDetails);
            EmployeeCrud employeeCrud = new EmployeeCrud();
            eitherRes = employeeCrud.getDataJob(connection, idCreateUser, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            long idDataJob = ((DataJob) eitherRes.getFirstObject()).getJobId();
            MasterDetail masterDetail = new MasterDetail(amountTotal, priceTotal, idDataJob, idRenterUser);
            eitherRes = masterDetailCrud.insert(connection, masterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Insert sale in the table master_detail
            eitherRes = masterDetailCrud.insert(connection, masterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            long idMasterDetail = ((Identifier) eitherRes.getFirstObject()).getId();
            //Insert sale in the table rental_detail
            eitherRes = rentalDetailCrud.insert(connection, idMasterDetail, rentalDetails);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Insert sale in the table buy_detail
            eitherRes = buyDetailCrud.insert(connection, idMasterDetail, buyDetails);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Update copy current of all the movies rentals and buys.
            eitherRes = copyCrud.updateAmountCurrent(connection, idCreateUser, copiesMovie);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Get data inserted
            eitherRes = masterDetailCrud.getDetailSaleOf(connection, idMasterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            MasterDetailSaile masterDetailPerson = (MasterDetailSaile) eitherRes.getFirstObject();
            //Get amount subtotal and price subtotal of retal
            eitherRes = reportSaleCrud.getReportRental(connection, idMasterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> reportRental = eitherRes.getListObject();
            eitherRes = reportSaleCrud.getReportBuy(connection, idMasterDetail);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> reportBuy = eitherRes.getListObject();
            eitherRes = new Either();
            eitherRes.addModeloObjet(masterDetailPerson);
            eitherRes.addListObject(reportRental);
            eitherRes.addListObject(reportBuy);
            eitherRes.setCode(CodeStatus.OK);
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
            ArrayList<SaleDetail> rentalDetails,
            ArrayList<SaleDetail> buyDetails) {
        String operationRental = OperationSale.R.name();
        for (Sale sale : sales) {
            boolean terminate = false;
            while (!terminate) {
                int posCopy = getPosCopyMovieOf(sale.getIdMovie(), copiesMovie);
                CopyMovie copyMovie = (CopyMovie) copiesMovie.remove(posCopy);
                int copyCurrent = copyMovie.getAmountCurrent() - sale.getAmount();
                if (copyCurrent >= 0) {
                    terminate = true;
                    if (sale.getOperation().equals(operationRental)) {
                        addSaleDetail(rentalDetails, copyMovie, sale.getOperation(), sale.getAmount());
                    } else {
                        addSaleDetail(buyDetails, copyMovie, sale.getOperation(), sale.getAmount());
                    }
                    copyMovie.setAmountCurrent(copyCurrent);
                    copiesMovie.add(posCopy, copyMovie);//update copy
                } else {
                    if (sale.getOperation().equals(operationRental)) {
                        addSaleDetail(rentalDetails, copyMovie, sale.getOperation(), sale.getAmount());
                    } else {
                        addSaleDetail(buyDetails, copyMovie, sale.getOperation(), sale.getAmount());
                    }
                    int amountRemaining = sale.getAmount() - copyMovie.getAmountCurrent();
                    sale.setAmount(amountRemaining);
                    copyMovie.setAmountCurrent(0);//means that from that copy all the movie are sold out
                    copiesMovie.add(posCopy, copyMovie);
                }
            }
        }
    }

    /*
    *Method that returns the position of the copy
     */
    private int getPosCopyMovieOf(long idMovie, ArrayList<ModelObject> copiesMovie) {
        int posCopy = -1;
        int i = 0;
        boolean findCopy = false;
        while (i < copiesMovie.size() && !findCopy) {
            CopyMovie copyMovie = (CopyMovie) copiesMovie.get(i);
            if (idMovie == copyMovie.getMovieId() && copyMovie.getAmountCurrent() > 0) {
                findCopy = true;
                posCopy = i;
            }
            i++;
        }
        return posCopy;
    }

    private void addSaleDetail(ArrayList<SaleDetail> saleDetails, CopyMovie copyMovie, String operation, int amount) {
        String operationRental = OperationSale.R.name();
        String operationBuy = OperationSale.B.name();
        String operationBuyPremier = OperationSale.BP.name();
        double priceRental = price(operationRental);
        double priceBuy = price(operationBuy);
        double priceBuyPremier = price(operationBuyPremier);
        if (operation.equals(operationRental)) {
            double priceRentalSubTotal = amount * priceRental;
            saleDetails.add(new RentalDetail(copyMovie.getCopyMovieId(), amount, priceRentalSubTotal, operationRental));
        } else {
            String dateCurrent = DateOperation.getDateCurrent();
            double priceBuySubTotal = 0.0;
            String createMovieDate = getCreateMovieDate(copyMovie.getMovieId());
            if (DateOperation.areSameMonthAndYear(dateCurrent, createMovieDate)) {
                priceBuySubTotal = amount * priceBuyPremier;
                saleDetails.add(new BuyDetail(copyMovie.getCopyMovieId(), amount, priceBuySubTotal, operationBuyPremier));
            } else {
                priceBuySubTotal = amount * priceBuy;
                saleDetails.add(new BuyDetail(copyMovie.getCopyMovieId(), amount, priceBuySubTotal, operationBuy));
            }
        }
    }

    private double price(String priceId) {
        double priceRes = 0.0;
        boolean find = false;
        int i = 0;
        while (i < prices.size() && !find) {
            Price price = (Price) prices.get(i);
            if (price.getId().equals(priceId)) {
                find = true;
                priceRes = price.getPrice();
            }
            i++;
        }
        return priceRes;
    }

    private int getSubTotalAmount(ArrayList<SaleDetail> saleDetails) {
        int amount = 0;
        for (SaleDetail saleDetail : saleDetails) {
            amount = amount + saleDetail.getAmount();
        }
        return amount;
    }

    private double getSubTotalPrice(ArrayList<SaleDetail> saleDetails) {
        double price = 0;
        for (SaleDetail saleDetail : saleDetails) {
            price = price + saleDetail.getPrice();
        }
        return price;
    }

    private String getCreateMovieDate(long movieId) {
        String createMovieDate = "";
        boolean findMovie = false;
        int i = 0;
        while (i < movies.size() && !findMovie) {
            Movie movie = (Movie) movies.get(i);
            if (movie.getId() == movieId) {
                findMovie = true;
                createMovieDate = movie.getCreateDate();
            }
            i++;
        }
        return createMovieDate;
    }

    public Either registerRentReturn(long idEmployee, long idRenterUser, long idMasterDetail, ArrayList<RentReturn> rentReturns) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String create = ConstantData.Crud.create.name();
            String update = ConstantData.Crud.update.name();
            String active = ConstantData.Status.Active.name();

            MovieValidationDB employeeValidationDB = new MovieValidationDB();
            //checks if the employee exists
            permission.setNameObject(ConstantData.ObjectMovie.Sale.name());
            eitherRes = permission.getEmployee(connection, idEmployee, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            eitherRes = permission.getRenterUser(connection, idRenterUser, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Validation of permission           
            eitherRes = permission.checkUserPermissionCustomerCare(connection, idEmployee, create);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<Sale> rentReturnSales = new ArrayList<Sale>();
            rentReturnSales.addAll(rentReturns);
            eitherRes = checkMovies(connection, rentReturnSales);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<Long> idsMovieSale = getIdsMovie(rentReturnSales);
            //Get movies           
            eitherRes = movieCrud.getMovie(connection, idsMovieSale, active);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            RentReturnCreate rentReturnCreate = new RentReturnCreate(movies, rentReturnSales);
            eitherRes = rentReturnCreate.complyCondition();
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //To verify that the return amount is coherent with the rented
            eitherRes = saleValidationDB.verifyRentQuantity(connection, idMasterDetail, rentReturnSales);
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

    private Either verifyPenalty(Connection connection, ArrayList<RentalDetail> rentalDetails, String dateRental) {
        String dateCurrent = DateOperation.getTimertampCurrent();
        int diferenceDays = DateOperation.diferenceDays(dateRental, dateCurrent);
        if (diferenceDays > ConstantData.MAX_DAYS) {
            return addPenalty(connection, rentalDetails, diferenceDays);
        }
        return new Either();
    }

    private Either addPenalty(Connection connection, ArrayList<RentalDetail> rentalDetails, int daysPenalty) {
        String idPricePenalty = OperationSale.PEN.name();
        Either eitherPrice = new Either();
        try {
            eitherPrice = priceCrud.getPriceOf(connection, idPricePenalty);
        } catch (Exception e) {
            ArrayList<String> listError = new ArrayList<String>();
            listError.add(e.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        Price price = (Price) eitherPrice.getFirstObject();
        double pricePenalty = price.getPrice();
        for (int i = 0; i < rentalDetails.size(); i++) {
            RentalDetail rentalDetail = rentalDetails.remove(i);
            double rentalPriceCurrent = rentalDetail.getPriceOficial();
            double penaltySubTotal = rentalDetail.getAmountOficial() * pricePenalty;
            rentalDetail.setPriceOficial(rentalPriceCurrent + pricePenalty);
            rentalDetails.add(i, rentalDetail);
        }
        return new Either();
    }
}
