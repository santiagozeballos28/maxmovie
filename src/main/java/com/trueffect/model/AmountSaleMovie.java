package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class AmountSaleMovie extends ModelObject{

    private long idMovie;
    private int amount;

    public AmountSaleMovie() {
    }

    public AmountSaleMovie(long idMovie, int amount) {
        this.idMovie = idMovie;
        this.amount = amount;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public int getAmount() {
        return amount;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
