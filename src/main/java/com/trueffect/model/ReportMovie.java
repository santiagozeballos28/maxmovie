package com.trueffect.model;

import com.trueffect.util.ModelObject;

/**
 *
 * @author santiago.mamani
 */
public class ReportMovie extends ModelObject{

    private long idMovie;
    private String nameMovie;
    private int amountCurrent;
    private int amountRental;
    private int amountBuy;

    public ReportMovie() {
    }

    public ReportMovie(long idMovie, String nameMovie) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
    }

    public ReportMovie(long idMovie, String nameMovie, int amountCurrent, int amountRental, int amountBuy) {
        this.idMovie = idMovie;
        this.nameMovie = nameMovie;
        this.amountCurrent = amountCurrent;
        this.amountRental = amountRental;
        this.amountBuy = amountBuy;
    }

    public long getIdMovie() {
        return idMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public int getAmountCurrent() {
        return amountCurrent;
    }

    public int getAmountRental() {
        return amountRental;
    }

    public int getAmountBuy() {
        return amountBuy;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public void setAmountCurrent(int amountCurrent) {
        this.amountCurrent = amountCurrent;
    }

    public void setAmountRental(int amountRental) {
        this.amountRental = amountRental;
    }

    public void setAmountBuy(int amountBuy) {
        this.amountBuy = amountBuy;
    }
}
