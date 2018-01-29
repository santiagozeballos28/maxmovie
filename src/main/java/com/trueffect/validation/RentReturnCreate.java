package com.trueffect.validation;

import com.trueffect.messages.Message;
import com.trueffect.model.Movie;
import com.trueffect.model.Sale;
import com.trueffect.response.Either;
import com.trueffect.tools.CodeStatus;
import com.trueffect.tools.ConstantData;
import com.trueffect.tools.ConstantData.ObjectMovie;
import com.trueffect.util.ModelObject;
import com.trueffect.util.OperationString;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author santiago.mamani
 */
public class RentReturnCreate {

    private ArrayList<ModelObject> movies;
    private ArrayList<Sale> sales;
    protected HashMap<String, String> listData;

    public RentReturnCreate() {
        listData = new HashMap<String, String>();
        movies = new ArrayList<ModelObject>();
        sales = new ArrayList<Sale>();
    }

    public RentReturnCreate(ArrayList<ModelObject> movies, ArrayList<Sale> sales) {
        listData = new HashMap<String, String>();
        this.movies = movies;
        this.sales = sales;
    }

    public Either complyCondition() {
        return verifyData();
    }

    private Either verifyData() {
        ArrayList<String> listError = new ArrayList<String>();
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
                String errorMessages = OperationString.generateMesage(Message.NOT_VALID_SALE_VALID_DATA_ARE, listData);
                listError.add(errorMessages);
            }
        }
        //To check if there was an error
        if (!listError.isEmpty()) {
            return new Either(CodeStatus.BAD_REQUEST, listError);
        }
        return new Either();
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
            }
            i++;
        }
        return nameMovie;
    }
}
