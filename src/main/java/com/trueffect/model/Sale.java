package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class Sale extends ModelObject{
    private long idMovie;
    private int amount;
    private String operation;

    public Sale() {
    }

    public Sale(long idMovie, int amount, String operation) {
        this.idMovie = idMovie;
        this.amount = amount;
        this.operation = operation.trim();
    }

    public long getIdMovie() {
        return idMovie;
    }

    public int getAmount() {
        return amount;
    }

    public String getOperation() {
        return operation;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOperation(String operation) {
        this.operation = operation.trim();
    }
}
