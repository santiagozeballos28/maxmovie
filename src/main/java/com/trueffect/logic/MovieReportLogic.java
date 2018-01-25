package com.trueffect.logic;

import com.trueffect.conection.db.DataBasePostgres;
import com.trueffect.model.AmountSaleMovie;
import com.trueffect.model.CopyMovie;
import com.trueffect.model.Movie;
import com.trueffect.model.ReportMovie;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.BuyDetailCrud;
import com.trueffect.sql.crud.CopyCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.sql.crud.RentalDetailCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.util.ModelObject;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class MovieReportLogic {

    private HashMap<String, String> listData;
    private Permission permission;
    private MovieCrud movieCrud;
    private RentalDetailCrud rentalDetailCrud;
    private BuyDetailCrud buyDetailCrud;
    private CopyCrud copyCrud;

    public MovieReportLogic() {
        String object = ConstantData.ObjectMovie.MovieReport.getDescription();
        listData = new HashMap<String, String>();
        permission = new Permission();
        permission.setNameObject(object);
        movieCrud = new MovieCrud();
        rentalDetailCrud = new RentalDetailCrud();
        buyDetailCrud = new BuyDetailCrud();
        copyCrud = new CopyCrud();
    }

    public Either getInformationOfMovies(long idUserSearch) {
        Either eitherRes = new Either();
        Connection connection = null;
        try {
            //open conection 
            connection = DataBasePostgres.getConection();
            String get = ConstantData.Crud.get.name();
            String active = ConstantData.Status.Active.name();
            //checks if the employee exists
            eitherRes = permission.getPerson(connection, idUserSearch, active, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Check if the user can modify
            eitherRes = permission.checkUserPermission(connection, idUserSearch, get);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            //Get all movies Actived
            eitherRes = movieCrud.getMovie(connection,null);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> allMovies = eitherRes.getListObject();
            //Available amount of each movie
            eitherRes = copyCrud.getAmountCurrentAll(connection);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> amountCurrentAvailable = eitherRes.getListObject();
            //Amount of each movie rented 
            eitherRes = rentalDetailCrud.getReportReantalAmount(connection);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> amountSaleRental = eitherRes.getListObject();
            //Amount of each movie buyed 
            eitherRes = buyDetailCrud.getReportBuyAmount(connection);
            if (eitherRes.existError()) {
                throw eitherRes;
            }
            ArrayList<ModelObject> amountSaleBuy = eitherRes.getListObject();
            // Create Report MOvie
            ArrayList<ModelObject> reportMovies = generateReportMovie(allMovies);
            addAmountAvailableCopy(reportMovies, amountCurrentAvailable);
            addAmountSaleRental(reportMovies, amountSaleRental);
            addAmountSaleBuy(reportMovies, amountSaleBuy);
            eitherRes = new Either();
            eitherRes.setListObject(reportMovies);
            eitherRes.setCode(CodeStatus.OK);
            DataBasePostgres.connectionCommit(connection);
        } catch (Either e) {
            eitherRes = e;
        } finally {
            DataBasePostgres.connectionClose(connection, eitherRes);
        }
        return eitherRes;
    }

    private ArrayList<ModelObject> generateReportMovie(ArrayList<ModelObject> allMovies) {

        ArrayList<ModelObject> reportMovies = new ArrayList<ModelObject>();
        for (ModelObject movieObject : allMovies) {
            Movie movie = (Movie) movieObject;
            reportMovies.add(new ReportMovie(movie.getId(), movie.getName(),movie.getStatus()));
        }
        return reportMovies;
    }

    private void addAmountAvailableCopy(ArrayList<ModelObject> reportMovies, ArrayList<ModelObject> amountCurrentAvailable) {
        for (ModelObject modelObject : amountCurrentAvailable) {
            CopyMovie copyMovie = (CopyMovie) modelObject;
            int posMovie = findMovie(copyMovie.getMovieId(), reportMovies);
            ReportMovie reportMovie = (ReportMovie) reportMovies.remove(posMovie);
            reportMovie.setQuantityCurrent(copyMovie.getAmountCurrent());
            reportMovies.add(posMovie, reportMovie);
        }
    }

    private void addAmountSaleRental(ArrayList<ModelObject> reportMovies, ArrayList<ModelObject> amountSaleRental) {
        for (ModelObject modelObject : amountSaleRental) {
            AmountSaleMovie amountSaleMovie = (AmountSaleMovie) modelObject;
            int posMovie = findMovie(amountSaleMovie.getIdMovie(), reportMovies);
            ReportMovie reportMovie = (ReportMovie) reportMovies.remove(posMovie);
            reportMovie.setQuantityRental(amountSaleMovie.getAmount());
            reportMovies.add(posMovie, reportMovie);
        }
    }

    private void addAmountSaleBuy(ArrayList<ModelObject> reportMovies, ArrayList<ModelObject> amountSaleBuy) {
        for (ModelObject modelObject : amountSaleBuy) {
            AmountSaleMovie amountSaleMovie = (AmountSaleMovie) modelObject;
            int posMovie = findMovie(amountSaleMovie.getIdMovie(), reportMovies);
            ReportMovie reportMovie = (ReportMovie) reportMovies.remove(posMovie);
            reportMovie.setQuantityBuy(amountSaleMovie.getAmount());
            reportMovies.add(posMovie, reportMovie);
        }
    }

    private int findMovie(long idMovie, ArrayList<ModelObject> reportMovies) {
        int posMovie = -1;
        int i = 0;
        boolean findMovie = false;
        while (i < reportMovies.size() && !findMovie) {
            ReportMovie reportMovie = (ReportMovie) reportMovies.get(i);
            if (reportMovie.getIdMovie() == idMovie) {
                findMovie = true;
                posMovie = i;
            }
            i++;
        }
        return posMovie;
    }
}
