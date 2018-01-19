package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class Sale extends ModelObject{
    private long idMovie;
    private int aomunt;
    private String operation;

    public Sale(long idMovie, int aomunt, String operation) {
        this.idMovie = idMovie;
        this.aomunt = aomunt;
        this.operation = operation.trim();
    }

    public long getIdMovie() {
        return idMovie;
    }

    public int getAomunt() {
        return aomunt;
    }

    public String getOperation() {
        return operation;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setAomunt(int aomunt) {
        this.aomunt = aomunt;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
}
