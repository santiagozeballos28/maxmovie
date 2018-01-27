package com.trueffect.logic;

import com.trueffect.messages.Message;
import com.trueffect.model.CopyMovie;
import com.trueffect.model.Movie;
import com.trueffect.model.ReportMovie;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.sql.crud.CopyCrud;
import com.trueffect.sql.crud.MovieCrud;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.OperationSale;
import com.trueffect.tools.ConstantData.Status;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class SaleValidationDB {

    protected HashMap<String, String> listData;
    private CopyCrud copyCrud;
    private MovieCrud movieCrud;

    public SaleValidationDB() {
        this.listData = new HashMap<String, String>();
        this.copyCrud = new CopyCrud();
        this.movieCrud = new MovieCrud();
    }

    public Either verifyCopiesMovie(Connection connection, ArrayList<Sale> sales) {

        ArrayList<String> listError = new ArrayList<String>();
        ArrayList<Long> idsMovieSale = getIdsMovie(sales);
        Either either = new Either();
        try {
            either = copyCrud.getAmountCurrent(connection, idsMovieSale);
        } catch (Exception e) {
            listError.add(e.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        //List of movies with current copies
        ArrayList<ModelObject> copyMovies = either.getListObject();
        try {
            String active = Status.Active.name();
            either = movieCrud.getMovie(connection, idsMovieSale, active);
        } catch (Exception e) {
            listError.add(e.getMessage());
            return new Either(CodeStatus.INTERNAL_SERVER_ERROR, listError);
        }
        //List of movies requested to sale
        ArrayList<ModelObject> movies = either.getListObject();
        String errorMgs = "";
        ArrayList<ModelObject> reportMovies = joinMoviesWithQuantity(movies, copyMovies);
        for (Sale sale : sales) {
            int posReportMovie = getPosReportMovie(sale.getIdMovie(), reportMovies);
            ReportMovie reportMovie = (ReportMovie) reportMovies.get(posReportMovie);
            if (sale.getAmount() > reportMovie.getQuantityCurrent()) {
                String operationSale = OperationSale.valueOf(sale.getOperation()).getDescription();
                listData.clear();
                listData.put(ConstantData.DATA, sale.getAmount() - reportMovie.getQuantityCurrent() + "");
                listData.put(ConstantData.NAME_OBJECT, reportMovie.getNameMovie());
                listData.put(ConstantData.OPERATION, operationSale);
                errorMgs = OperationString.generateMesage(Message.INSUFFICIENT_AMOUNT, listData);
                listError.add(errorMgs);
            }
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

    private ArrayList<ModelObject> joinMoviesWithQuantity(ArrayList<ModelObject> movies, ArrayList<ModelObject> copyMovies) {
        ArrayList<ModelObject> reportMovie = new ArrayList<ModelObject>();
        for (ModelObject modelObject : movies) {
            Movie movie = (Movie) modelObject;
            int quantityCurrent = getQuantity(movie.getId(), copyMovies);
            reportMovie.add(new ReportMovie(movie.getId(), movie.getName(), quantityCurrent));
        }
        return reportMovie;
    }

    private int getQuantity(long idMovie, ArrayList<ModelObject> copyMovies) {
        int quantityCurrent = -1;
        boolean findCopy = false;
        int i = 0;
        while (i < copyMovies.size() && !findCopy) {
            CopyMovie copyMovie = (CopyMovie) copyMovies.get(i);
            if (idMovie == copyMovie.getMovieId()) {
                findCopy = true;
                quantityCurrent = copyMovie.getAmountCurrent();
            }
            i++;
        }
        return quantityCurrent;
    }

    private int getPosReportMovie(long idMovie, ArrayList<ModelObject> reportMovies) {
        int posReportMovie = -1;
        boolean findReportMovie = false;
        int i = 0;
        while (i < reportMovies.size() && !findReportMovie) {
            ReportMovie reportMovie = (ReportMovie) reportMovies.get(i);
            if (idMovie == reportMovie.getIdMovie()) {
                findReportMovie = true;
                posReportMovie = i;
            }
            i++;
        }
        return posReportMovie;
    }

    Either verifyRentQuantity(Connection connection, long idMasterDetail, ArrayList<Sale> rentReturnSales) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
