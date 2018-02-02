package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Movie;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.ObjectMovie;
import com.trueffect.tools.ConstantData.OperationSale;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class SaleCreate {

    private ArrayList<ModelObject> movies;
    private ArrayList<Sale> sales;
    protected HashMap<String, String> listData;

    public SaleCreate() {
        listData = new HashMap<String, String>();
        movies = new ArrayList<ModelObject>();
        sales = new ArrayList<Sale>();
    }

    public SaleCreate(ArrayList<ModelObject> movies, ArrayList<Sale> sales) {
        listData = new HashMap<String, String>();
        this.movies = movies;
        this.sales = sales;
    }

    public Either complyCondition() {
        Either eitherRes = verifyEmpty();
        if (eitherRes.existError()) {
            return eitherRes;
        }
        return verifyData();
    }

    private Either verifyEmpty() {
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        for (Sale sale : sales) {
            String nameMovie = getNameMovie(sale.getIdMovie());
            //Validation empty amount movie
            if (sale.getAmount() == 0) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.AMOUNT_MOVIES);
                listData.put(ConstantData.NAME_OBJECT, nameMovie);
                listData.put(ConstantData.OBJECT, ObjectMovie.Movie.name());
                errorMessages = OperationString.generateMesage(Message.EMPTY_DATA_SALE, listData);
                listError.add(errorMessages);
            }
            //Validation empty operation sale
            if (StringUtils.isBlank(sale.getOperation())) {
                listData.clear();
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.OPERATION_SALE);
                listData.put(ConstantData.NAME_OBJECT, nameMovie);
                listData.put(ConstantData.OBJECT, ObjectMovie.Movie.name());
                errorMessages = OperationString.generateMesage(Message.EMPTY_DATA_SALE, listData);
                listError.add(errorMessages);
            }
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private Either verifyData() {
        ArrayList<String> listError = new ArrayList<String>();
        String errorMessages = "";
        for (Sale sale : sales) {
            String nameMovie = getNameMovie(sale.getIdMovie());
            //Validation of amount the sale
            if (sale.getAmount() < 0) {
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.AMOUNT_MOVIES);
                listData.put(ConstantData.DATA, sale.getAmount() + "");
                listData.put(ConstantData.NAME_OBJECT, nameMovie);
                listData.put(ConstantData.OBJECT, ObjectMovie.Movie.name());
                listData.put(ConstantData.VALID, ConstantData.POSITIVE_NUMBERS);
                errorMessages = OperationString.generateMesage(Message.NOT_VALID_SALE_VALID_DATA_ARE, listData);
                listError.add(errorMessages);
            }
            //Validation of amount 
            if (!isValidOperationSale(sale.getOperation())) {
                String validSales = OperationSale.R.getDescription() + ", " + OperationSale.B.getDescription();
                listData.clear();
                listData.put(ConstantData.TYPE_DATA, ConstantData.OPERATION_SALE);
                listData.put(ConstantData.DATA, sale.getOperation() + "");
                listData.put(ConstantData.NAME_OBJECT, nameMovie);
                listData.put(ConstantData.OBJECT, ObjectMovie.Movie.name());
                listData.put(ConstantData.VALID, validSales);
                errorMessages = OperationString.generateMesage(Message.NOT_VALID_SALE_VALID_DATA_ARE, listData);
                listError.add(errorMessages);
            }
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
    }

    private boolean isValidOperationSale(String sale) {
        try {
            OperationSale operationSale = OperationSale.valueOf(sale.trim().toUpperCase());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String getNameMovie(long idMovie) {
        String nameMovie = "";
        int i = 0;
        boolean findMovie = false;
        while (i < movies.size() && !findMovie) {
            Movie movie = (Movie) movies.get(i);
            if (movie.getId() == idMovie) {
                findMovie = true;
                nameMovie = movie.getName();
                System.out.println("Name: " +nameMovie);
            }
            i++;
        }
        return nameMovie;
    }
}
